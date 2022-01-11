# Spring boot project

## REST-API controller for managing a beauty online shop

### Entities
1. User
2. Order
3. OrderDetails
4. Product
5. Review
6. Category

### Technologies and libraries

1. Spring boot
2. Hibernate
3. JPA
4. Maven
5. Mapstruct
6. Lombok
7. Mockito

### Documentation

For using the application, the requests can be executed via ```swagger``` by going to the following ```URL```:
<br>
```http://localhost:8099/swagger-ui/#/```
<br>

[![Screenshot-2022-01-10-at-23-33-37.png](https://i.postimg.cc/KzZdT67X/Screenshot-2022-01-10-at-23-33-37.png)](https://postimg.cc/sBN6d00K)
[![Screenshot-2022-01-10-at-23-33-31.png](https://i.postimg.cc/vm7JcYVL/Screenshot-2022-01-10-at-23-33-31.png)](https://postimg.cc/nszRWtTC)
[![Screenshot-2022-01-10-at-23-33-50.png](https://i.postimg.cc/NMpZkdbK/Screenshot-2022-01-10-at-23-33-50.png)](https://postimg.cc/Ppv6tzgH)

### Testing
[![Screenshot-2022-01-11-at-12-17-12.png](https://i.postimg.cc/9M8yp99m/Screenshot-2022-01-11-at-12-17-12.png)](https://postimg.cc/sQWBfvKq)

[![Screenshot-2022-01-11-at-12-13-55.png](https://i.postimg.cc/NMf1mD5C/Screenshot-2022-01-11-at-12-13-55.png)](https://postimg.cc/McNM8y01)

Created unit tests for the services methods and for the REST endpoints. All tests passed with success with
an average of **80%** line coverage for service and **81%** line coverage for controller.
Tests can be run via IntelliJ interface or using the following command:
<br>
```mvn test```
<br>
