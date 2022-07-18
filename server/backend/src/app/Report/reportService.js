const {pool} = require("../../../config/database");
const baseResponse = require("../../../config/baseResponseStatus");
const {response, errResponse} = require("../../../config/response");
const reportDao = require("./reportDao");
const reportProvider = require("./reportProvider"); 
const { logger } = require("../../../config/winston");

exports.createReport = async function(reportLocation, reportDate, reportContent, nickName, lat, lon){

    console.log("reportService.js");
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        
        const insertReportParams = [reportLocation, reportDate, reportContent, nickName, lat, lon];
        const reportResult = await reportDao.insertReport(connection, insertReportParams);
        connection.release();

        return reportResult;
    } catch (err) {
        logger.error(`App - report Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    }
}