## Running the application locally

Clone this application on a folder in a local machine, inside the folder 
user the following commands:

* package the application
```shell
mvn package
```

* build the application docker image
```shell
docker build -t api.school .
```

* start the docker containers with docker-compose (make sure your port 8080 is free)
```shell
docker-compose up -d
```

After completed these commands, you should have the two 
containers up and running. The application should be listening to URL and 
the Swagger with the documentation of API available as well.
- [http://localhost:8080](http://localhost:8080)
- [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/)

Check the containers using **docker ps** or a [CTOP](https://github.com/bcicen/ctop) tool.

* docker ps
  ![DockerPs](https://github.com/eduardojvendruscolo/metadata-school-registration/blob/master/images/docker-ps.png?raw=true)
* CTOP
  ![CTOP](https://github.com/eduardojvendruscolo/metadata-school-registration/blob/master/images/ctop.png?raw=true)

## Endpoints and payloads

The API documentation can be acessed by [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/)

![Swagger](https://github.com/eduardojvendruscolo/metadata-school-registration/blob/master/images/swagger.png?raw=true)

**Course API**

Save a new course
* Endpoint -> http://localhost:8080/course
* Method -> POST
* Payload example -> ```{
  "name": "molestiae nesciunt quis",
  "description": "Fuga labore fuga enim blanditiis. Tenetur est provident. Consectetur nihil cumque. Maxime alias tenetur enim id eos sed doloremque dolorem voluptas. Doloribus soluta omnis qui et fugit quo voluptatibus.",
  "startDate": "1664393494",
  "endDate": "1664393494"
  }```

Update an existing course
* Endpoint -> http://localhost:8080/course/{uuid}
* Method -> PUT
* Payload example -> ```{
  "name": "accusantium ullam natus",
  "description": "Sit magnam dolorum earum et ipsum dolores ducimus. Non id dolore quia sed sit veniam sunt minus nemo. Vel dolores dolor asperiores voluptatem voluptatem adipisci pariatur. Saepe sit accusantium sunt ipsam voluptatem. Aliquid doloribus officia. Corrupti quia aliquam nihil inventore pariatur unde.",
  "startDate": "1664393714",
  "endDate": "1664393714"
  }```

Delete an existing course
* Endpoint -> http://localhost:8080/course/{uuid}
* Method -> DELETE

Enroll a student to a course
* Endpoint -> http://localhost:8080/course/{courseId}/enroll-student/{studentId}
* Method -> POST

Get students of a course
* Endpoint -> http://localhost:8080/course/{courseId}/students
* Method -> GET

Get a list of courses without students
* Endpoint -> http://localhost:8080/course/without-student
* Method -> GET

Get courses with all relationships of students
* Endpoint -> http://localhost:8080/course/all-relationships
* Method -> GET

**Student API**

Save a new student
* Endpoint -> http://localhost:8080/student
* Method -> POST
* Payload example -> ```{
  "name": "Claude Simas",
  "document": "91",
  "birthDate": 1664394091,
  "phoneNumber": "44-590-771-4301"
  }```

Update a existing student
* Endpoint -> http://localhost:8080/student/{studentId}
* Method -> PUT
* Payload example -> ```{
  "name": "Courtney Carter",
  "document": "692",
  "birthDate": 1664394185,
  "phoneNumber": "9-472-804-0037"
  }```

Delete a existing student
* Endpoint -> http://localhost:8080/student/{studentId}
* Method -> DELETE

Get courses by student
* Endpoint -> http://localhost:8080/student/{studentId}/courses
* Method -> GET

Get students without courses
* Endpoint -> http://localhost:8080/student/without-course
* Method -> GET

Get students with all relationships of courses
* Endpoint -> http://localhost:8080/student/all-relationships
* Method -> GET

For test the API you can use a tool called [POSTMAN](https://www.postman.com/) and send 
requests to the API.

![Postman](https://github.com/eduardojvendruscolo/metadata-school-registration/blob/master/images/postman_2.gif?raw=true)