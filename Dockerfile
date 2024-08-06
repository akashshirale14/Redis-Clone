FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /dicedb
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package 

FROM openjdk:11-jre-slim
WORKDIR /dicedb
# Copy the built JAR file from the build stage
COPY --from=build /dicedb/target/dice-server-1.0.jar /dicedb/dice-server-1.0.jar
EXPOSE 7379 
CMD ["java", "-jar", "dice-server-1.0.jar"]