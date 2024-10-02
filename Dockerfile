FROM maven:3.9-eclipse-temurin-21 as build

WORKDIR /app

COPY src ./src

COPY pom.xml ./pom.xml

RUN mvn package

FROM eclipse-temurin:21.0.4_7-jre-noble

COPY --from=build /app/target/*.jar picpay-simplificado.jar

CMD ["java", "-jar", "picpay-simplificado.jar"]