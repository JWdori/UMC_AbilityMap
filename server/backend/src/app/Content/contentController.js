const jwtMiddleware = require("../../../config/jwtMiddleware");
const contentProvider = require("./contentProvider");
const contentService = require("./contentService");
const baseResponse = require("../../../config/baseResponseStatus");
const { response, errResponse } = require("../../../config/response");
const converter = require("xml-js")

const axios = require('axios');

const regexEmail = require("regex-email");
const {emit} = require("nodemon");
const { SUCCESS } = require("../../../config/baseResponseStatus");

/**
 * API No. 0
 * API Name : 테스트 API
 * [GET] /app/test
 */
exports.getTest = async function (req, res) {
    return res.send(response(baseResponse.SUCCESS))
};

/**
 * API No. 1.0
 * API Name : 자전거 사고 다발지역 정보 받아오기
 * [GET] /get/bike
 */
exports.getBike = async function (req, res) {
    const getBikeResult = await contentProvider.getBikeData();

    return res.send(response(baseResponse.SUCCESS,getBikeResult));
};

/**
 * API No. 1.1
 * API Name : 휠체어 급속 충전기 위치 받아오기
 * [GET] /get/charger
 */
exports.getCharger = async function (req, res) {
    const getChargerResult = await contentProvider.getChargerLocation();

    return res.send(response(baseResponse.SUCCESS, getChargerResult));
}

/**
 * API No. 1.2
 * API Name : 급경사지 위치 받아오기
 * [GET] /get/ramp
 */
 exports.getRamp = async function (req, res) {
    const getRampResult = await contentProvider.getRampLocation();

    return res.send(response(baseResponse.SUCCESS, getRampResult));
}

/**
 * API No. 1.3
 * API Name : 학교 휠체어 경사로 위치 받아오기
 * [GET] /get/school
 */
exports.getSchool = async function (req, res) {
    const getSchool = await contentProvider.getSchool();

    return res.send(response(baseResponse.SUCCESS, getSchool));
}

/**
 * API No. 1.4
 * API Name : 지하철 엘리베이터 위치 받아오기
 * [GET] /get/elevator
 */
exports.getElevator = async function (req, res) {
    const getElevator = await contentProvider.getElevator();

    return res.send(response(baseResponse.SUCCESS, getElevator));
}

/*
 * API No. 1.5
 * API Name : 약국 + 병원 + 의원 + 보건소 데이터 받아오기
 * [GET] /get/medical
 */

 exports.getMedical = async function (req, res) {
    const getMedical = await contentProvider.getMedical();

    return res.send(response(baseResponse.SUCCESS, getMedical));
}

/**
 * API No. 1.6
 * API Name : 복지센터 위치 받아오기
 * [GET] /get/welfare
 */

exports.getWelfare = async function (req, res) {
    console.log("contentController-getWelfare");
    const getWelfare = await contentProvider.getWelfare();
    
    return res.send(response(baseResponse.SUCCESS, getWelfare));
}

/*
 * API No. 1.7
 * API Name : 휠체어 리프트 데이터 받아오기
 * [GET] /get/lift
*/
exports.getLift = async function (req, res) {
    const getLift = await contentProvider.getLift();
    
    return res.send(response(baseResponse.SUCCESS, getLift));
}

