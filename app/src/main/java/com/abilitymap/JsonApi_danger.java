package com.abilitymap;

import android.os.AsyncTask;

import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class JsonApi_danger extends AsyncTask<String, String, String> {
    public static boolean startFlagForCoronaApi;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {

        String temp = "Not Gained";
        try {
            temp = GET(strings[0], strings[1]);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private String GET(String x, String y) throws IOException {

        String data = "";
        String myUrl3 = "http://3.35.237.29/report";
        try {
            URL url = new URL(myUrl3);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            String line;
            String result = "";

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = bf.readLine()) != null) {
                result = result.concat(line);

            }
//            Log.d("CoronaApi", "The response is :" + result);
            JSONObject root = new JSONObject(result);
            JSONArray Api = root.getJSONArray("result");
            for (int i = 0; i < Api.length(); i++) {
                JSONObject item = Api.getJSONObject(i);
                danger_item danger_item = new danger_item(
                        item.getString("lat"),
                        item.getString("lon"),
                        item.getString("reportIdx")

                );

                MainActivity.danger_list.add(danger_item);

            }
            startFlagForCoronaApi = false;


        } catch (NullPointerException | JsonSyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
    public static String postRequest(String reportDetail) {

        String response = "";

        try {

            String myUrl3 = "http://3.35.237.29/report";
            URL url = new URL(myUrl3);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // 전송 방식
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
            conn.setDoOutput(true);	// URL 연결을 출력용으로 사용(true)

            String requestBody = reportDetail;
            System.out.println("requestBody:" + requestBody);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(requestBody);
            bw.flush();
            bw.close();

            System.out.println("getContentType():" + conn.getContentType()); // 응답 콘텐츠 유형 구하기
            System.out.println("getResponseCode():"    + conn.getResponseCode()); // 응답 코드 구하기
            System.out.println("getResponseMessage():" + conn.getResponseMessage()); // 응답 메시지 구하기

            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            response = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }



    public class danger_item {


        private String lat;
        private String lng;
        private String idx;
//        private String location;
//        private String week;
//        private String weekend;
//        private String holiday;



        public danger_item(String lat, String lng, String idx){
                           //String location, String week, String weekend, String holiday) {
            this.lat = lat;
            this.lng = lng;
            this.idx = idx;
//            this.location = location;
//            this.week = week;
//            this.weekend = weekend;
//            this.holiday = holiday;

        }

        public String getName(){
            return idx;
        }

        public void setName(String idx) {
            this.idx = idx;
        }


        public String getLat(){
            return lat;
        }
//        public void setLat(String lat) {
//            this.lat = lat;
//        }

        public String getLng(){
            return lng;
        }
//
//        public void setlng(String lng) {
//            this.lng = lng;
//        }
//
//        public String getLocation() { return location; }
//        public String getWeek() { return week; }
//        public String getWeekend() { return weekend; }
//        public String getHoliday() { return holiday; }




    }
}