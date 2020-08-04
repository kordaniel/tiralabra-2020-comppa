# tiralabra-2020-comppa
This is a project that is created for the University of Helsinki course [_Aineopintojen harjoitustyö_](https://courses.helsinki.fi/fi/TKT20010/135568630). In short Comppa will be an software, that can be used to compress and decompress various files using different algorithms. It will also provide ways to compare both the results and speeds between the different algorithms. More info can be found in the documentation.

Comppa does not use any ready made data structures or algorithms, not from Java standard library or any other library. Everything will be implemented in pure Java from scratch, as this is one requirement for the course.  

### Documentation
* [Specification](documentation/specifications.md) 
* [User manual](documentation/user_manual.md) 
* [Tests](documentation/testing.md) 
* [Implementation](documentation/implementation.md)

### Weekly reports
* [Week 2](documentation/reports/week_report.md#viikko-2)
* [Week 1](documentation/reports/week_report.md#viikko-1)

### Releases
* Releases will be published here

### Gradle
Gradle is included in this repository. All the following commands assume you use the gradlew-binary, which is located inside the comppa-directory. If some of the commands fails to execute, run `./gradlew clean` and try again.  

#### Unittests
Unittests can be run with the following command
```
./gradlew test
```
This command will generate a report with the output of the tests into `comppa/build/reports/tests/test/index.html`  

#### Test coverage report
Test coverage report can be generated with the following command utilizing Jacoco
```
./gradlew jacocoTestReport
```
This command will genereate a report with the details into `comppa/build/reports/jacoco/test/html/index.html`  

#### Checkstyle report
Checkstyle reports can be generated with the following commands
```
./gradlew check
./gradlew checkstyleMain
./gradlew checkstyleTest
```
These commands will generate reports inside the directory `comppa/build/reports/checkstyle/`. For example the `./gradlew check` command will generate an HTML-formatted report called `main.html`
