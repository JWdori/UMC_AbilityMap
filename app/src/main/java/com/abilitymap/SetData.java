//package com.abilitymap;
//import com.naver.maps.map.overlay.Overlay;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.jar.Attributes;
//
//public interface SetData extends Overlay.OnClickListener {
//    public  class MapPoint{
//        private String Name;
//        private double latitude;
//        private double longitude;
//
//        public MapPoint(){
//            super();
//        }
//
//        public MapPoint(String name, double latitude, double longitude){
//            this.Name = Name;
//            this.latitude = latitude;
//            this.longitude = longitude;
//        }
//
//        public String getName(){
//            return Name;
//        }
//
//        public void setName(String Name){
//            this.Name = Name;
//        }
//
//        public Double getLatitude(){
//            return latitude;
//        }
//        public void setLatitude(double latitude){
//            this.latitude = latitude;
//        }
//
//        public Double getLongitude(){
//            return longitude;
//        }
//        public void setLongitude(double longitude){
//            this.longitude = longitude;
//        }
//    }
//
//
//    class TestApiData {
//        String apiUrl = "http://3.35.237.29/total";
//
//        public ArrayList<TestData> getData() {
//            //return data와 관련된 부분
//            ArrayList<TestData> dataArr = new ArrayList<TestData>();
//
//            //네트워킹 작업은 메인스레드에서 처리하면 안된다. 따로 스레드를 만들어 처리하자
//            Thread t = new Thread() {
//                @Override
//                public void run() {
//                    try {
//
//                        //url과 관련된 부분
//                        String fullurl = apiUrl;
//                        URL url = new URL(fullurl);
//                        InputStream is = url.openStream();
//
//                        //xmlParser 생성
//                        XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
//                        XmlPullParser parser = xmlFactory.newPullParser();
//                        parser.setInput(is,"utf-8");
//
//                        //xml과 관련된 변수들
//                        boolean bName = false, bLat = false, bLong = false;
//                        String name = "", latitude = "", longitude = "";
//
//                        //본격적으로 파싱
//                        while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
//                            int type = parser.getEventType();
//                            TestData data = new TestData();
//
//                            //태그 확인
//                            if(type == XmlPullParser.START_TAG) {
//                                if (parser.getName().equals("col")) {
//                                    if (parser.getAttributeValue(0).equals("위치명"))
//                                        bName = true;
//                                    else if (parser.getAttributeValue(0).equals("위도"))
//                                        bLat = true;
//                                    else if (parser.getAttributeValue(0).equals("경도"))
//                                        bLong = true;
//                                }
//                            }
//                            //내용 확인
//                            else if(type == XmlPullParser.TEXT) {
//                                if (bName) {
//                                    name = parser.getText();
//                                    bName = false;
//                                } else if (bLat) {
//                                    latitude = parser.getText();
//                                    bLat = false;
//                                } else if (bLong) {
//                                    longitude = parser.getText();
//                                    bLong = false;
//                                }
//                            }
//                            //내용 다 읽었으면 데이터 추가
//                            else if (type == XmlPullParser.END_TAG && parser.getName().equals("item")) {
//                                data.setName(name);
//                                data.setLatitude(Double.valueOf(latitude));
//                                data.setLongitude(Double.valueOf(longitude));
//
//                                dataArr.add(data);
//                            }
//
//                            type = parser.next();
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (XmlPullParserException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (XmlPullParserException e) {
//                        e.printStackTrace();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            };
//            try {
//                t.start();
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return dataArr;
//        }
//
//    }
//}
