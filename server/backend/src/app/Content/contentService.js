const {logger} = require("../../../config/winston");
const {pool} = require("../../../config/database");
const secret_config = require("../../../config/secret");
const userProvider = require("./contentProvider");
const userDao = require("./contentDao");
const baseResponse = require("../../../config/baseResponseStatus");
const {response} = require("../../../config/response");
const {errResponse} = require("../../../config/response");

const jwt = require("jsonwebtoken");
const crypto = require("crypto");
const {connect} = require("http2");

// Service: Create, Update, Delete 비즈니스 로직 처리

// 공공데이터 자전거 사고다발지역 업데이트
exports.updateBike = async function (total_array) {
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        const dropBike = await contentDao.dropBike(connection);
        const updateBike = await contentDao.updateBike(connection, total_array);
        connection.release()

        return response(baseResponse.SUCCESS);

    } catch (err) {
        logger.error(`App - editUser Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    }
};