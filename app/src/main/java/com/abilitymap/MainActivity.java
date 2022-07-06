package com.abilitymap;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.SimpleDateFormat;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abilitymap.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener, SetMarker {
    private GpsTracker gpsTracker;
    private NaverMap naverMap;
    public static ArrayList<JsonApi.total_item> total_list = new ArrayList();
    private FusedLocationSource locationSource;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mLastlocation = null;
    private double speed, calSpeed, getSpeed;
    public static boolean startFlagForCoronaApi;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private ArrayList<Marker> TotalmarkerList = new ArrayList();
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private LatLng currentPosition;
    List<DTO> items;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private ActivityMainBinding binding;

    List<LatLng> latLngList = new ArrayList<>();
    private boolean clickable = true;

//    List<Double> latitudeList = new ArrayList<Double>();
//    List<Double> longitudeList = new ArrayList<Double>();
//
//    double LNG = Double.parseDouble(latitudeList.toString());
//    double LAT = Double.parseDouble(longitudeList.toString());
//
//
//try {
//
//        JSONObject Land = new JSONObject(result);
//        JSONArray jsonArray = Land.getJSONArray("Response");
//        for(int i = 0 ; i<jsonArray.length(); i++){
//            JSONObject subJsonObject = jsonArray.getJSONObject(i);
//
//            Double sLAT = subJsonObject.getDouble("latitude"); //String sLAT = subJsonObject.getString("latitude");
//            Double sLNG = subJsonObject.getDouble("longitude"); //String sLNG = subJsonObject.getString("longitude");
//
//            latitudeList.add(sLAT);
//            longitudeList.add(sLNG);
//        }
//    } catch (
//    JSONException e) {
//        e.printStackTrace();
//    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){ //화면 생성과 함께 현재 위치 받아옴.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initClickListener();
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);

        if(mapFragment ==null ){
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }
        locationSource = new FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });
        drawMarker();
//        new Thread(() -> {
//            setUpMap(); // network 동작, 인터넷에서 xml을 받아오는 코드
//        }).start();
        String lat = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.latitude);
        String lon = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.longitude);
        JsonApi coronaApi = new JsonApi();
        coronaApi.execute(lat,lon,"");
        items = new ArrayList<>();
        // 핸들러
    }





    //
    @Override
    public void onRequestPermissionsResult ( int requestCode,
                                             @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE  && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

            if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
            }
        }

    }


    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        ImageButton Call_button = (ImageButton)findViewById(R.id.call_button);
        ImageButton Report_button = (ImageButton)findViewById(R.id.repot_button);
        if(overlay instanceof Marker && clickable){
//            Toast.makeText(this.getApplicationContext(),"위험지역입니다",Toast.LENGTH_LONG).show();

            LocationDetailFragment infoFragment = new LocationDetailFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.map, infoFragment).addToBackStack(null).commit();
            clickable = false;
            Call_button.setVisibility(View.INVISIBLE);
            Report_button.setVisibility(View.INVISIBLE);

            Log.d("clickable?", String.valueOf(clickable));

            LatLng selectedPosition = ((Marker) overlay).getPosition();
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition,16).animate(CameraAnimation.Easing);
            naverMap.moveCamera(cameraUpdate);

            naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                    getSupportFragmentManager().beginTransaction().remove(infoFragment).commit();
                    getSupportFragmentManager().popBackStack();
                    clickable = true;
                    Call_button.setVisibility(View.VISIBLE);
                    Report_button.setVisibility(View.VISIBLE);
                    Log.d("clickable?", String.valueOf(clickable));
                    Log.d("click event","onMapClick");
                }
            });

            return true;
        }
        return false;

    }

    @Override
    public void onBackPressed(){
        ImageButton Call_button = (ImageButton)findViewById(R.id.call_button);
        ImageButton Report_button = (ImageButton)findViewById(R.id.repot_button);
        clickable = true;
        super.onBackPressed();
        Call_button.setVisibility(View.VISIBLE);
        Report_button.setVisibility(View.VISIBLE);
        Log.d("clickable?", "backKeyPressed");
        Log.d("clickable?", String.valueOf(clickable));
    }


    void checkRunTimePermission() {
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                //Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }



    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    8);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);

        return address.getAddressLine(0).toString()+"\n";

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);

                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, id);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private void UpdateCircle(double x, double y){
        CircleOverlay circle = new CircleOverlay();
        circle.setCenter(new LatLng(x, y));
        circle.setRadius(30);
        circle.setColor(Color.parseColor("#30FF7B00"));
        circle.setOutlineColor(Color.parseColor("#30FF7B00"));
        circle.setMap(naverMap);
        circle.setMinZoom(15);

        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setIcon(OverlayImage.fromResource(R.drawable.invalid_name));
        marker.setMinZoom(8);
        marker.setMaxZoom(15);
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(x,y));
        marker2.setIcon(OverlayImage.fromResource(R.drawable.invalid_name));
        marker2.setMinZoom(16);
        marker.setMaxZoom(15);
        marker2.setWidth(80);
        marker2.setHeight(80);
        marker2.setMap(naverMap);


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentPosition).animate(CameraAnimation.Fly,0);
        naverMap.moveCamera(cameraUpdate);
        this.naverMap = naverMap;

