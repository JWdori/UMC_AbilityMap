package com.abilitymap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class XmlApi {
    public XmlApi(){
        try {
            apiParserSearch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<MapPoint> apiParserSearch() throws Exception{
        URL url = new URL(getURLParam(null));
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis,"utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();

        String name = null, longitude=null, latitude=null;
        boolean bname=false, blongitude=false, blatitude=false;

        while (event_type != XmlPullParser.END_DOCUMENT){
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
                if (tag.equals("name")) {
                    bname = true;
                }
                if (tag.equals("lat")) {
                    blatitude = true;
                }
                if (tag.equals("lon")) {
                    blongitude = true;
                }
                System.out.println(mapPoint.size()+"111");
            }else  if(event_type == XmlPullParser.TEXT){
                if(bname==true){
                    name = xpp.getText();
                    bname = false;
                }else if(blatitude == true){
                    latitude = xpp.getText();
                    blatitude = false;
                }else if(blongitude==true){
                    longitude = xpp.getText();
                    blongitude = false;
                }
                System.out.println(mapPoint.size()+"111");
            }else if(event_type ==XmlPullParser.END_TAG){
                tag = xpp.getName();
                if(tag.equals("result")){
                    MapPoint entity = new MapPoint();
                    entity.setName(name);
                    entity.setLatitude(Double.valueOf(latitude));
                    entity.setLongitude(Double.valueOf(longitude));
                    mapPoint.add(entity);
                    System.out.println(mapPoint.size()+"111");
                }
            }
            event_type = xpp.next();
        }
        System.out.println(mapPoint.size());

        return mapPoint;
    }


    private String getURLParam(String search){
        String url = "http://3.35.237.29/total";
        return url;

    }

    public static void main(String[] args){
        new XmlApi();
    }

}