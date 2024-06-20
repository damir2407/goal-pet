FROM openjdk:17-jdk-slim
ADD build/libs/goal-pet-0.0.1-SNAPSHOT.jar goal-pet.jar
CMD ["java", "-jar", "goal-pet.jar"]