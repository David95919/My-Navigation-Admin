FROM openjdk:17-jdk-slim

WORKDIR /app

COPY nav-server/target/nav-server-1.0-SNAPSHOT.jar /app

EXPOSE 80
EXPOSE 8080

ENV MYSQL_HOST="localhost"
ENV MYSQL_PORT="3306"
ENV MYSQL_DATABASE="navigation"
ENV MYSQL_USERNAME="root"
ENV MYSQL_PASSWORD="123456"

ENV REDIS_HOST="localhost"
ENV REDIS_PORT="6379"
ENV REDIS_PASSWORD="123456"
ENV REDIS_DATABASE="0"

ENV SECRETKEY="yourSecretKey"
ENV ADMINTTL="36000000"


CMD ["sh", "-c", "java -jar nav-server-1.0-SNAPSHOT.jar \
     --one.moonx.datasource.host=${MYSQL_HOST} \
     --one.moonx.datasource.port=${MYSQL_PORT} \
     --one.moonx.datasource.username=${MYSQL_USERNAME} \
     --one.moonx.datasource.password=${MYSQL_PASSWORD} \
     --one.moonx.redis.host=${REDIS_HOST} \
     --one.moonx.redis.port=${REDIS_PORT} \
     --one.moonx.redis.password=${REDIS_PASSWORD} \
     --one.moonx.redis.database=${REDIS_DATABASE} \
     --one.moonx.jwt.adminSecretKey=${SECRETKEY} \
     --one.moonx.jwt.adminTtl=${ADMINTTL} \
     "]