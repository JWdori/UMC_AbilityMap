const {pool} = require("../../../config/database");
const baseResponse = require("../../../config/baseResponseStatus");
const {response, errResponse} = require("../../../config/response");
const reportDao = require("./reportDao");
const { logger } = require("../../../config/winston");

exports.getReportInfo = async function(){
    const connection = await pool.getConnection(async (conn) => conn);
    const reportResult = await reportDao.getReport(connection);

    connection.release();

    return reportResult;
}

exports.getWrongReportInfo = async function(){
    const connection = await pool.getConnection(async (conn) => conn);
    const reportResult = await reportDao.getWrongReport(connection);

    connection.release();

    return reportResult;
}
