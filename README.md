# AGH Quality Assurance - End of term project assignment
# RestAssured - TestNG -Java- Automated testing
A Maven project using RestAssured and TestNG frameworks.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)


## Providing test data

The project uses methods with data entered manuall as well as DataProviders.
DataProviders 

| type of data providing      | Details                                 |
|:----------------------------|:----------------------------------------|
|basic test method            | data is enetered in the test method     |
|method using Data Provider   | file is input in the DataProvider method|
|DataProvider reading from file| reads data from provided csv file      |


## Getting Started

Copy the repo into your local machine.


### Run tests locally

Right click the feature file and select "Run" or "Debug" to start the test.

### Run tests through the commandline

As this project uses Maven and can be run using commandline.

To run the test, use your CI or point Maven to the project and use the goals:

```
mvn clean
```






