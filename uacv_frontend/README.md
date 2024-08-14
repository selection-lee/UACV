# 환경 변수 설정
- 프로젝트 최상위 디렉토리에 `.env` 생성
- 필요한 변수명 작성
  ```
  VITE_BACKEND_API_URL=API_주소
  VITE_SOCKET_URL=맵핑_웹소켓_주소
  VITE_MQTT_HOST=MQTT_주소
  VITE_MQTT_USERNAME=MQTT_아이디
  VITE_MQTT_PASSWORD=MQTT_비밀번호
  ```
  ```javascript
  // 로컬 빌드일 경우
  VITE_BACKEND_API_URL="http://localhost:8080/api"
  VITE_SOCKET_URL="ws://localhost:8080/socket"
  ```
- 필요한 위치에서 `import` 하여 사용
  - `import.meta.env.VITE_BACKEND_API_URL`
  - `import.meta.env.VITE_SOCKET_URL`