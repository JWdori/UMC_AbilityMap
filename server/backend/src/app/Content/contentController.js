const jwtMiddleware = require("../../../config/jwtMiddleware");
const contentProvider = require("./contentProvider");
const contentService = require("./contentService");
const baseResponse = require("../../../config/baseResponseStatus");
const {response, errResponse} = require("../../../config/response");

const regexEmail = require("regex-email");
const {emit} = require("nodemon");

/**
 * API No. 0
 * API Name : 테스트 API
 * [GET] /app/test
 */
exports.getTest = async function (req, res) {
    return res.send(response(baseResponse.SUCCESS))
};

/**
 * API No. 1
 * API Name: 모든 위치 받아오기
 * [GET] /total
 */
exports.getAll = async function (req, res) {
    const getAllHospital = await contentProvider.getAll();

    return res.send(response(baseResponse.SUCCESS, getAllHospital));
};