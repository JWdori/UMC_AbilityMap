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
                        item.getString("reportIdx"),    //제보수정시 필요한 아이디ㅇㄾ
                        item.getString("reportDate"),
                        item.getString("reportContent"),
                        item.getString("nickName"),
                        item.getString("reportImage")
                );

                MainActivity.danger_list.add(danger_item);
                System.out.println(danger_item.reportImage+"ㅎㅇ");

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
        private String reportIdx;
        private String reportDate;
        private String reportContent;
        private String reportLocation;
        private String nickName;
        private String reportImage;


        public danger_item(String lat, String lng, String idx, String reportDate,
                           String reportContent, String nickName, String reportImage){
                           //String location, String week, String weekend, String holiday) {
            this.lat = lat;
            this.lng = lng;
            this.reportIdx = idx;
            this.reportDate = reportDate;
            this.reportContent = reportContent;
            this.nickName = nickName;
            this.reportImage = reportImage;

        }

        public String getIndex(){
            return reportIdx;
        }
        public void setIndex(String idx) {
            this.reportIdx = idx;
        }
        public String getLat(){
            return lat;
        }
        public String getLng(){
            return lng;
        }
        public String getNickName(){
            return nickName;
        }
        public String getReportDate(){
            return reportDate;
        }
        public String getReportContent(){
            return reportContent;
        }
        public String getReportImage() { return reportImage; }


    }
}