//        LatLng initialPosition = new LatLng(mLastlocation);
//        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
//        naverMap.moveCamera(cameraUpdate);
        naverMap.setMaxZoom(18.0);
        naverMap.setMinZoom(8.0);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setScaleBarEnabled(true);
        uiSettings.setZoomControlEnabled(true); //줌인 줌아웃
        uiSettings.setLocationButtonEnabled(true);

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        final TextView location_text = (TextView)findViewById(R.id.location_text);

        latLngList.add(new LatLng(37.300909685747236,126.84036999665139 )); //주민센터
        latLngList.add(new LatLng(37.30092006963348,126.84651707027692  )); //상록구청
        latLngList.add(new LatLng(37.30080820319068,126.84365805640256  )); //119
        latLngList.add(new LatLng(37.30030995420335,126.8450464027002  )); //상록보건소
        latLngList.add(new LatLng(37.299298647544646,126.84512742919043   )); //상록경찰서
        latLngList.add(new LatLng(37.30578504908008,126.84432454144101    )); //지역아동센터


        latLngList.add(new LatLng(37.29964234222025,126.84612490571303   )); //장애인
        latLngList.add(new LatLng(37.299632110432704,126.8469200877772   )); //장애인
        latLngList.add(new LatLng(37.29891910144883,126.84600231252934    )); //장애인
        latLngList.add(new LatLng(37.298322034553244,126.84590202160551     )); //장애인
        latLngList.add(new LatLng(37.30157589850863,126.8450381659243      )); //장애인

        latLngList.add(new LatLng(37.30012291575613,126.83825685541521     )); //약국
        latLngList.add(new LatLng(37.30078496095471,126.843116709908      )); //약국

        latLngList.add(new LatLng(37.298925701379005,126.84588105222103       )); //급속충전기

        latLngList.add(new LatLng(37.298495139953886,126.83723115856097        )); //경사로
        latLngList.add(new LatLng(37.30175911322991,126.84389859082773        )); //경사로
        latLngList.add(new LatLng(37.30021510929659,126.8448661337656        )); //경사로
        latLngList.add(new LatLng(37.29970314731508,126.8461135029482        )); //경사로
        latLngList.add(new LatLng(37.30160083561462,126.84515936590596        )); //경사로

        latLngList.add(new LatLng(37.498831572249586,126.95256812603331 )); // 상히 테스트용


        setMarker(0,latLngList,"slope",naverMap);
        setMarker(1,latLngList,"slope",naverMap);
        setMarker(2,latLngList,"slope",naverMap);
        setMarker(3,latLngList,"slope",naverMap);
        setMarker(4,latLngList,"slope",naverMap);
        setMarker(5,latLngList,"slope",naverMap);

        setMarker(6,latLngList,"slope",naverMap);
        setMarker(7,latLngList,"slope",naverMap);
        setMarker(8,latLngList,"slope",naverMap);
        setMarker(9,latLngList,"slope",naverMap);
        setMarker(10,latLngList,"slope",naverMap);

        setMarker(11,latLngList,"slope",naverMap);
        setMarker(12,latLngList,"slope",naverMap);

        setMarker(13,latLngList,"charger",naverMap);

        setMarker(14,latLngList,"wheelchair",naverMap);
        setMarker(15,latLngList,"wheelchair",naverMap);
        setMarker(16,latLngList,"wheelchair",naverMap);
        setMarker(17,latLngList,"wheelchair",naverMap);
        setMarker(18,latLngList,"wheelchair",naverMap);

        setMarker(19,latLngList,"danger",naverMap);


        //사고 다발 지역
        UpdateCircle(37.30155838266366,126.84715868584975 );
        UpdateCircle(37.30731010483543,126.83602493657628 );
        UpdateCircle(37.30314238314502,126.8389891901272  );
        UpdateCircle(37.29787636235218,126.84966999005518);
        UpdateCircle(37.305613496417976,126.84751143174793 );
        UpdateCircle(37.30854279577155,126.841369080322  );

        /*
        Marker marker = new Marker();
        marker.setPosition(latLngList.get(0));
        marker.setMap(naverMap);
        marker.setOnClickListener(this);
*/

        /*
        final TextView textView_lat = findViewById(R.id.lat);
        final TextView textView_lon = findViewById(R.id.lon);
        */
