FROM adoptopenjdk/openjdk11
MAINTAINER farahani.dev@gmail.com
COPY target/wallet-api-0.0.1-SNAPSHOT.jar wallet-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/wallet-api-0.0.1-SNAPSHOT.jar"]