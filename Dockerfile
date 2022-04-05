FROM maven:3.8.4-openjdk-11
ADD . /src
WORKDIR /src

RUN rm -f application.*

RUN mvn clean
RUN mvn package spring-boot:repackage
RUN mv /src/target/challenge-0.0.1.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar","./app.jar"]