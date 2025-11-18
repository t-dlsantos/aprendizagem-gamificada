FROM eclipse-temurin:21-jdk

WORKDIR /aprendizagem_gamificada

COPY target/*.jar /aprendizagem_gamificada/aprendizagem_gamificada-0.0.1-SNAPSHOT.jar

EXPOSE 8585

CMD ["java", "-jar", "aprendizagem_gamificada-0.0.1-SNAPSHOT.jar"]