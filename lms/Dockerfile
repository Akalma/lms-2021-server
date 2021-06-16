FROM mcr.microsoft.com/java/jre:8-zulu-alpine
ARG JAR_FILE=target/lms.jar
COPY ${JAR_FILE} lms.jar
ENTRYPOINT ["java","-jar","/lms.jar"]