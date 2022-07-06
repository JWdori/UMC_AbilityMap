//package com.abilitymap;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.BufferedInputStream;
//import java.util.ArrayList;
//
//public class TotalApi{
//    private static String ServiceKey="";
//    public TotalApi(){
//        try {
//            apiParserSearch;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    public ArrayList<MapPoint> apiParserSearch() throws Exception{
//        URL url = new URL(getURLParam(null));
//        xmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setNamespaceAware(true);
//        XmlPullParser xpp = factory.newPullParser();
//        BufferedInputStream bis = new BufferedInputStream(url.openStream());
//        xpp.setInput(bis,"utf-8");
//        String tag = null;
//        int event_type = xpp.getEventType();
//
//        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();
//
//
//    }
//
//
//
//}