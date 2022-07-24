const {logger} = require("../../../config/winston");
const {pool} = require("../../../config/database");
const secret_config = require("../../../config/secret");
const contentProvider = require("./contentProvider");
const contentDao = require("./contentDao");
const baseResponse = require("../../../config/baseResponseStatus");
const {response} = require("../../../config/response");
const {errResponse} = require("../../../config/response");

// Service: Create, Update, Delete 비즈니스 로직 처리

// 자전거 사고다발지역 업데이트
exports.updateBike = async function (total_array) {
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        const dropBike = await contentDao.dropBike(connection);
        const updateBike = await contentDao.updateBike(connection, total_array);
        connection.release()

        return response(baseResponse.SUCCESS);

    } catch (err) {
        logger.error(`App - editContent Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    }
};

// 의료기관 정보 업데이트
exports.updateMedical = async function (result_array) {
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        const updateMedical = await contentDao.updateMedical(connection, result_array);
        connection.release();

        return response(baseResponse.SUCCESS);
    } catch (err) {
        logger.error(`App - editContent Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    }
}

// 의료기관 테이블 비우기
exports.dropMedical = async function () {
    try {
        const connection = await pool.getConnection(async (conn) => conn);
        const dropMedical = await contentDao.dropMedical(connection);
        connection.release();

        return;
    } catch (err) {
        logger.error(`App - editContent Service error\n: ${err.message}`);
        return errResponse(baseResponse.DB_ERROR);
    };
};