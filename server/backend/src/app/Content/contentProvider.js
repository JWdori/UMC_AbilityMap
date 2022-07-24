const { pool } = require("../../../config/database");
const { logger } = require("../../../config/winston");

const contentDao = require("./contentDao");

// Provider: Read 비즈니스 로직 처리

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

// 급경사지 위치 받아오기
exports.getRampLocation = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getRampLocationResult = await contentDao.getRampLocation(connection);
  connection.release();

  return getRampLocationResult;
}

// 학교 휠체어 경사로 받아오기
exports.getSchool = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getSchoolResult = await contentDao.getSchool(connection);
  connection.release();

  return getSchoolResult;
}

// 지하철 엘리베이터 위치 받아오기
exports.getElevator = async function() {
  const connection = await pool.getConnection(async (conn) => conn);
  const getElevatorResult = await contentDao.getElevator(connection);
  connection.release();

  return getElevatorResult;
}

// 약국 + 병원 + 의원 + 보건소 데이터 받아오기
exports.getMedical = async function() {
  const connection = await pool.getConnection(async (conn) => conn);
  const getMedicalResult = await contentDao.getMedical(connection);
  connection.release();

  return getMedicalResult;
}

// 복지센터 위치 받아오기
exports.getWelfare = async function() {
  const connection = await pool.getConnection(async (conn) => conn);
  console.log("contentProvider-getWelfare");
  const getWelfareResult = await contentDao.getWelfare(connection);
  connection.release();

  return getWelfareResult;
}

// 휠체어 리프트 위치 받아오기
exports.getLift = async function() {
  const connection = await pool.getConnection(async (conn) => conn);
  const getLiftResult = await contentDao.getLift(connection);
  connection.release();

  return getLiftResult;
}