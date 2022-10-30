# Meds Rest APIs

### Swagger

     http://localhost:8080/record/swagger-ui.html

### Actuator

    http://localhost:8080/record/actuator

### H2 Database console

    http://localhost:8080/record/h2

## Run application

    mvn spring-boot:run

### Reference Documentation

## Add records

### request

`POST /record/records`

    http://localhost:8080/record/api/v1/records

    [
        {
            "medId": 1,
            "name": "Ak47",
            "email": "mail@gmail.com"        
        },
        {
            "medId": 2,
            "name": "Ak47f",
            "email": "mail@gmail.com"       
        }
    ]

## Get a record

### request

`GET /record/records/{medId}`

    curl -i -H 'Accept: application/json' http://localhost:8080/record/api/v1/records/1

## Get all records

### request

`GET /record/records`

    curl -i -H 'Accept: application/json' http://localhost:8080/record/api/v1/records

## Update a record

### request

`PUT /record/records/{medId}`

    http://localhost:8080/record/api/v1/records/1

    {
        "medId": 1,
        "name": "Ak47b",
        "email": "mail@gmail.com"    
    }

## Delete a record

### request

`DELETE /record/records/{medId}`

    http://localhost:8080/record/api/v1/records/2

## Partial update a record

### request

`PATCH /record/records/{medId}`

    http://localhost:8080/record/api/v1/records/1

    {
        "name": "M-16"    
    }




