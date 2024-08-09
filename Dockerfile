# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 Spring Boot JAR 파일을 복사
COPY build/libs/Pupfit-0.0.1-SNAPSHOT.jar pupfit.jar

# 포트 설정
EXPOSE 8080

# JAR 파일 실행
CMD ["java", "-jar", "pupfit.jar"]