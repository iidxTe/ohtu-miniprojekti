# ohtu-miniprojekti
 ![](https://github.com/iidxTe/ohtu-miniprojekti/workflows/Java%20CI/badge.svg)

[![codecov](https://codecov.io/gh/iidxTe/ohtu-miniprojekti/branch/master/graph/badge.svg)](https://codecov.io/gh/iidxTe/ohtu-miniprojekti)

[Product Backlog and Sprint Backlogs ](https://docs.google.com/spreadsheets/d/1jcgyrBhQjKcOjReRpKeF86ApAhfezBlr4MvJ3JAZQGc/edit?usp=sharing)

The idea of this project is to practice using agile software development and scrum processes.

In this project a reading tip library will be implemented. The idea of the library is to be a place where user can save their reading tips. User is able to browse the tips and mark books read. The user needs to register to the library.

### User Guide
Application can be used directly via browser from url:
https://limitless-lowlands-09269.herokuapp.com/

You can also download the software to your workstation by cloning from the project: 
https://github.com/iidxTe/ohtu-miniprojekti.git

After cloning the project, run command:
"./gradlew bootrun" 
in the project root file and after this use the software in address localhost:8080.

You need to have Java 8 installed to use this software.

### Tests:
Tests can be executed by running:
"./gradlew test jacocoTestReport". 

Report about the range of tests can be found from file:
build/reports/jacoco/test/html/index.html


### Definition of Done
* User stories have acceptance criteria, and most of them are also documented with Cucumber and are
automatically tested
* Line coverage of added code is at least 70%
* CI passes all tests
* Feature is usable on production
* Code quality is acceptable
  * Good naming
  * Modular architecture
  * Consistent code style
  
