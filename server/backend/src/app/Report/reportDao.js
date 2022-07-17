async function insertReport(connection, insertReportParams) {


    const insertReportQuery = `
    
        INSERT INTO Report(reportLocation, reportDate, reportContent, nickName, lat, lon)
        VALUES (?,?,?,?,?,?);
    
    `;

    const [insertReportRow] = await connection.query(insertReportQuery, insertReportParams);
    console.log("reportDao.js");
    return insertReportRow;
};

async function getReport(connection){
    const getReportQuery = `
        SELECT *
        FROM Report

    `;

    const [reportInfoRow] = await connection.query(getReportQuery);
    console.log(reportInfoRow);
    return reportInfoRow;
}

module.exports = {
    insertReport,
    getReport
};
