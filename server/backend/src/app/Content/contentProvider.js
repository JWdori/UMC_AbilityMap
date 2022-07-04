const { pool } = require("../../../config/database");
const { logger } = require("../../../config/winston");

const contentDao = require("./contentDao");

// Provider: Read 비즈니스 로직 처리

exports.getAll = async function () {
  const connection = await pool.getConnection(async (conn) => conn);
  const getAllListResult = await contentDao.getAllList(connection);
  connection.release();

  return getAllListResult;
};