module.exports = function(app){
    const content = require('./contentController');
    const jwtMiddleware = require('../../../config/jwtMiddleware');

    // 0. 테스트 API
    app.get('/test', content.getTest)

    // 1. 병원 정보 받아오기
    app.get('/total', content.getAll);
    
    // 2.1 공공 데이터 자전거 사고 다발지역 정보 받아오기
    app.get("/get/bike", content.getBike);

    // 2.2 휠체어 급속 충전기 위치
    app.get("/get/charger", content.getCharger);

    // 3.0 공공 데이터 자전거 사고 다발지역 정보 업데이트 -> 클라이언트 측 사용 X, 관리자가 데이터 업데이트 할 때 사용 예정
    app.get('/update/bike', content.updateBike);
};