# 환경 변수 설정
- 프로젝트 최상위 디렉토리에 `.env` 생성
- 필요한 변수명 작성
  ```
  BACKEND_API_URL=API_주소
  ```
- `main.js`에서 `import` 하여 사용
  - `import.meta.env.BACKEND_API_URL`