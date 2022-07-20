async function insertReport(connection, insertReportParams) {


    const insertReportQuery = `
    
        INSERT INTO Report(reportLocation, reportDate, reportContent, nickName)
        VALUES (?,?,?,?);
    
    `;

    const [insertReportRow] = await connection.query(insertReportQuery, insertReportParams);
    console.log("reportDao.js");
    return insertReportRow;
};

async function getReport(connection){
    const getReportQuery = `
        SELECT reportLocation, reportDate, reportContent, nickName
        FROM Report

    `;

    const [reportInfoRow] = await connection.query(getReportQuery);
    console.log(reportInfoRow);
    return reportInfoRow;
}

async function updateWrongReport(connection, editWrongReportParams){

    const updateWrongReportQuery = `
        UPDATE Report
        SET wrong = ?
        WHERE reportIdx = ?;
    `;

    const updateWrongReportRow = await connection.query(updateWrongReportQuery, editWrongReportParams);

    return updateWrongReportRow;
}

module.exports = {
    insertReport,
    getReport,
    updateWrongReport
};
