#!/bin/bash

KEYCLOAK_DIR=/e/space/identity/keycloak-26.3.3
KEYCLOAK_SELECTED_THEME=base-extended

#######################################################
# STEP 1: Generate SPI jar
#######################################################
echo "[*] Generating Jar..."
if mvn clean package; then
  echo -e "\e[32m[✔] SPI JAR package built successfully.\e[0m"
else
  echo -e "\e[31m[✘] Failed to build SPI JAR package.\e[0m" >&2
  exit 1
fi

#######################################################
# STEP 2: Exporting SPI jar to keycloak providers
#######################################################
echo "[*] Exporting Jar..."
if cp ./target/*.jar "${KEYCLOAK_DIR}/providers/"; then
  echo -e "\e[32m[✔] Jar copied successfully to ${KEYCLOAK_DIR}/providers/ \e[0m"
else
  echo echo -e "\e[31m[✘] Failed to copy Jar to ${KEYCLOAK_DIR}/providers/ \e[0m" >&2
  exit 1
fi

########################################################
# STEP 3: Exporting SPI template to keycloak Themes
########################################################
echo "[*] Exporting templates..."
if cp ./src/main/resources/theme-resources/identity-user.ftl "${KEYCLOAK_DIR}/themes/${KEYCLOAK_SELECTED_THEME}/login/"; then
  echo -e "\e[32m[✔] Template copied successfully to ${KEYCLOAK_DIR}/themes/${KEYCLOAK_SELECTED_THEME}/login/ \e[0m"
else
  echo echo -e "\e[31m[✘] Failed to copy template to ${KEYCLOAK_DIR}/themes/${KEYCLOAK_SELECTED_THEME}/login/ \e[0m" >&2
  exit 1
fi

########################################################
# STEP 4: Starting Keycloak Development Server
########################################################
echo "[*] Starting keycloak..."
${KEYCLOAK_DIR}/bin/kc.sh start-dev --log-level=org.keycloak:DEBUG