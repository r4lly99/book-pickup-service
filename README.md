# Book Pickup Service
Books borrow and pickup service 

## Description
* Handle borrowing books from the library with a certain date.

## Getting Started
* Pull all service dependencies

### Dependencies

* Java8 / Java11
* SpringBoot

### Installing

* Clone this repository
* Enabled annotation processing

```
mvn clean install
```

### Executing program

* Run only

```
mvn spring-boot:run
```
### Build

To build your application simply run:

```
mvn package
```
And then run your app using the java command:
```
java -jar <path>book-pickup-service-0.0.1-SNAPSHOT.jar 
```

### Endpoint List

* Get list of books by subject
```
curl --location 'http://localhost:8080/api/v1/books/book-list?subject=java'
```

* Submit a book pick up schedule 
```
curl --location 'http://localhost:8080/api/v1/books/pickup' \
--header 'Content-Type: application/json' \
--data '{
    "edition_numbers": 26,
    "pick_up_date_time" : "2023-06-10 10:08:02",
    "username" : "rully",
    "phone_number" : "081224816942"
}'
```
### Version

* 0.0.1
```
- Initial Release 
```

