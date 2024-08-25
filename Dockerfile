FROM openjdk:17-jdk

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

COPY build/libs/*.jar dsjs.jar
COPY src/main/resources/OracleCloud src/main/resources/OracleCloud

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dspring.profiles.active=dev -jar /dsjs.jar"]