//        final TextView tvGetSpeed = findViewById(R.id.tvGetspeed);
//        final TextView tvCalSpeed = findViewById(R.id.tvCalspeed);


        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {

                gpsTracker = new GpsTracker(MainActivity.this);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                double deltaTime = 0;

                // getSpeed() 함수를 이용하여 속도 계산(m/s -> km/h)
                getSpeed = Double.parseDouble(String.format("%.3f", location.getSpeed() * 3.6));

                // 위치 변경이 두번째로 변경된 경우 계산에 의해 속도 계산
                if(mLastlocation != null){
                    deltaTime = (location.getTime() - mLastlocation.getTime());
                    // 속도 계산(시간=ms, 거리=m -> km/h)
                    speed = (mLastlocation.distanceTo(location) / deltaTime) * 3600;
                    calSpeed = Double.parseDouble(String.format("%.3f", speed));
                }
                //현재위치를 지난 위치로 변경
                mLastlocation = location;

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);


                String addrCut[] = address.split(" ");
                location_text.setText(addrCut[2]+" "+addrCut[3]+" "+addrCut[4]);





                String lat_str = Double.toString(latitude);
                String lon_str = Double.toString(longitude);

                /*
                textView_lat.setText(lat_str);
                textView_lon.setText(lon_str);
                 */

                LocationButtonView locationButtonView = findViewById(R.id.navermap_location_button);
                locationButtonView.setMap(naverMap);

                String gs_str = Double.toString(getSpeed);
                String cs_str = Double.toString(calSpeed);


                //api 가져오는 부분

//                Thread th = new Thread(String.valueOf(MainActivity.this));
//                new Thread(() -> {
//                    th.start(); // network 동작, 인터넷에서 xml을 받아오는 코드
//                }).start();
//
//
//
//                try {
//                    StringBuffer sb = new StringBuffer();
//                    URL url = new URL("http://3.35.237.29/total");
//
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                    // 저 경로의 source를 받아온다.
//                    if (conn != null) {
//                        conn.setConnectTimeout(5000);
//                        conn.setUseCaches(false);
//
//                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//                            while (true) {
//                                String line = br.readLine();
//                                if (line == null)
//                                    break;
//                                sb.append(line + "\n");
//                            }
//                            Log.d("myLog", sb.toString());
//                            br.close();
//                        }
//                        conn.disconnect();
//                    }
//
//                    // 받아온 source를 JSONObject로 변환한다.
//                    JSONObject jsonObj = new JSONObject(sb.toString());
//                    JSONArray jArray = (JSONArray) jsonObj.get("result");
//
//                    // 0번째 JSONObject를 받아옴
//                    JSONObject row = jArray.getJSONObject(0);
//                    DTO dto = new DTO();
//                    dto.setName(row.getString("name"));
//                    dto.setTel(row.getString("tel"));
//                    items.add(dto);
//
//                    Log.d("받아온값1 : ", row.getString("name"));
//                    Log.d("받아온값2 : ", row.getString("tel"));
//
//                    // 1번째 JSONObject를 받아옴
//                    JSONObject row2 = jArray.getJSONObject(1);
//                    DTO dto2 = new DTO();
//                    dto2.setName(row2.getString("name"));
//                    dto2.setTel(row2.getString("tel"));
//                    items.add(dto2);
//
//                    Log.d("받아온값3 : ", row2.getString("name"));
//                    Log.d("받아온값4 : ", row2.getString("tel"));
//
//                }catch (Exception e){
//                    e.printStackTrace();
//
//                }

//                Toast.makeText(getApplicationContext(), ""+items+"ek", Toast.LENGTH_SHORT).show();

            }

        });


    }


    private void initClickListener() {

        binding.layoutToolBar.ivMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {            //menu 클릭 시 open drawer
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        View header = binding.navigationView.getHeaderView(0);
        ImageView image = header.findViewById(R.id.iv_close);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // X 클릭 시 close drawer
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

//        binding.navigationView.setNavigationItemSelectedListener(this);

    }
    private void setUpMap(){
        XmlApi parser = new XmlApi();
        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();
    try {

        mapPoint = parser.apiParserSearch();
    } catch (Exception e) {
        System.out.println(3333);
        e.printStackTrace();
    }
    for (int i =0; i<mapPoint.size(); i++){
        for (MapPoint entity:mapPoint){
            UpdateCircle(mapPoint.get(i).getLatitude(), mapPoint.get(i).getLongitude());
        }
    }


    }
    private void removeMarkerAll() {
        for (Marker marker : TotalmarkerList) {
            marker.setMap(null); // 삭제
        }

    }

    private void drawMarker() {
        for (int i =0 ; i< total_list.size(); i++){
            JsonApi.total_item item = total_list.get(i);
            UpdateCircle((Double.parseDouble(item.getLat())), Double.parseDouble(item.getLng()));
//            TotalmarkerList.add(marker);
        }
        return;
    }


}