const { pool } = require("../../../config/database");
const { logger } = require("../../../config/winston");

const contentDao = require("./contentDao");

// Provider: Read 비즈니스 로직 처리

// 모든 정보 받아오기
exports.getAll = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getAllListResult = await contentDao.getAllList(connection);
  connection.release();

  return getAllListResult;
};

// 자전거 사고 다발 지역 받아오기
exports.getBikeData = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getBikeDataResult = await contentDao.getBikeData(connection);
  connection.release();

  return getBikeDataResult;
}

// 휠체어 충전기 위치 받아오기
exports.getChargerLocation = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getChargerLocationResult = await contentDao.getChargerLocation(connection);
  connection.release();

  return getChargerLocationResult;
}