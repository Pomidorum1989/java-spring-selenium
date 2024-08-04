## Automation framework leveraging Spring and Selenium

### Description
Simple test automation framework based on spring boot, selenium and Allure reporting

### Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Pomidorum1989/java-spring-selenium
   ./gradlew test -Dtestng.suite=src/test/resources/suits/sauce-lab-suite.xml -Dbrowser=chrome