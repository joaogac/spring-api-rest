# https://www.docker.com/blog/9-tips-for-containerizing-your-spring-boot-code/
# docker build -t spring-api-rest .
FROM amazoncorretto:17-alpine-jdk as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM amazoncorretto:17-alpine
RUN addgroup springgroup; adduser --ingroup springgroup --disabled-password spring
USER spring
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]