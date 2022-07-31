FROM openjdk:8
COPY ./target/TutorialService-0.0.1.jar  TutorialService-0.0.1.jar
CMD ["java", "-jar","TutorialService-0.0.1.jar"]