const jwtMiddleware = require("../../../config/jwtMiddleware");
const baseResponse = require("../../../config/baseResponseStatus");

const reportProvider = require("../../app/Report/reportProvider");
const reportService = require("../../app/Report/reportService");

const { response, errResponse } = require("../../../config/response");
const logger = require("../../../config/winston");
const crypto = require("crypto");


/*
    API No. 4.0
    API Name: 제보 생성 API
    [POST] /reports
*/

exports.postReport = async function(req, res){
    const { reportLocation, reportDate, reportContent, nickName, lat, lon, reportImage } = req.body;

    //validation 처리

    console.log("controller.js");
    //create 작업을 위해 내용들 service로 전송
    const createReportResponse = await reportService.createReport(reportLocation, reportDate, reportContent, nickName, lat, lon, reportImage);

    return res.send(baseResponse.SUCCESS);
}


/*
    API No. 4.1
    API Name: 제보 조회 API
    [GET] /report
*/

exports.getReport = async function(req, res){
    
    //validation 처리
    console.log("controller, getReport");
    const reportInfo = await reportProvider.getReportInfo();

    return res.send(response(baseResponse.SUCCESS,reportInfo));
}


/*
    API No. 4.2
    API NAme: 제보 수정 요청 API
    [PATCH] /report
*/

exports.patchReport = async function(req, res){
    //validation 처리
    console.log("reportController, patchReport");
    
    const reportIdx = req.params.reportIdx;
  
    const {wrong} = req.body;
    
    const editReportResponse = await reportService.editWrongReport(reportIdx, wrong);

    return res.send(response(editReportResponse));

}
