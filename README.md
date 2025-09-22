# Keycloak Email/Phone Identifier Authentication SPI Deployment Script

This project provides a simple **Bash automation script** to build and deploy a custom **Keycloak Service Provider Interface (SPI)** along with custom theme templates.

The script handles the following steps:

1. **Build SPI JAR** using Maven.
2. **Copy JAR** into the Keycloak `providers/` directory.
3. **Copy custom theme template** (`identity-user.ftl`) into your selected Keycloak theme.
4. **Start Keycloak** in development mode with debug logging enabled.

---

## 🚀 Prerequisites

Make sure you have the following installed and configured before using this script:

- **Keycloak**: Version `26.3.3` (adjust `KEYCLOAK_DIR` in the script for your environment).
- **Java**: `11` or higher (required for Maven and Keycloak).
- **Maven**: For building the SPI JAR (`mvn clean package`).
- **Bash Shell**: Linux, macOS, or Git Bash on Windows.

---

## 📂 Project Structure

```
.
├── deploy.sh                     # Deployment script (this file)
├── pom.xml                       # Maven configuration for SPI
├── src/main/java/...             # SPI source code
└── src/main/resources/theme-resources/
    └── identity-user.ftl         # Custom theme template
```

---

## ⚙️ Configuration

Before running the script, update the following variables inside `deploy.sh`:

```bash
KEYCLOAK_DIR=/path/to/keycloak-26.3.3
KEYCLOAK_SELECTED_THEME=base-extended
```

- `KEYCLOAK_DIR` → Path to your local Keycloak installation.
- `KEYCLOAK_SELECTED_THEME` → Target Keycloak theme where the template should be copied.

---

## ▶️ Usage

Run the script to build, deploy, and start Keycloak:

```bash
bash deploy.sh
```

### Example Output

```
[*] Generating Jar...
[✔] SPI JAR package built successfully.

[*] Exporting Jar...
[✔] Jar copied successfully to /e/space/identity/keycloak-26.3.3/providers/

[*] Exporting templates...
[✔] Template copied successfully to /e/space/identity/keycloak-26.3.3/themes/base-extended/login/

[*] Starting keycloak...
```

Keycloak will then start in **development mode** with debug logs enabled.

---

## 🛠 Troubleshooting

- **Maven not found**  
  → Install Maven and ensure `mvn` is in your PATH.

- **Keycloak fails to start**  
  → Check `KEYCLOAK_DIR` path and confirm Keycloak is installed correctly.

- **Template not applied**  
  → Verify that the selected theme (`KEYCLOAK_SELECTED_THEME`) is active in your Keycloak realm.

---

## 📜 License

This project is licensed under the MIT License – feel free to use and adapt it.  


---

## ☕ Buy Me a Coffee ❤️

If you found this Keycloak SPI helpful, you can support me by buying me a coffee!

Even a small $5 contribution helps me maintain the project, fix bugs, and add small improvements.

<p align="center">
  <a href="https://donate.stripe.com/7sY4gz02HctGeM158q1sQ01" target="_blank">
    <img src="https://img.shields.io/badge/Buy%20Me%20a%20Coffee-$5-blue?style=for-the-badge&logo=Coffee" alt="Buy Me a Coffee"/>
  </a>
</p>

Thanks a lot for your support! It really keeps this project alive! 🙏
