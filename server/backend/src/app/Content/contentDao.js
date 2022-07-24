const { getAll } = require("./contentProvider");

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

// 자전거 사고다발 지역 받아오기
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

// 엘리베이터 위치 받아오기
async function getElevator(connection) {
  const getElevatorQuery = `
              SELECT *
              FROM elevator;
              `;
  const [getElevator] = await connection.query(getElevatorQuery);

  return getElevator;
}

// 의료기관 위치 받아오기
async function getMedical(connection) {
  const getMedicalQuery = `
              SELECT *
              FROM medical;
              `;
  const [getMedical] = await connection.query(getMedicalQuery);

  return getMedical;
}

// 복지센터 위치 받아오기
async function getWelfare(connection) {
  const getWelfareQuery = `
              SELECT *
              FROM Welfare;
              `;
  const [getWelfare] = await connection.query(getWelfareQuery);

  return getWelfare;
}

// 휠체어 리프트 위치 받아오기
async function getLift(connection) {
  const getLiftQuery = `
              SELECT *
              FROM Lift;
              `;

  const [getLift] = await connection.query(getLiftQuery);

  return getLift;
}

// 의료기관 테이블 비우기
async function dropMedical(connection) {
  const dropMedicalQuery = `
              TRUNCATE TABLE medical;
              `;
  
  const dropMedical = await connection.query(dropMedicalQuery);

  return;
}

// 의료기관 정보 저장
async function updateMedical(connection, result_array) {
  const length = result_array.length;

  for (let i = 0; i < length; i++) {

    console.clear()
    console.log(i + " / " + length + " (" + (i/length*100).toFixed(2) + "%)")

    let array = []
    if (result_array[i].dutyDiv == "B" || result_array[i].dutyDiv == "A" || result_array[i].dutyDiv == "R" || result_array[i].dutyDiv == "C" || result_array[i].dutyDiv === undefined) {
    
      if (result_array[i].dutyAddr.indexOf("서울특별시") !== -1) {
        array[0] = result_array[i].dutyAddr
      } else {
        continue
      }

      array[1] = result_array[i].dutyName
      if (array[1] === "중복") {
        continue;
      }
      array[2] = result_array[i].dutyTel1
      if (result_array[i].dutyTime1c !== undefined) {
        array[3] = result_array[i].dutyTime1c
      } else {
        array[3] = "(정보없음)"
      }
      if (result_array[i].dutyTime1s !== undefined) {
        array[4] = result_array[i].dutyTime1s
      } else {
        array[4] = "(정보없음)"
      }
      if (result_array[i].dutyTime2c !== undefined) {
        array[5] = result_array[i].dutyTime2c
      } else {
        array[5] = "(정보없음)"
      }
      if (result_array[i].dutyTime2s !== undefined) {
        array[6] = result_array[i].dutyTime2s
      } else {
        array[6] = "(정보없음)"
      }
      if (result_array[i].dutyTime3c !== undefined) {
        array[7] = result_array[i].dutyTime3c
      } else {
        array[7] = "(정보없음)"
      }
      if (result_array[i].dutyTime3s !== undefined) {
        array[8] = result_array[i].dutyTime3s
      } else {
        array[8] = "(정보없음)"
      }
      if (result_array[i].dutyTime4c !== undefined) {
        array[9] = result_array[i].dutyTime4c
      } else {
        array[9] = "(정보없음)"
      }
      if (result_array[i].dutyTime4s !== undefined) {
        array[10] = result_array[i].dutyTime4s
      } else {
        array[10] = "(정보없음)"
      }
      if (result_array[i].dutyTime5c !== undefined) {
        array[11] = result_array[i].dutyTime5c
      } else {
        array[11] = "(정보없음)"
      }
      if (result_array[i].dutyTime5s !== undefined) {
        array[12] = result_array[i].dutyTime5s
      } else {
        array[12] = "(정보없음)"
      }
      if (result_array[i].dutyTime6c !== undefined) {
        array[13] = result_array[i].dutyTime6c
      } else {
        array[13] = "(정보없음)"
      }
      if (result_array[i].dutyTime6s !== undefined) {
        array[14] = result_array[i].dutyTime6s
      } else {
        array[14] = "(정보없음)"
      }
      if (result_array[i].dutyTime7c !== undefined) {
        array[15] = result_array[i].dutyTime7c
      } else {
        array[15] = "(정보없음)"
      }
      if (result_array[i].dutyTime7s !== undefined) {
        array[16] = result_array[i].dutyTime7s
      } else {
        array[16] = "(정보없음)"
      }
      if (result_array[i].dutyTime8c !== undefined) {
        array[17] = result_array[i].dutyTime8c
      } else {
        array[17] = "(정보없음)"
      }
      if (result_array[i].dutyTime8s !== undefined) {
        array[18] = result_array[i].dutyTime8s
      } else {
        array[18] = "(정보없음)"
      }
      if (result_array[i].wgs84Lat !== undefined || result_array[i].wgs84Lon !== undefined) {
        array[19] = result_array[i].wgs84Lat
        array[20] = result_array[i].wgs84Lon
      } else {
        continue;
      }
    } else {
      continue;
    };
      
    const updateMedicalQuery = `
                INSERT INTO medical (address, name, tel, monc, mono, tuec, tueo, wedc, wedo, thuc, thuo, fric, frio, satc, sato, sunc, suno, holc, holo, lat, lon)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                `;
    const updateMedical = await connection.query(updateMedicalQuery, array);
  };

  return;
}

module.exports = {
  updateBike,
  dropBike,
  getBikeData,
  getChargerLocation,
  getRampLocation,
  getSchool,
  getElevator,
  getMedical,
  getWelfare,
  getLift,
  dropMedical,
  updateMedical
};

