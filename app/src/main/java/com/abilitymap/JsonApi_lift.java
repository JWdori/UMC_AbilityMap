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

public class JsonApi_lift extends AsyncTask<String, String, String> {
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
        String myUrl3 = "http://3.35.237.29/get/lift";
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
                lift_item lift_item = new lift_item(
                        item.getString("lat"),
                        item.getString("lon"),
                        item.getString("idx")
                );
                MainActivity.lift_list.add(lift_item);

            }
            startFlagForCoronaApi = false;


        } catch (NullPointerException | JsonSyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public class lift_item {


        private String lat;
        private String lng;
        private String idx;
        private String remain_stat;



        public lift_item(String lat, String lng, String idx) {
            this.lat = lat;
            this.lng = lng;
            this.idx = idx;

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




    }
}