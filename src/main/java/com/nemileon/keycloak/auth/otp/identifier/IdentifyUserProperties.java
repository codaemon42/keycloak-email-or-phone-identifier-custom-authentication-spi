package com.nemileon.keycloak.auth.otp.identifier;

public class IdentifyUserProperties {
    public static final String PROVIDER_ID = "identify-user-authenticator";
    public static final String DEFAULT_LABEL_TEXT = "Email or Phone";
    public static final String DEFAULT_BUTTON_TEXT = "Login";
    public static final String CONFIG_DISPLAY_TYPE = "Identify User (Email/Phone)";
    public static final String CONFIG_HELPER_TEXT = "Identifier user spi for identifying users with email or phone numbers.";
    public static final String CONFIG_PROPERTY_LABELTEXT_NAME = "labelText";
    public static final String CONFIG_PROPERTY_BUTTONTEXT_NAME = "buttonText";

    public static final String EMAIL_OR_PHONE_FIELD = "username";
    public static final String PHONE_USER_ATTR = "phoneNumber";
    public static final String IDENTIFIER_FORM = "identity-user.ftl";
    public static final String IDENTIFIER_VALIDATION_ERROR_MSG = "Please enter email or phone number";
    public static final String USER_NOT_FOUND_ERROR_MSG = "User not found";

}
