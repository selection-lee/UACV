// mock-server.js
const http = require('http');
const sockjs = require('sockjs');

// SockJS 서버 생성
const echo = sockjs.createServer({ prefix:'/sockjs' });
const server = http.createServer();

// 클라이언트 연결 시 이벤트 핸들러
echo.on('connection', function(conn) {
  console.log('Client connected');
  
  // 5초마다 메시지 전송
  setInterval(() => {
    conn.write(JSON.stringify({ message: '테스트 신호입니다!' }));
  }, 5000);
});

// SockJS 핸들러를 HTTP 서버에 설치
echo.installHandlers(server, { prefix:'/sockjs' });

// 서버 시작
server.listen(8081, '0.0.0.0', () => {
  console.log('Mock SockJS server running on http://localhost:8081');
});

// CORS 설정을 위한 미들웨어 (필요한 경우)
server.on('request', (req, res) => {
  if (req.method === 'OPTIONS') {
    res.writeHead(200, {
      'Access-Control-Allow-Origin': 'http://localhost:3000', // 프론트엔드 서버 주소에 맞게 수정
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type',
      'Access-Control-Allow-Credentials': 'true'
    });
    res.end();
  }
});




// // mock-server.js
// const WebSocket = require('ws');
// const http = require('http');

// // HTTP 서버 생성
// const server = http.createServer((req, res) => {
//     if (req.method === 'OPTIONS') {
//       res.writeHead(200, {
//         // 'Access-Control-Allow-Origin': '*',
//         'Access-Control-Allow-Origin': 'http://localhost:3000', // 특정 도메인 허용
//         'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
//         'Access-Control-Allow-Headers': 'Content-Type',
//         'Access-Control-Allow-Credentials': 'true' // credentials 허용
//       });
//       res.end();
//       return;
//     }
//     res.writeHead(200, {
//       'Content-Type': 'text/event-stream', // MIME 타입 설정
//       'Access-Control-Allow-Origin': 'http://localhost:3000', // 특정 도메인 허용
//       'Access-Control-Allow-Credentials': 'true' // credentials 허용
//     });
//     res.end();
//   });

// // WebSocket 서버 생성
// const wss = new WebSocket.Server({ server });

// wss.on('connection', ws => {
//   console.log('Client connected');
//   setInterval(() => {
//     ws.send(JSON.stringify({ message: '테스트 신호입니다!' }));
//   }, 5000);
// });

// server.listen(8081, () => {
//   console.log('Mock WebSocket server running on ws://localhost:8081');
// });
