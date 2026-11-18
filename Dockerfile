FROM maven:3.9-eclipse-temurin-21 AS build
ARG SPRING_PROFILE=prod
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN case "$SPRING_PROFILE" in \
        dev|test|prod) ;; \
        *) echo "SPRING_PROFILE must be dev, test, or prod" >&2; exit 1 ;; \
    esac \
    && mvn --batch-mode --no-transfer-progress -P"$SPRING_PROFILE" verify

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/target/sso-server-*.jar /app/sso-server.jar
USER 10001:10001
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "/app/sso-server.jar"]
CMD ["--spring.jpa.properties.jakarta.persistence.schema-generation.scripts.action=none"]
