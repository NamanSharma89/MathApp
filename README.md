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


##### Kubernetes with Minikube
- Install instructions for K8s on linux machine. 

    - Installing `kubectl`
        - Download version v1.19.0 on Linux, type:
          ~~~~
          curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.19.0/bin/linux/amd64/kubectl
          ~~~~
      
        - Make the `kubectl` binary executable.
            ~~~~
            chmod +x ./kubectl
            ~~~~
        - Move the binary in to your PATH.
            ~~~~
            sudo mv ./kubectl /usr/local/bin/kubectl
            ~~~~
        - Test to ensure the version you installed is up-to-date:
            ~~~~
            kubectl version --client
            ~~~~

    - Installing **Minikube** 
        -  Refer important instructions on installing correct hypervisor for your machine. VirtualBox is used here.
            -  https://kubernetes.io/docs/tasks/tools/install-minikube/
        -  Download Minikube with below instruction :
                
            ~~~~
            curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
              && chmod +x minikube
            ~~~~
        
        -  Add executable to your path

            ~~~~
            sudo mkdir -p /usr/local/bin/
            sudo install minikube /usr/local/bin/  
            ~~~~
- Starting Application with Minikube

    -   Start Minikube
    
        -  To start Minikube for first time with VirtualBox
        
            ~~~~
            minikube start --driver=virtualbox
            ~~~~
        
        -  To start if you have already built the image on VirtualBox once.
        
            ~~~~
            minikube start
            ~~~~
           
    -   To ensure we use K8s (Minikube's) docker environment to upload docker image, execute the below commands.
        
        - To enable :
        
            ~~~~ 
            eval $(minikube docker-env) 
            ~~~~
        
        - To disable : 
            
            ~~~~
            eval $(minikube docker-env -u). 
            ~~~~
          
    -   Build the docker image using below command it will be published inside minikube’s environment
        
        ~~~~
        docker build -t mathapp:mathapp .
        ~~~~
     
    -   Check if the image is present. 
        
        ~~~~
        docker images
        ~~~~ 
        
    -   The definition of K8s cluster can be defined in a file. Refer `deployment.yaml`. 
    -   Apply the definition to Minikube 
        
        ~~~~
        kubectl apply -f deployment.yaml
        ~~~~
        
        Success output logs indicating the cluster is up.
        
        ~~~~
        deployment.apps/mathapp created
        service/mathapp created
        ~~~~  
    
    -   Get the url for the service to access the pods where application is running 
        
        ~~~~
        minikube service mathapp   
        ~~~~    
    
    -   Append the REST endpoints to access the application service.
    
    -   To delete the deployment 
        
        ~~~~
        kubectl delete deployment mathapp   
        ~~~~
        
        ...and service
        
        ~~~~
        kubectl delete -n default service mathapp
        ~~~~



### AWS
    
##### Deploy Strategy 

- AWS CodePipeline
    - A push to code repository would auto trigger build for the project.  
    - Unit tests are run in CodeBuild.
    - Docker image is created and published to ECR. 
- ECS with Fargate
    - Deploy phase of the pipeline would deploy the newly generated container published in ECR to the already Fargate service.
 