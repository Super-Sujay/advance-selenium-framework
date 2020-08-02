# Advance Selenium Framework

This is an advance selenium automation framework which can be use for automating any web based application.

## Features
 - Page object model implementation (refer to the src/test/java folder for more reference).
 - Execution of tests using suite files (refer to the src/test/resources/suitefiles folder for more reference).
 - Data handling using Excel(.xlsx) files as well as CSV(.csv) files (refer to the src/test/resources folder for more reference).
 - Parallel execution of tests.
 - Ability to connect to an external selenium grid.
 - Ability to connect to a web based application using proxy settings.

## Requirement
 - Java 1.8 or higher version
 - Apache Maven 3.6.2 or higher version

## Installation
 - Clone the project in your local system.
 - Run the command `mvn clean install` in your local system.
 - Use the below mentioned dependency.
```
        <dependency>
		<groupId>org.automation.project</groupId>
		<artifactId>advance-selenium-framework</artifactId>
		<version>1.0.0</version>
	</dependency>
```