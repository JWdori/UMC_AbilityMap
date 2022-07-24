module.exports = function(app) {
    const report = require('./reportController');

    //3.0 제보 생성 API 
    app.post('/report', report.postReport);
    
     //3.1 제보 조회 API
    app.get('/report', report.getReport);
    
    //3.2 제보 수정 요청 API
    app.patch('/report/:reportIdx',report.patchReport);


};