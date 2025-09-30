package com.nemileon.keycloak.auth.otp.identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IdentifyUserProperties {
    // SPI SPECIFIC TERMS
    public static final String PROVIDER_ID = "find-user-by-email-phone";
    public static final String CONFIG_DISPLAY_TYPE = "Find User By Email/Phone)";
    public static final String CONFIG_HELPER_TEXT = "Find user spi for identifying users with their email or phone number.";

    // CONST
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";

    // ADMIN CONFIG SPECIFIC TERMS
    public static final String DEFAULT_LABEL_TEXT = "Email or Phone";
    public static final String CONFIG_PROPERTY_LABELTEXT_NAME = "labelText";

    public static final String DEFAULT_BUTTON_TEXT = "Login";
    public static final String CONFIG_PROPERTY_BUTTONTEXT_NAME = "buttonText";

    public static final String DEFAULT_PHONE_ATTR = "phoneNumber";
    public static final String CONFIG_PROPERTY_PHONE_ATTR_NAME = "phoneNumberAttr";

    public static final String DEFAULT_AUTH_NOTE = "authMethod";
    public static final String CONFIG_PROPERTY_AUTH_NOTE_NAME = "authNoteName";

    // TEMPLATE SPECIFIC TERMS
    public static final String IDENTIFIER_FORM = "identity-user.ftl";
    public static final String EMAIL_OR_PHONE_FIELD = "username";
    public static final String IDENTIFIER_VALIDATION_ERROR_MSG = "Please enter email or phone number";
    public static final String USER_NOT_FOUND_ERROR_MSG = "User not found";

    // === CENTRALIZED CONFIG DEFAULTS MAP ===
    public static final Map<String, String> DEFAULTS = Map.ofEntries(
        Map.entry(CONFIG_PROPERTY_LABELTEXT_NAME, DEFAULT_LABEL_TEXT),
        Map.entry(CONFIG_PROPERTY_BUTTONTEXT_NAME, DEFAULT_BUTTON_TEXT),
        Map.entry(CONFIG_PROPERTY_PHONE_ATTR_NAME, DEFAULT_PHONE_ATTR),
        Map.entry(CONFIG_PROPERTY_AUTH_NOTE_NAME, DEFAULT_AUTH_NOTE)
    );
}
