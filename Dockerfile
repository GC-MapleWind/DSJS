FROM openjdk:17-jdk

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

COPY build/libs/*.jar dsjs.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /dsjs.jar"]
