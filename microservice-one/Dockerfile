ARG BUILD_IMAGE=maven:3.9.2-amazoncorretto-20
ARG RUNTIME_IMAGE=amazoncorretto:20-alpine3.17

##########################################################
# Build Application
##########################################################

FROM ${BUILD_IMAGE} as build
WORKDIR /microservice-one
COPY src ./src
COPY pom.xml ./

RUN mvn clean install


##########################################################
# Run Application
##########################################################
FROM ${RUNTIME_IMAGE}
COPY --from=build /microservice-one/target/microservice-one-*.jar /app/microservice-one.jar
EXPOSE 80
CMD ["java", "-jar", "-Dserver.port=80", "/app/microservice-one.jar"]
