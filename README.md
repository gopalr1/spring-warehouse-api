### Spring Boot Application - spring-warehouse-api

## Background
Using spring framework / libraries build a cloud native warehouse service , that will accept a
search request with few text parameter on input or may be free text search. It returns the
matching products and location where they are placed in the warehouse.
It should have end points to create a warehouse boxes and create entry of a product which can
be placed in the warehouse boxes
Sort the result by product name alphabetically.
Make sure the service:
 is production-ready from resilience, stability, quality and performance point of view
 is self-documenting
 exposes health checks and metrics
 Uses RDBMS for data storage

Technical requirements:

- Java 11 runtime
- Spring Boot 2
- H2 Database


## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites
For building and running the application you need:

- Java 11 Runtime
- Maven 3

Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the package com.spring.warehouse.SpringWarehouseApiApplication from your IDE.


Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run

## SpringRecipeAPI
The Application exposes REST Endpoints as below

API Request:

Add a product to Warehouse rack - http://localhost:8080/api/warehouse/product Http Method : POST
Request:
```
      {  
         "rackId":"1",
         "warehouseName":"Utrecht-01",
         "orderNumber":34456,
         "productName":"ABC Window Glass",
         "quantity": 5,
         "packageSize":"LARGE",
         "notes":"Handle with care"    
      }
```

Add a new rack - http://localhost:8080/api/warehouse/rack Http Method : POST
Request:
```
      {  
          "rackType":"LARGE",
          "warehouseName":"Utrecht01"    
      }
```

Find all racks by allocation - http://localhost:8080/api/warehouse/racks Http Method : GET
 
Search product by name -http://localhost:8080/api/warehouse/search?productName=?&page=?&noOfRecords=? Http Method : GET

The API has pagination support

Sample JSON Response for Search product by name:
```
{
   "products":[
      {
         "id":2,
         "rackId":2,
         "warehouseName":"Utrecht01",
         "orderNumber":3028,
         "productName":"Glass Window",
         "quantity":2,
         "packageSize":"EXTRA_LARGE",
         "notes":"Handle with care",
         "inTimestamp":"2022-10-07T21:15:21.001",
         "outTimestamp":null
      }
   ],
   "totalRecords":1,
   "totalPages":1
}
```



## Swagger UI - API Documentation
In this Application the swagger UI is also configured for the API documentation purpose.

Swagger UI URL : http://localhost:8080/swagger-ui/index.html




## Test Cases


Unit and Integration cases are written with 85% Test Coverage.

For running test case
```
mvn clean verify
```

### Docker

To run this api as a container image. Using below commands will build the docker image and run

```


docker build -t warehouse-api .

docker container run -p 8080:8080 warehouse-api .


```
 
