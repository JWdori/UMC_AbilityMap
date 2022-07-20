const {pool} = require("../../../config/database");
const baseResponse = require("../../../config/baseResponseStatus");
const {response, errResponse} = require("../../../config/response");
const reportDao = require("./reportDao");
const reportProvider = require("./reportProvider"); 
const { logger } = require("../../../config/winston");

exports.createReport = async function(reportLocation, reportDate, reportContent, nickName){

    console.log("reportService.js");
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        
        const insertReportParams = [reportLocation, reportDate, reportContent, nickName];
        const reportResult = await reportDao.insertReport(connection, insertReportParams);
        connection.release();

        return reportResult;
    } catch (err) {
        logger.error(`App - report Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    }
}


exports.editWrongReport = async function (reportIdx, wrong) {
    const connection = await pool.getConnection(async (conn) => conn);

    //const findReportLocation = await reportDao.findReport(connection);
    
    try {
        const editWrongReportParams = [wrong, reportIdx];
        const editWrongReportResult = await reportDao.updateWrongReport(connection, editWrongReportParams);
    
        return response(baseResponse.SUCCESS);
    } catch (err) {
        console.log(`App - editWrongReport Service error\n: ${err.message}`);
    
        return errResponse(baseResponse.DB_ERROR);
    } finally {
        connection.release();
    }
}
