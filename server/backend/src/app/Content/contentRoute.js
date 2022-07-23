module.exports = function(app){
    const content = require('./contentController');
    const jwtMiddleware = require('../../../config/jwtMiddleware');

    // 0. 테스트 API
    app.get('/test', content.getTest)

    // 1. 병원 정보 받아오기
    app.get('/total', content.getAll);
    
    // 1.7 휠체어 리프트 데이터 받아오기
    app.get("/get/lift", content.getLift);
    
    // 2.1 공공 데이터 자전거 사고 다발지역 정보 받아오기
    app.get("/get/bike", content.getBike);

    // 2.2 휠체어 급속 충전기 위치
    app.get("/get/charger", content.getCharger);

    // 2.3 급경사지 위치
    app.get('/get/ramp', content.getRamp);

    // 2.4 학교 휠체어 경사로 위치 받아오기
    app.get("/get/school", content.getSchool);

    // 2.5 지하철 역 엘리베이터 위치 받아오기
    app.get("/get/elevator", content.getElevator);
    
    // 2.6 병원 데이터 받아오기
    app.get("/get/medical", content.getMedical);

    // 2.7 복지센터 데이터 받아오기
    console.log("route.js");
    app.get("/get/welfare", content.getWelfare);

    // 3.0 공공 데이터 자전거 사고 다발지역 정보 업데이트 -> 클라이언트 측 사용 X, 관리자가 데이터 업데이트 할 때 사용 예정
    app.get('/update/bike', content.updateBike);
};
