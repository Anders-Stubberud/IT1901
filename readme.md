# Group gr2325 repository

[Open in che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2325/gr2325?new)
<br>
_NB! Che is not compatible with UI tests, so install with:_ `mvn clean install -Pskip-ui-tests` to run in che.
<br>
_This will also skip the jacoco report for the UI module. Install locally for full testing and full report._

This project contains the IT1901 project for group 25.

Our application is "WordDetective: Substring Edition". The main objective for our project is to create a fun and intuitive game for learning.

### TL;DR

WordDetective is about <code>writing words</code> that <code>contains</code> a certain <code>substring</code>. For example if the category is **fruits** and the substring is **"PL"**. Then one answer can be **Pineap<span style="color: green;">pl</span>e**

Further details about the application is located in the [WordDetective README](WordDetective/README.md).

# Documentation

## Releases of WordDetective:

([Release 1](docs/release1/README.md))
([Release 2](docs/release2/README.md))
([Release 3](docs/release3/README.md))

# Description of content

The root directory primarily contains the "docs" folder and the "WordDetective" Folder.
<br>
The "docs" folder contains the documentation of the various releases.
<br>
The "WordDetective" folder contains the code of the project, and is made up of the following modules:
api, core, persistence, types, and ui.
<br>
The api module contains the server configuration and various controllers. The controllers hold the API endpoints which are requested by the UI. Once the API controllers receives a request, it delegates the task to the core module.
<br>
The core module contains the core logic of the program. Tasks requiring access to persistently stored user information is delegated to the persistence module.
<br>
The persistence module accesses the persistent user files to retrieve user information.
<br>
The ui module contains files related to the frontend of the application, such as fxml files and their respective java controller files.
<br>
The types module contains the "User" class, which is used to store the user persistently, and enums for login and registration, which is used for giving feedback.
<br>

_Tentative sketch of folder structure_

```
WordDetective
│
└── core
│   │
│   └── src
│      ├── main
│      │   ├── java
│      │   │   └── core
│      │   │        └── contains core logic files and persistence with gson files
│      │   │
│      │   └── resources
│      │              ├── default_categories
│      │              │                    └── contains all the default categories, in JSON files
│      │              ├──default_stats
│      │              │              └── contains stats contributed by all guest users, in JSON format.
│      │              ├──users
│      ├── test              └── contains all registered user along with their information, in JSON format.
│          └── java
│              └── core
│                  └── contains test classes for core logic and persistence
│
│
└── ui
    │
    └──  src
       ├── main
       │   └── java
       │       └── ui
       │           └── contains controller files and factory files
       │
       ├── test
       │   └── java
       │       └── ui
       │           └── contains ui tests
       │
       └── resources
                   └── contains fxml files
```

# Version Requirements

**Java:** 17+

**JavaFx:** 17.0.8

**Apache Maven:** 3.8.1

# Building the project

**Clone code**
<br>
<code>git clone https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2325/gr2325.git</code>
<br>
<code>cd gr2325/WordDetective</code>

**Build backend application**
<br>
<i>locally: </i><code>mvn clean install</code>
<br>
<i>for che: </i><code>mvn clean install -Pskip-ui-tests<code>

**Create Desktop application**

- <code>mvn -f ui/pom javafx:jlink</code>
  - This will create a folder named "worddetectivelauncher", and an equivalent zip file in ui/target. Since we have included javafx media for mp4 background video, the "assets" folder in the ui module must be manually added to the runtime image. This is as simple as copying the "assets" folder into the "worddetectivelauncher" folder, and then update the zip file accordingly.
- <code>mvn -f ui/pom.xml jpackage:jpackage</code>
- change directory to ui/target/dist, and use: start WordDetective_SubstringEdition-1.0.0.exe
- Follow the instructions given by the setup wizard.

# Running application

**Run application**
<br>
Spin up the server: <code>mvn -f api/pom.xml spring-boot:run</code>
_open new terminal_
<code>mvn -f ui/pom.xml javafx:run</code>

# Running WordDetective test suite

### Tests

**Run tests**
<br>
<code>mvn test</code>

**Generate jacoco report**
(The report can be found in target/site/jacoco)
<br>
<code>mvn jacoco:report</code>

### Quality tests

**Run checkstyle test**
<br>
<code>mvn checkstyle:check</code>

**Run spotbugs test**
<br>
<code>mvn spotbugs:check</code>

# Clean project

**Clean up downloaded dependencies**
<br>
<code>mvn clean</code>

# Creators

```

- Dag Kristian Andersen
- Mads André Bårnes
- Bengt Andreas Rotheim
- Anders Stubberud

```
