# Prueba Producto

Este archivo describe todos los pasos realizados para configurar, probar y desplegar la aplicación de *Producto* utilizando *Spring Boot, **Docker, **JUnit, **Mockito, y **Kubernetes*.

## Índice
1. [Requisitos Previos](#requisitos-previos)  
2. [Configuración del Proyecto](#configuración-del-proyecto)  
3. [Pruebas Unitarias](#pruebas-unitarias)  
4. [Construcción de Imagen Docker](#construcción-de-imagen-docker)  
5. [Despliegue en Kubernetes](#despliegue-en-kubernetes)  
6. [Acceso a Swagger y H2](#acceso-a-swagger-y-h2)  
7. [Comandos Utilizados](#comandos-utilizados)  


## Descripción del Proyecto
Este proyecto de prueba *Producto* es una aplicación basada en *Spring Boot* que permite gestionar órdenes y productos. La aplicación incluye servicios REST para la creación y consulta de productos y órdenes, y está configurada para pruebas unitarias, contenerización con *Docker* y despliegue en un clúster de *Kubernetes*.

## Requisitos Previos
- *Java 17*  
- *Maven*  
- *Docker Desktop*  
- *Kubernetes (Minikube o Docker Desktop con Kubernetes habilitado)*  
- *Postman* (opcional para probar las APIs)

## Configuración del Proyecto

1. *Clonar el repositorio o preparar la estructura del proyecto*.
2. **Configurar el archivo application.properties:**

properties
spring.application.name=producto
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update


3. *Dependencias principales* (agregadas en pom.xml):
   - Spring Boot Starter Web
   - Spring Boot Starter Data JPA
   - H2 Database
   - Swagger
   - JUnit 5 y Mockito para pruebas unitarias

## Pruebas Unitarias

Se implementaron las pruebas unitarias para los servicios **OrderServiceImpl** y **ProductServiceImplement** utilizando *JUnit 5* y *Mockito*.

Ejemplo de ejecución de pruebas:

bash
mvn test


*Resultado esperado:*

plaintext
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS


## Construcción de Imagen Docker

1. *Crear el archivo Dockerfile:*

dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/producto-0.0.1-SNAPSHOT.jar /app/producto.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/producto.jar"]


2. *Construir la imagen Docker:*

bash
docker build -t nicolasmenaca/producto:latest .


3. *Verificar la imagen creada:*

bash
docker images


4. *Ejecutar la aplicación en un contenedor Docker:*

bash
docker run -p 8080:8080 nicolasmenaca/producto:latest


## Despliegue en Kubernetes

1. *Crear los archivos YAML:*

**deployment.yaml:**

yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: producto-deployment
spec:
  replicas: 2
  selector:
    matchLabels:
      app: producto
  template:
    metadata:
      labels:
        app: producto
    spec:
      containers:
        - name: producto
          image: nicolasmenaca/producto:latest
          ports:
            - containerPort: 8080


**service.yaml:**

yaml
apiVersion: v1
kind: Service
metadata:
  name: producto-service
spec:
  type: LoadBalancer
  selector:
    app: producto
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080


2. *Aplicar los recursos en Kubernetes:*

bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml


3. *Verificar el estado de los pods y servicios:*

bash
kubectl get pods
kubectl get services


4. *Acceder al servicio en Kubernetes:*  
   Usa la IP del *LoadBalancer* o el comando:

bash
minikube service producto-service --url


## Acceso a Swagger y H2

- *Swagger UI:*  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

- *Consola H2:*  
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  *Conectar con: jdbc:h2:mem:testdb, usuario sa y sin contraseña.*

## Comandos Utilizados

- *Construir y ejecutar pruebas:*

  bash
  mvn clean install
  mvn test
  

- *Construir imagen Docker:*

  bash
  docker build -t nicolasmenaca/producto:latest .
  

- *Desplegar en Kubernetes:*

  bash
  kubectl apply -f deployment.yaml
  kubectl apply -f service.yaml
  

- *Verificar servicios y pods:*

  bash
  kubectl get pods
  kubectl get services
  
