module.exports = function(app){
    const content = require('./contentController');
    const jwtMiddleware = require('../../../config/jwtMiddleware');

    // 0. 테스트 API
    app.get('/test', content.getTest)

    // 1. 병원 정보 받아오기
    app.get('/total', content.getAll);
};