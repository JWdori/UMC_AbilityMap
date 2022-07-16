module.exports = function(app) {
    const report = require('./reportController');

    //4.0 제보 생성 API 
    console.log("Route.js");
    app.post('/report', report.postReport);
    
     //4.1 제보 조회 API
    app.get('/report', report.getReport);

};
