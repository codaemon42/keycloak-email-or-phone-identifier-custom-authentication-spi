package com.nemileon.keycloak.auth.otp.identifier;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Map;

public class IdentifyUserAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext authFlowContext) {
        authFlowContext.challenge(buildIdentifierForm(authFlowContext).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
    }

    @Override
    public void action(AuthenticationFlowContext authFlowContext) {
        String emailOrPhone = authFlowContext.getHttpRequest()
                .getDecodedFormParameters()
                .getFirst(IdentifyUserProperties.EMAIL_OR_PHONE_FIELD);

        // Input Validation
        if(emailOrPhone == null || emailOrPhone.isBlank()) {
            authFlowContext.failureChallenge(AuthenticationFlowError.INVALID_USER,
                    buildIdentifierForm(authFlowContext).setError(IdentifyUserProperties.IDENTIFIER_VALIDATION_ERROR_MSG).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
            return;
        }

        // User Lookup
        UserModel user = authFlowContext.getSession().users()
                .getUserByEmail(authFlowContext.getRealm(), emailOrPhone);
        if(user == null) {
            user = authFlowContext.getSession().users()
                    .searchForUserByUserAttributeStream(authFlowContext.getRealm(), IdentifyUserProperties.PHONE_USER_ATTR, emailOrPhone)
                    .findFirst().orElse(null);
        }

        if (user == null) {
            authFlowContext.failureChallenge(AuthenticationFlowError.INVALID_USER,
                    buildIdentifierForm(authFlowContext).setError(IdentifyUserProperties.USER_NOT_FOUND_ERROR_MSG).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
            return;
        }

        authFlowContext.setUser(user);
        authFlowContext.success();
    }

    /**
     * Additional method created to create and manage attributes in the form
     * */
    private LoginFormsProvider buildIdentifierForm(AuthenticationFlowContext authFlowContext) {
        String labelText = IdentifyUserProperties.DEFAULT_LABEL_TEXT;
        String buttonText = IdentifyUserProperties.DEFAULT_BUTTON_TEXT;

        if (authFlowContext.getAuthenticatorConfig() != null) {
            Map<String, String> cfg = authFlowContext.getAuthenticatorConfig().getConfig();
            if (cfg != null) {
                labelText = cfg.getOrDefault(IdentifyUserProperties.CONFIG_PROPERTY_LABELTEXT_NAME, labelText);
                buttonText = cfg.getOrDefault(IdentifyUserProperties.CONFIG_PROPERTY_BUTTONTEXT_NAME, buttonText);
            }
        }

        LoginFormsProvider emailOrPhoneForm = authFlowContext.form();
        emailOrPhoneForm.setAttribute(IdentifyUserProperties.CONFIG_PROPERTY_LABELTEXT_NAME, labelText);
        emailOrPhoneForm.setAttribute(IdentifyUserProperties.CONFIG_PROPERTY_BUTTONTEXT_NAME, buttonText);

        return emailOrPhoneForm;
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
