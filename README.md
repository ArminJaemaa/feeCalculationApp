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
## :heavy_exclamation_mark: Business rules
- Additional fees apply for bike and scooter based on wind and temperature
- Base fee depends on region and vehicle type
- Bike and scooter usage is forbidden based on weather phenomenon
- Bike usage is forbidden if wind speed is higher than Xm/s
---
## :scroll: Project structure
- `controller` - REST endpoinst and API documendation interface
- `dto` - Data Transfer Objects for external API mapping and structured request/response handling
- `entity/model` - Domain objects and Enums
- `exception` - Custom business exceptions and global error handling
- `repository` - Data access layer
- `service` - Business logic and scheduled integration task
---
## :books: Database schema
This application uses H2 in-memory database.

>- [H2 Database console](http://localhost:8080/h2-console "H2 console")
>  (`http://localhost:8080/h2-console`)
>    - JDBC URL: `jdbc:h2:mem:testdb`
>    - User: `sa`
>    - Pass: `(empty)`
---
## :rocket: How to run locally
1. Navigate to project foler
  ```
  cd feeCalculator
  ```
2. Run the application
  ```
  ./mvnw spring-boot:run
  ```
---
## :clipboard: API Documentation
- [API documentation](http://localhost:8080/swagger-ui/index.html "swagger UI")
  (`http://localhost:8080/swagger-ui/index.html`)
### Example request/response

```
GET /api/delivery-fee?city=Tallinn&vehicle=Car

response:
4
```

---
## :wrench: Tests
- **Unit tests** `DeliveryFeeServiceTest` - Math rules and forbidden exception tests
- **Integration tests** `DeliveryFeeIntegrationTest` - tests optional date and time fetch against a real h2 database instance
### Run all tests
```
./mvnw test
```
---
## Licence
[MIT](./LICENCE)
