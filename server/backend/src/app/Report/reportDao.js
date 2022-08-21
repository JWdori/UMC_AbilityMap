async function insertReport(connection, insertReportParams) {


    const insertReportQuery = `
    
        INSERT INTO Report(reportLocation, reportDate, reportContent, nickName, lat, lon, reportImage)
        VALUES (?,?,?,?,?,?,?);
    
    `;

    const [insertReportRow] = await connection.query(insertReportQuery, insertReportParams);
    console.log("reportDao.js");
    return insertReportRow;
};

async function getReport(connection){
    const getReportQuery = `
        SELECT *
        FROM Report
        WHERE wrong = -1 OR wrong = 1 OR wrong = 2 OR wrong = 3 OR wrong = 4

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

async function getWrongReport(connection){
    const getWrongReportQuery = `
        SELECT * 
        FROM Report
        WHERE wrong > -1
    `;

    const [wrongReportInfoRow] = await connection.query(getWrongReportQuery);
    console.log(wrongReportInfoRow);
    return wrongReportInfoRow;
}

module.exports = {
    insertReport,
    getReport,
    updateWrongReport,
    getWrongReport
};
