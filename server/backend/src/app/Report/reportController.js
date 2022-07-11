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
    const { reportLocation, reportDate, reportContent, nickName } = req.body;

    //validation 처리

    console.log("controller.js");
    //create 작업을 위해 내용들 service로 전송
    const createReportResponse = await reportService.createReport(reportLocation, reportDate, reportContent, nickName);

    return res.send(baseResponse.SUCCESS);
}