/**
 * API No. 2.0
 * API Name : 자전거 사고 다발지역 정보 업데이트
 * [GET] /app/getData
 */
 exports.updateBike = async function (req, res) {
    const list1 = ["11680", "11740", "11305", "11500", "11620", "11215", "11530", "11545", "11350", "11320", "11230", "11590", "11440", "11410", "11650", "11200", "11290", "11710", "11470", "11560", "11170", "11380", "11110", "11140", "11260", "26440", "26410", "26710", "26290", "26170", "26260", "26230", "26320", "26530", "26380", "26140", "26500", "26470", "26200", "26110", "26350", "27200", "27290", "27710", "27140", "27230", "27170", "27260", "27110", "28710", "28245", "28200", "28140", "28177", "28237", "28260", "28185", "28720", "28110", "29200", "29155", "29110", "29170", "29140", "30230", "30110", "30170", "30200", "30140", "31140", "31170", "31200", "31710", "31110", "36110", "41820", "41281", "41285", "41287", "41290", "41210", "41610", "41310", "41410", "41570", "41360", "41250", "41190", "41135", "41131", "41133", "41113", "41117", "41111", "41115", "41390", "41273", "41271", "41550", "41173", "41171", "41630", "41830", "41670", "41800", "41370", "41463", "41465", "41461", "41430", "41150", "41500", "41480", "41220", "41650", "41450", "41590"]
    const list2 = ["42150",	"42820",	"42170",	"42230",	"42210",	"42800",	"42830",	"42750",	"42130",	"42810",	"42770",	"42780",	"42110",	"42190",	"42760",	"42720",	"42790",	"42730",	"43760",	"43800",	"43720",	"43740",	"43730",	"43770",	"43150",	"43745",	"43750",	"43111",	"43112",	"43114",	"43113",	"43130",	"44250",	"44150",	"44710",	"44230",	"44270",	"44180",	"44760",	"44210",	"44770",	"44200",	"44810",	"44131",	"44133",	"44790",	"44825",	"44800",	"45790",	"45130",	"45210",	"45190",	"45730",	"45800",	"45770",	"45710",	"45140",	"45750",	"45740",	"45113",	"45111",	"45180",	"45720",	"46810",	"46770",	"46720",	"46230",	"46730",	"46170",	"46710",	"46110",	"46840",	"46780",	"46150",	"46910",	"46130",	"46870",	"46830",	"46890",	"46880",	"46800",	"46900",	"46860",	"46820",	"46790",	"47290",	"47130",	"47830",	"47190",	"47720",	"47150",	"47280",	"47920",	"47250",	"47840",	"47170",	"47770",	"47760",	"47210",	"47230",	"47900",	"47940",	"47930",	"47730",	"47820",	"47750",	"47850",	"47111",	"47113",	"48310",	"48880",	"48820",	"48250",	"48840",	"48270",	"48240",	"48860",	"48330",	"48720",	"48170",	"48740",	"48125",	"48127",	"48123",	"48121",	"48129",	"48220",	"48850",	"48730",	"48870",	"48890",	"50130",	"50110"]

    let total_array = []

    const getResult = await function(list, total_array) {
        for (const i of list) { 
            let SIDO = i.substr(0, 2);
            let GUGUN = i.substr(2, 3);

            axios.get('http://apis.data.go.kr/B552061/frequentzoneBicycle/getRestFrequentzoneBicycle', {
                params: {
                    ServiceKey: process.env.KEY1,
                    searchYearCd: "2020",
                    siDo: SIDO,
                    guGun: GUGUN,
                    type: "json",
                    numOfRows: "1000",
                    pageNo: "1"
                }
            })
                .then(function (response) {
                    item_array = response.data.items.item
    
                    // console.log(item_array[0].lo_crd)
    
                    for (let i = 0; i < item_array.length; i++) {
                        // console.log(item_array[i].lo_crd, item_array[i].la_crd)
                        const result = {lat: item_array[i].la_crd, lon: item_array[i].lo_crd}
                        
                        console.log(result)
                        
                        total_array.push(result)
                    }
                });
        }
    };
    
    const response1 = getResult(list1, total_array);
    setTimeout(getResult, 5000, list2, total_array);

    setTimeout(() => { console.log(total_array) }, 7500);

    // setTimeout(() => { return res.send(response(baseResponse.SUCCESS ,total_array)) }, 10000)
    try {
        setTimeout(() => { contentService.updateBike(total_array) }, 10000)
        setTimeout(() => { return res.send(response(baseResponse.SUCCESS)); }, 15000)
    }
    catch (err) {
        logger.error(`App - updateBike error\n: ${err.message}`);
        return res.send(errResponse(baseResponse.SERVER_ERROR))
    }
    // const updateBike = await contentService.updateBike(total_array);
};

/**
 * API No. 2.1
 * API Name : 의료기관 정보 업데이트
 */
exports.updateMedical = async function (req, res) {
    const getResult1 = async function () {
        await axios.get('http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown', {
            params: {
                serviceKey: process.env.PARMACY_KEY1,
                pageNo: "1",
                numOfRows: "1000000"
            }
        })
            .then(function (response) {
                const result_array1 = response.data.response.body.items.item;
                // console.log(result_array1)
                const updateMedical = contentService.updateMedical(result_array1);
                
                // console.log(result_array1)
            });
    }

    const drop = contentService.dropMedical();

    // const a = getResult1();

    const getResult2 = async function () {
        await axios.get('http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncFullDown', {
            params: {
                serviceKey: process.env.FULL_KEY1,
                pageNo: "1",
                numOfRows: "100000"
            }
        })
            .then(function (response) {
                const result_array2 = response.data.response.body.items.item;

                const updataMedical2 = contentService.updateMedical(result_array2);
            });
    };

    // setTimeout(getResult2, 150000);
    getResult1().then(getResult2())
    // const b = getResult2();

    return res.send(response(baseResponse.SUCCESS));
};