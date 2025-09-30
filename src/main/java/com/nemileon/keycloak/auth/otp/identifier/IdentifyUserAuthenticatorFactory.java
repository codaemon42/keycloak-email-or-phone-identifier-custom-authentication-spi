package com.nemileon.keycloak.auth.otp.identifier;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class IdentifyUserAuthenticatorFactory implements AuthenticatorFactory {

    @Override
    public String getDisplayType() {
        return IdentifyUserProperties.CONFIG_DISPLAY_TYPE;
    }

    @Override
    public String getReferenceCategory() {
        return IdentifyUserProperties.CONFIG_DISPLAY_TYPE;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return IdentifyUserProperties.CONFIG_HELPER_TEXT;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        List<ProviderConfigProperty> configProperties = new ArrayList<>();
        ProviderConfigProperty labelTextProperty = new ProviderConfigProperty();
        labelTextProperty.setName(IdentifyUserProperties.CONFIG_PROPERTY_LABELTEXT_NAME);
        labelTextProperty.setLabel("Label Text");
        labelTextProperty.setHelpText("The label text appears above the input field");
        labelTextProperty.setType(ProviderConfigProperty.STRING_TYPE);
        labelTextProperty.setDefaultValue(IdentifyUserProperties.DEFAULT_LABEL_TEXT);

        ProviderConfigProperty buttonTextProperty = new ProviderConfigProperty();
        buttonTextProperty.setName(IdentifyUserProperties.CONFIG_PROPERTY_BUTTONTEXT_NAME);
        buttonTextProperty.setLabel("Button Text");
        buttonTextProperty.setType(ProviderConfigProperty.STRING_TYPE);
        buttonTextProperty.setDefaultValue(IdentifyUserProperties.DEFAULT_BUTTON_TEXT);

        ProviderConfigProperty phoneNumberProperty = new ProviderConfigProperty();
        phoneNumberProperty.setName(IdentifyUserProperties.CONFIG_PROPERTY_PHONE_ATTR_NAME);
        phoneNumberProperty.setLabel("Phone Number Attribute");
        phoneNumberProperty.setHelpText("The name of the user-attribute that holds the phone number");
        phoneNumberProperty.setType(ProviderConfigProperty.STRING_TYPE);
        phoneNumberProperty.setDefaultValue(IdentifyUserProperties.DEFAULT_PHONE_ATTR);

        configProperties.add(labelTextProperty);
        configProperties.add(buttonTextProperty);
        configProperties.add(phoneNumberProperty);
        return configProperties;
    }

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        return new IdentifyUserAuthenticator();
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return IdentifyUserProperties.PROVIDER_ID;
    }
}
