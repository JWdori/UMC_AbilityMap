package com.abilitymap;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonApi_fac extends AsyncTask<String, String, String> {
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

        Log.d("Task3", "POST");
        String temp = "Not Gained";
        try {
            temp = GET(strings[0], strings[1]);
            Log.d("REST", temp);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private String GET(String x, String y) throws IOException {


        String data = "";
        String myUrl3 = "http://3.35.237.29/get/welfare";
        try {
            URL url = new URL(myUrl3);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(0);
            conn.setConnectTimeout(0);
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
                fac_item fac_item = new fac_item(
                        item.getString("lat"),
                        item.getString("lon"),
                        item.getString("name"),
                        item.getString("address"),
                        item.getString("time"),
//                        item.getString("weekend"),
//                        item.getString("holiday"),
                        item.getString("tel")
                );
                MainActivity.fac_list.add(fac_item);

            }
            startFlagForCoronaApi = false;


        } catch (NullPointerException | JsonSyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public class fac_item {


        private String lat; //위도
        private String lng; //경도
        private String name;    //장소 이름
        private String location;    //상세주소
        private String week;    //주중 영업시간
        private String weekend; //주말 영업시간
        private String holiday; //공휴일 영업시간
        private String phone;   //전화번호



        public fac_item(String lat, String lng, String name, String location, String week,String phone) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;
            this.location = location;
            this.week = week;
            this.weekend = weekend;
            this.holiday = holiday;
            this.phone = phone;
        }

        public String getName(){
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getLat(){
            return lat;
        }
        public void setLat(String lat) { this.lat = lat; }
        public String getLng(){
            return lng;
        }
        public void setlng(String lng) {
            this.lng = lng;
        }
        public String getLocation() { return location; }
        public String getWeek() { return week; }
        public String getWeekend() { return weekend; }
        public String getHoliday() { return holiday; }
        public String getPhone() { return phone; }




    }
}