ARG BUILD_IMAGE=maven:3.9.2-amazoncorretto-20
ARG RUNTIME_IMAGE=amazoncorretto:20-alpine3.17

##########################################################
# Build Application
##########################################################

FROM ${BUILD_IMAGE} as build
WORKDIR /config-updates-management-job
COPY src ./src
COPY pom.xml ./

RUN mvn clean install


##########################################################
# Run Application
##########################################################
FROM ${RUNTIME_IMAGE}
COPY --from=build /config-updates-management-job/target/config-updates-management-job-*.jar /app/config-updates-management-job.jar
CMD ["java", "-jar", "/app/config-updates-management-job.jar"]
