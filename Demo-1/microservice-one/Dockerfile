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
EXPOSE 8080

# NOTE:
# The management port 9090 is diabled by default.
# If required it can be exposed a runtime.
# For Eg: docker run --expose=9090
# Or from the kubernetes manifest YAML of pod (spec.container.ports...) configuration.
# EXPOSE 9090

CMD ["java", "-jar", "/app/microservice-one.jar"]
