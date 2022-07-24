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

public class JsonApi_charge extends AsyncTask<String, String, String> {
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
        String myUrl3 = "http://3.35.237.29/get/charger";
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
                charge_item charge_item = new charge_item(
                        item.getString("lat"),
                        item.getString("lon"),
                        item.getString("idx"),
                        item.getString("location"),
                        item.getString("week"),
                        item.getString("weekend"),
                        item.getString("holiday")

                );

                MainActivity.charge_list.add(charge_item);

            }
            startFlagForCoronaApi = false;


        } catch (NullPointerException | JsonSyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public class charge_item {
        private String lat;
        private String lng;
        private String idx;
        private String location;
        private String week;
        private String weekend;
        private String holiday;

        public charge_item(String lat, String lng, String idx,
                           String location, String week, String weekend, String holiday) {
            this.lat = lat;
            this.lng = lng;
            this.idx = idx;
            this.location = location;
            this.week = "평일 "+ week +"\n" + "주말 " + weekend + "\n" + "공휴일 " + holiday ;
            this.weekend = weekend;
            this.holiday = holiday;

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
        public void setLat(String lat) {
            this.lat = lat;
        }

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




    }
}