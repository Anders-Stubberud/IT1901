# Group gr2325 repository

This project contains the IT1901 project for group 25.

Our application is "WordDetective: Substring Edition". The main objective for our project is to create a fun and intuitive game for learning.
Further details about the application is located in the [WordDetective README](WordDetective/README.md).

# Documentation

## Releases of WordDetective:

( [Release 1](docs/release1/README.md) )

# Description of content

The root directory primarily contains the "docs" folder and the "WordDetective" Folder.
<br>
The "docs" folder contains the documentation of the various releases.
<br>
The "WordDetective" folder contains the code of the project, and is made up of the "core" and "ui" folders.
<br>
The "core" folder contains the backend java files and their correlating tests.
<br>
The "ui" folder contains files related to the frontend of the application, such as fxml files and their respective java controller files.

## Version Requirements

**Java:** 17+

**JavaFx:** 17.0.8

**Apache Maven:** 3.8.1

## Building the project

**Clone code**
<br>
<code>git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2325/gr2325.git</code>
<br>
<code>cd gr2325/WordDetective</code>

**Build backend application**
<br>
<code>mvn clean install</code>

## Running application

**Run application**
<br>
<code>mvn -f ui/pom.xml javafx:run</code>

## Running WordDetective test suite

**Run tests**
<br>
<code>mvn test</code>

**Run checkstyle test**
<br>
<code>mvn checkstyle:check</code>

**Generate jacoco report**
(The report can be found in target/site/jacoco)
<br>
<code>mvn jacoco:report</code>

## Clean project

**Clean up downloaded dependencies**
<br>
<code>mvn clean</code>

# Creators

````
- Dag Kristian Andersen
- Mads André Bårnes
- Bengt Andreas Rotheim
- Anders Stubberud
```
````
