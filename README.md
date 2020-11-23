#Poseiden App

Poseiden is a financial and trading web-app with this features :

- BidLists
- CurvePoints
- Rules
- Ratings
- Trades

This features are accessible for every connected User.

It possible to add User with profil "Admin" or "User"

Only Admin profil can manage users.

## Getting Started

- Endpoint : http://localhost:8080/
- Actuator : http://localhost:8090/

## Prerequisites
### What things you need to install the software

1. Framework: Spring Boot v2.3.1 RELEASE
2. Java 1.8
3. Maven 3.6.2
4. Thymeleaf
5. Mysql 8.0 : need to create a MySQL database "demoP7" on localhost.


## Installing
1.  Run the sql command create.sql ou use MySql Workbench

2.  User and password to access to the DB are stored in Environnement Variables
     
    -    Use application.properties to change :
        
            - **spring.datasource.username=${P7_USER_SQL}**
            - **spring.datasource.password=${P6_PWD_SQL}**

## Running App
To start the application, execute :

java -jar **Poseiden-skeleton-0.0.1-SNAPSHOT.jar**

## Testing
mvn clean install mvn clean verify (generate tests and test report) mvn site (generate reportings)
