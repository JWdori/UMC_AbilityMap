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

// 자전거 사고다발 지역 데이터 베이스 비우기
async function dropBike(connection) {
  const dropBikeQuery = `
                TRUNCATE TABLE bike;
                `;
  
  const dropBike = await connection.query(dropBikeQuery);

  return;
}

// 자전거 사고다발 지역 업데이트
async function updateBike(connection, total_array) {
  for (let i = 0; i<total_array.length; i++) {
    let array = [];
    array[0] = total_array[i].lat;;
    array[1] = total_array[i].lon;
    const updateBikeQuery = `
                INSERT INTO bike (lat, lon)
                VALUES (?, ?);
                `;
    const updateBike = await connection.query(updateBikeQuery, array);
  };
  
  return;
}

// 공공데이터 자전거 사고다발 지역 받아오기
async function getBikeData(connection) {
  const getBikeDataQuery = `
                SELECT *
                FROM bike;
                `;
  const [getBikeData] = await connection.query(getBikeDataQuery);

  return getBikeData;
}

// 휠체어 충전기 위치 받아오기
async function getChargerLocation(connection) {
  const getChargerLocationQuery = `
                SELECT *
                FROM charger;
                `;
  const [getChargerLocation] = await connection.query(getChargerLocationQuery);

  return getChargerLocation;
}

// 급경사지 위치 받아오기
async function getRampLocation(connection) {
  const getRampLocationQuery = `
                SELECT *
                FROM ramp;
                `;
  const [getRampLocation] = await connection.query(getRampLocationQuery);

  return getRampLocation;
}

// 학교 휠체어 경사로 위치 받아오기
async function getSchool(connection) {
  const getSchoolQuery = `
              SELECT *
              FROM school;
              `;
  const [getSchool] = await connection.query(getSchoolQuery);

  return getSchool;
}

// 지하철역 엘리베이터 위치 받아오기
async function getElevator(connection) {
  const getElevatorQuery = `
              SELECT *
              FROM elevator;
              `;
  const [getElevator] = await connection.query(getElevatorQuery);

  return getElevator;
}

module.exports = {
  getAllList,
  updateBike,
  dropBike,
  getBikeData,
  getChargerLocation,
  getRampLocation,
  getSchool,
  getElevator
};
