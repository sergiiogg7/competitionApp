FROM openjdk:17-jdk-alpine as builder

WORKDIR /app

#Coìar el pom, el mvw y la carpeta .mvn
COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

#Descargar solo las dependencias de maven
# -clean -- Ejecuta las fases pre-clean y clean del ciclo de vida CLEAN de maven(elimina el target)
# -package -- Ejecuta todos las fases del ciclo de vida por defecto hasta la pase package
# -Dmaven.main.skip -- Omite el codigo fuente
# -Dspring-boot.repackage.skip -- Omite la reempaquetacion de la app Spring Boot en un ejecutable . Para no crear el JAE
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

COPY ./src ./src

RUN ./mvnw clean package "-Dmaven.test.skip"

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/competitionApp-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "competitionApp-0.0.1-SNAPSHOT.jar"]

