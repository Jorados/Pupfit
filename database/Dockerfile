# MySQL 최신 버전 이미지를 사용
FROM mysql:latest

# 환경 변수 설정: 데이터베이스, 사용자, 루트 비밀번호, 사용자 비밀번호
ENV MYSQL_DATABASE=pupfit
ENV MYSQL_USER=myuser
ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_PASSWORD=mypassword

# 포트 3306 노출 (MySQL 기본 포트)
EXPOSE 3306

# 초기화 SQL 파일 복사
COPY ./init.sql /docker-entrypoint-initdb.d/
