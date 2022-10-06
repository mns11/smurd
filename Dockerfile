FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test.smurd_bot
ENV BOT_TOKEN=5395640602:AAGTvG09ckXEcCCg4kxIsu8A0y1OEI-myBU
ENV BOT_DB_USERNAME=smurd_db_user
ENV BOT_DB_PASSWORD=smurd_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]