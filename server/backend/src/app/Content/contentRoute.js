module.exports = function(app){
    const content = require('./contentController');
    const jwtMiddleware = require('../../../config/jwtMiddleware');

    // 0. 테스트 API
    app.get('/test', content.getTest);

    
    // 1.0 자전거 사고 다발지역 정보 받아오기
    app.get("/get/bike", content.getBike);

    // 1.1 휠체어 급속 충전기 위치 받아오기
    app.get("/get/charger", content.getCharger);

    // 1.2 급경사지 위치 받아오기
    app.get('/get/ramp', content.getRamp);

    // 1.3 학교 휠체어 경사로 위치 받아오기
    app.get("/get/school", content.getSchool);

    // 1.4 지하철 역 엘리베이터 위치 받아오기
    app.get("/get/elevator", content.getElevator);
    
    // 1.5 약국 + 병원 + 의원 + 보건소 데이터 받아오기
    app.get("/get/medical", content.getMedical);

    // 1.6 복지센터 데이터 받아오기
    app.get("/get/welfare", content.getWelfare);

    // 1.7 휠체어 리프트 데이터 받아오기
    app.get("/get/lift", content.getLift);
    

    // 2.0 공공 데이터 자전거 사고 다발지역 정보 업데이트 -> 클라이언트 측 사용 X, 관리자가 데이터 업데이트 할 때 사용 예정
    // app.get('/update/bike', content.updateBike);

    // 2.1 공공 데이터 의료기관 정보 업데이트 -> 클라이언트 측 사용 X, 관리자가 데이터 업데이트 할 때 사용 예정
    // app.get("/update/medical", content.updateMedical);
};