# Delivery fee calculator
Spring boot application that calculates delivery fee for couriers based on business rules and up to date weather conditions.
## :dart: Features
- **weather import** - Scheduled fetch for weather data from Estonian Environment Agency
- **Weather based calculation** - Implements business rules for temperature, wind speed, and phenomena and latest weather to calculate courier fee
- **History** - Historical fee can be calulated with correct weather data and base fee for given time
- **Fee managment** - CRUD interface to manage base fee-s for couriers
- **Vehicle restrictions** - Vehicle may be forbidden to be used based on weather and business rules
- **REST API** - REST interface with swagger documendation
---
## :computer: Technologies used
- Java
- H2 database
- Spring boot
- Lombok
- Swagger UI
- JUnit & Mockito
---
