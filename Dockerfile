# Build Angular
FROM node:23 AS ng-build

WORKDIR /src

RUN npm i -g @angular/cli

# COPY ecommerce/client/public public
COPY ecommerce/client/src src
COPY ecommerce/client/*.json .

RUN npm ci && ng build

# Build Spring Boot
FROM openjdk:23-jdk AS j-build

WORKDIR /src

COPY ecommerce/.mvn .mvn
COPY ecommerce/src src
COPY ecommerce/mvnw .
COPY ecommerce/pom.xml .

# Copy angular files over to static
COPY --from=ng-build /src/dist/client-side/browser/ src/main/resources/static

RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true

# Copy the JAR file over to the final container
FROM openjdk:23-jdk 

WORKDIR /app

COPY --from=j-build /src/target/*.jar app.jar

ENV PORT=8080
# ENV SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/bgg

EXPOSE ${PORT}

SHELL [ "/bin/sh", "-c" ]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar






# # Build the Angular client
# FROM node:23 AS buildang

# WORKDIR /src

# # Copy Angular source
# COPY ecommerce/client/*.json .
# # COPY ecommerce/client/public public
# COPY ecommerce/client/src src

# # Install dependencies and Angular CLI
# RUN npm ci
# RUN npm i -g @angular/cli

# # Build Angular
# RUN ng build --configuration=production

# # Build the Spring Boot application
# FROM eclipse-temurin:23-jdk AS buildjava

# WORKDIR /src

# COPY ecommerce/mvnw .
# COPY ecommerce/pom.xml .
# COPY ecommerce/src src
# COPY ecommerce/.mvn .mvn

# # Copy Angular build to Spring Boot static directory
# COPY --from=buildang /src/dist/client-side/browser/ src/main/resources/static/

# # Make mvnw executable and build Spring Boot app
# RUN chmod +x mvnw
# RUN ./mvnw package -DskipTests=true 

# # Deployment container
# FROM eclipse-temurin:23-jre

# WORKDIR /app

# # Copy the built Spring Boot JAR
# COPY --from=buildjava /src/target/* app.jar
# # COPY --from=buildjava /app/target/*.jar app.jar


# # Set environment variables
# ENV PORT=8080
# # ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/csf_b4_assessment
# # ENV SPRING_DATASOURCE_USERNAME=root
# # ENV SPRING_DATASOURCE_PASSWORD=password

# # ENV SPRING_MONGODB_URI=mongodb://localhost:27017/csf_b4_assessment

# EXPOSE ${PORT}

# ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar -Dserver.port=${PORT}