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

    upload CSV file with key: file, value: exercise,csv

## Get a record

### request

`GET /genesis/records/{code}`

    curl -i -H 'Accept: application/json' http://localhost:8080/genesis/api/v1/csv/records/1

## Get all records

### request

`GET genesis/api/v1/csv/records`

    curl -i -H 'Accept: application/json' http://localhost:8080/genesis/api/v1/csv/records

## Update a genesis

### request

`PUT /genesis/records/{medId}`

    http://localhost:8080/genesis/api/v1/records/1

    {
        "medId": 1,
        "name": "Ak47b",
        "email": "mail@gmail.com"    
    }

## Delete a record

### request

`DELETE /genesis/records/{medId}`

    http://localhost:8080/genesis/api/v1/records/2

## Partial update a record

### request

`PATCH /genesis/records/{medId}`

    http://localhost:8080/genesis/api/v1/records/1

    {
        "name": "M-16"    
    }




