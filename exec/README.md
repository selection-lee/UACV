# 빌드 및 배포 가이드
## 사용된 프레임워크 및 버전 정보
### EC2 인스턴스에서 작동하는 서비스
1. Docker
    - v27.1.1
2. Docker Compose
    - v2.29.1

### Docker Container로 작동하는 서비스
1. [Caddy Docker Proxy](https://github.com/lucaslorentz/caddy-docker-proxy)
    - Nginx를 대체하여 작동하는 프록시 서버
    - Base Image: `2.9.1-alpine`
2. MySQL
    - Base Image: `lts`
3. MongoDB
    - Base Image: `mongodb-community-server:7.0-ubuntu2204`
4. RabbitMQ
    - Base Image: `3.13.4-management-alpine`
5. SpringBoot
    - 로컬 빌드
    - Java 17 필요
    - Base Image: `eclipse-temurin:17-alpine`
6. Vue 3 with Vuetify 3
    - 로컬 빌드
    - Vue: v3.4.31
    - Vuetify: v3.6.14
    - Base Image:
        - Build: `node:lts-alpine`
        - Deploy: `nginx:stable-alpine`

### 사용된 IDE
1. VSCode
    - v1.92.1
2. Intellij IDEA Ultimate
    - v2024.1.4

## 빌드 환경변수
- 각 프로젝트 최상위 디렉토리에 `.env` 생성
- 필요한 변수명 작성

### SpringBoot (`uacv_backend`)
  ```
  PORT=포트번호
  MONGO_URI=mongodb://{HOST}:{PORT}
  MONGO_DBNAME=Document_이름
  MONGO_USERNAME=아이디
  MONGO_PASSWORD=비밀번호
  MYSQL_DRIVER=com.mysql.cj.jdbc.Driver
  MYSQL_URL=jdbc:mysql://{HOST}:{PORT}/{DB_이름}
  MYSQL_USERNAME=아이디
  MYSQL_PASSWORD=비밀번호
  RABBITMQ_HOST=RabbitMQ_호스트
  RABBITMQ_PORT=RabbitMQ_포트
  RABBITMQ_USERNAME=아이디
  RABBITMQ_PASSWORD=비밀번호
  JWT_SECRET_KEY=JWT_키
  LOG_LEVEL=로그 레벨 (기본 값: INFO)
  ```

### Vue (`uacv_frontend`)
  ```
  VITE_BACKEND_API_URL=API_주소
  VITE_SOCKET_URL=웹소켓_주소
  VITE_MQTT_HOST=MQTT_주소
  VITE_MQTT_USERNAME=MQTT_아이디
  VITE_MQTT_PASSWORD=MQTT_비밀번호
  ```

## 배포 특이사항
- SpringBoot 및 Vue 컨테이너는 Docker Hub에 올라있지 않으며 로컬에서 빌드하여 사용
- Caddy Docker Proxy 구현 특성 상 각 서비스별 리버스 프록시 정보는 Docker Compose 파일 내 Label으로 작성되어 있음
    - `caddy: i11c102.p.ssafy.io` 구문을 서비스할 주소에 맞춰 수정
- 다음 배포 순서를 따라 배포

### SpringBoot Build
- `uacv_backend` 디렉토리로 이동 후 아래 명령어 실행
    - `docker build -t spring:latest .`

### Vue Build
- `uacv_frontend` 디렉토리로 이동 후 아래 명령어 실행
    - `docker build -t vue:latest .`

### RabbitMQ 초기 설정
- `dump/3_RabbitMQ_240816` 디렉토리 내의 `rabbitmq.conf`를 Docker 컨테이너에 Mount 필요
- `compose` 디렉토리 내의 `default_infra.yml` Compose 파일의 RabbitMQ 마운트 설정 변경
    - `{Git_Clone_Dir}/exec/dump/3_RabbitMQ_240816/rabbitmq.conf:/etc/rabbitmq/rabbitmq.conf`
    - `{Git_Clone_Dir}/exec/dump/3_RabbitMQ_240816/default.json:/etc/rabbitmq/default.json`

### Docker 컨테이너 Deploy
1. `compose` 디렉토리로 이동
2. 기본 인프라 Compose Deploy
    - `docker compose -f default_infra.yml up -d`
3. UACV Compose Deploy
    - `docker compose up -d`
