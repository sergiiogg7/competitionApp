FROM openjdk:17-jdk-alpine as builder

WORKDIR /app

#Coìar el pom, el mvw y la carpeta .mvn
COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

#Descargar solo las dependencias de maven
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/competitionApp-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar","competitionApp-0.0.1-SNAPSHOT.jar"]
