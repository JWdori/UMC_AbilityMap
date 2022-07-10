async function insertReport(connection, insertReportParams) {


    const insertReportQuery = `
    
        INSERT INTO Report(reportLocation, reportDate, reportContent, nickName)
        VALUES (?,?,?,?);
    
    `;

    const [insertReportRow] = await connection.query(insertReportQuery, insertReportParams);
    console.log("reportDao.js");
    return insertReportRow;
};

module.exports = {
    insertReport
};