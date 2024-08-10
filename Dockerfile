## 베이스 이미지 설정
## 이 방식의 단점
## 코드를 수정하게 되면 직접 jar파일을 만들어줘야한다. 그리고 이미지를 다시 빌드하고 실행해야한다.
## 기존에 slim 베이스이미지를 사용해서 용량은 적은편이었지만, slim이 아니라면 700~800MB가 나올거임.
#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
## 빌드된 Spring Boot JAR 파일을 복사
#COPY build/libs/Pupfit-0.0.1-SNAPSHOT.jar pupfit.jar
#
## 포트 설정
#EXPOSE 8080
#
## JAR 파일 실행
#CMD ["java", "-jar", "pupfit.jar"]



# 멀티스테이징 빌드 테스트
## 이 방식의 장점
## 정상적인 베이스이미지를 사용(약 700~800)하여 내 애플리케이션의 코드와 설정을 WORKDIR 로 복사해서 빌드를 진행한다.
## 핵심은 처음 FROM에서 빌드된 jar파일을 이용해 딱 실행환경만 최소한으로 가지고 있는 slim이미지를 사용해서 멀티 스테이지 빌드를 진행한다.
## 이렇게 되면 용량도 slim(약 400MB) 정도 이며, 코드를 수정 후 직접 빌드할 필요가 없다.
## 하지만 아직 터미널에서 직접 front,back,db 이미지를 일일이 run 시키는 과정은 귀찮다. --> docker compose가 필요하다.
FROM gradle:7-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

COPY . /app

RUN gradle clean bootJar --no-daemon


# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 Spring Boot JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar /app/pupfit.jar

# 포트 설정
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "pupfit.jar"]










