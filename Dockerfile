FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test.smurd_bot
ENV BOT_TOKEN=5395640602:AAGTvG09ckXEcCCg4kxIsu8A0y1OEI-myBU
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]