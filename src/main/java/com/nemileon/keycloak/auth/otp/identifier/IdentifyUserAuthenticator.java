package com.nemileon.keycloak.auth.otp.identifier;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IdentifyUserAuthenticator implements Authenticator {
    private static final Logger logger = Logger.getLogger(IdentifyUserAuthenticator.class.getName());

    @Override
    public void authenticate(AuthenticationFlowContext authFlowContext) {
        authFlowContext.challenge(buildIdentifierForm(authFlowContext).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
    }

    @Override
    public void action(AuthenticationFlowContext authFlowContext) {
        Map<String, String> configs = getConfigs(authFlowContext);
        String authMethod = IdentifyUserProperties.EMAIL;
        String emailOrPhone = authFlowContext.getHttpRequest()
                .getDecodedFormParameters()
                .getFirst(IdentifyUserProperties.EMAIL_OR_PHONE_FIELD);

        logger.info("[FIND USER] Identifying user: " + emailOrPhone);
        // Input Validation
        if(emailOrPhone == null || emailOrPhone.isBlank()) {
            logger.warning("[FIND USER] Blank email or phone number");
            authFlowContext.failureChallenge(AuthenticationFlowError.INVALID_USER,
                    buildIdentifierForm(authFlowContext).setError(IdentifyUserProperties.IDENTIFIER_VALIDATION_ERROR_MSG).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
            return;
        }

        // User Lookup
        UserModel user = authFlowContext.getSession().users()
                .getUserByEmail(authFlowContext.getRealm(), emailOrPhone);
        if(user == null) {
            logger.info("[FIND USER] Identifying user with phone: " + emailOrPhone);
            authMethod = IdentifyUserProperties.PHONE;
            user = authFlowContext.getSession().users()
                    .searchForUserByUserAttributeStream(authFlowContext.getRealm(), configs.get(IdentifyUserProperties.CONFIG_PROPERTY_PHONE_ATTR_NAME), emailOrPhone)
                    .findFirst().orElse(null);
        }

        if (user == null) {
            logger.warning("[FIND USER] User not found by email and phone number");
            authFlowContext.failureChallenge(AuthenticationFlowError.INVALID_USER,
                    buildIdentifierForm(authFlowContext).setError(IdentifyUserProperties.USER_NOT_FOUND_ERROR_MSG).createForm(IdentifyUserProperties.IDENTIFIER_FORM));
            return;
        }

        authFlowContext.getAuthenticationSession().setAuthNote(configs.get(IdentifyUserProperties.CONFIG_PROPERTY_AUTH_NOTE_NAME), authMethod);
        authFlowContext.setUser(user);
        logger.info(String.format(
                "[FIND USER] Authentication flow details => authNote: %s, methodUsed: %s",
                configs.get(IdentifyUserProperties.CONFIG_PROPERTY_AUTH_NOTE_NAME),
                authFlowContext.getAuthenticationSession()
                        .getAuthNote(configs.get(IdentifyUserProperties.CONFIG_PROPERTY_AUTH_NOTE_NAME))
        ));
        authFlowContext.success();
    }

    /**
     * Additional method created to create and manage attributes in the form
     * */
    private LoginFormsProvider buildIdentifierForm(AuthenticationFlowContext authFlowContext) {
        Map<String, String> configs = getConfigs(authFlowContext);

        LoginFormsProvider emailOrPhoneForm = authFlowContext.form();
        emailOrPhoneForm.setAttribute(IdentifyUserProperties.CONFIG_PROPERTY_LABELTEXT_NAME, configs.get(IdentifyUserProperties.CONFIG_PROPERTY_LABELTEXT_NAME));
        emailOrPhoneForm.setAttribute(IdentifyUserProperties.CONFIG_PROPERTY_BUTTONTEXT_NAME, configs.get(IdentifyUserProperties.CONFIG_PROPERTY_BUTTONTEXT_NAME));

        return emailOrPhoneForm;
    }

    /**
     * Get Config Properties
     * */
    private Map<String, String> getConfigs(AuthenticationFlowContext authFlowContext) {
        Map<String, String> cfg = Optional.ofNullable(authFlowContext.getAuthenticatorConfig())
                .map(AuthenticatorConfigModel::getConfig)
                .orElse(Map.of());

        return IdentifyUserProperties.DEFAULTS.keySet().stream()
                .collect(Collectors.toMap(
                        key -> key,
                        key -> Optional.ofNullable(cfg.get(key)).orElse(IdentifyUserProperties.DEFAULTS.get(key))
                ));
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
