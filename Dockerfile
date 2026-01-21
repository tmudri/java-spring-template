# Builder stage: build the spring-boot fat jar
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace

# copy only pom first to leverage Docker layer caching for dependencies
COPY . .
# build, skipping tests to avoid test-time resolution failures inside container
RUN mvn -B -DskipTests package

# Runtime stage: minimal JRE and non-root user
FROM eclipse-temurin:21-jre
ARG APP_USER=appuser
ARG APP_GROUP=appuser

# create non-root user and home
RUN groupadd -r ${APP_GROUP} && useradd -r -g ${APP_GROUP} -m -d /home/${APP_USER} -s /sbin/nologin ${APP_USER}

WORKDIR /home/${APP_USER}

# copy jar from builder and fix ownership
COPY --from=builder /workspace/target/*.jar app.jar
RUN chown ${APP_USER}:${APP_GROUP} app.jar

USER ${APP_USER}

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/appuser/app.jar"]