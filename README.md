# MathApp
Application helps :
- Find sum of divisor of a number.

    ~~~~
    http://localhost:8080/sumofdivisors/123
    ~~~~
- Sort a csv string.
    ~~~~
    curl -X POST http://localhost:8080/sortnumbers/1,2,4,3
    ~~~~

### Building Application
- Maven

    ~~~~
    mvn clean package
    ~~~~
- Creating Docker container image
    - Using 'dockerd'. Execute command project root.
    
        ~~~
        sudo docker build -t mathapp .
        ~~~           
### Starting Application
##### Spring Boot
- Open terminal at project root folder.

    ~~~~
    java -Djava.security.egd=file:/dev/./urandom -jar MathApp-0.0.1-SNAPSHOT.jar
    ~~~~

##### Docker 
- Open terminal at project root folder.
 
    ~~~~
    sudo docker run -p 8080:8080 -d mathapp
    ~~~~

### AWS

##### Deploy Strategy 

- AWS CodePipeline
    - A push to code repository would auto trigger build for the project.  
    - Unit tests are run in CodeBuild.
    - Docker image is created and published to ECR. 
- ECS with Fargate
    - Deploy phase of the pipeline would deploy the newly generated container published in ECR to the already Fargate service.
 