# Genesis Rest APIs

### Swagger
    
     http://localhost:8080/genesis/swagger-ui.html

### Actuator

    http://localhost:8080/genesis/actuator

### H2 Database console

    http://localhost:8080/genesis/h2

## Run application

    mvn spring-boot:run

### Reference Documentation

## Add records

### request

`POST /genesis/api/v1/csv`

    http://localhost:8080/genesis/api/v1/csv

    upload CSV file with 
    [key: "file", value: exercise.csv]
![img.png](img.png)

## Fetch by code

### request

`GET /genesis/records/{code}`

    curl -i -H 'Accept: application/json' http://localhost:8080/genesis/api/v1/csv/records/1

## Fetch all data

### request

`GET genesis/api/v1/csv/records`

    curl -i -H 'Accept: application/json' http://localhost:8080/genesis/api/v1/csv/records

## Delete by record id

### request 

`DELETE /genesis/records/{recordId}`

    http://localhost:8080/genesis/api/v1/csv/records/1

## Delete all

### request

`DELETE /genesis/records`

    http://localhost:8080/genesis/api/v1/csv/records




