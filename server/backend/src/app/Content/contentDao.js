const { getAll } = require("./contentProvider");

// 병원 가져오기
async function getAllList(connection) {
  const getAllListQuery = `
                SELECT test.name, test.lat, test.lon, test.tel, test.location, Time.week, Time.weekend, Time.holiday, Time.var
                FROM test
                INNER JOIN Time ON test.idx=Time.idx;
                `;
  const [getAllList] = await connection.query(getAllListQuery);
  return getAllList;
}

module.exports = {
  getAllList
};
