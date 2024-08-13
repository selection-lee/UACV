# 환경 변수 설정
- 프로젝트 최상위 디렉토리에 `.env` 생성
- 필요한 변수명 작성
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
- 필요한 환경 변수는 `@Value` Annotation 사용하여 접근 가능