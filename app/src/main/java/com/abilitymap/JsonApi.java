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

public class JsonApi extends AsyncTask<String, String, String> {
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
        String myUrl3 = "http://3.35.237.29/total";
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
            JSONArray coronaArray = root.getJSONArray("result");
            for (int i = 0; i < coronaArray.length(); i++) {
                JSONObject item = coronaArray.getJSONObject(i);
//                Log.d("corona", item.getString("name"));
                total_item total_item = new total_item(
                        item.getString("lat"),
                        item.getString("lon"),
                        item.getString("name")
                );
                MainActivity.total_list.add(total_item);
                System.out.println(MainActivity.total_list.size()+"123");

            }
            startFlagForCoronaApi = false;


        } catch (NullPointerException | JsonSyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    public class total_item {


        private String lat;
        private String lng;
        private String name;
        private String remain_stat;



        public total_item(String lat, String lng, String name) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;

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