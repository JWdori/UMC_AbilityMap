package com.abilitymap;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.location.Address;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.SimpleDateFormat;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abilitymap.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLngBounds;
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

import ted.gun0912.clustering.naver.TedNaverClustering;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener, SetMarker, SetMarker_facility {
    private GpsTracker gpsTracker;
    private NaverMap naverMap;
    public static ArrayList<JsonApi_total.total_item> total_list = new ArrayList();
    public static ArrayList<JsonApi_bike.bike_item> bike_list = new ArrayList();
    private FusedLocationSource locationSource;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mLastlocation = null;
    private double speed, calSpeed, getSpeed;
    private LocationButtonView locationButtonView2;
    public static boolean startFlagForCoronaApi;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
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
    private static final String[] REQUIRED_PERMISSIONS2 = {
            "123",
            Manifest.permission.SEND_SMS,
    };
    private ActivityMainBinding binding;

    List<LatLng> latLngList = new ArrayList<>();
    private boolean clickable = true;
    private final long finishtimeed = 1000;
    private long presstime = 0;
    private boolean isDrawerOpen = false;


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
        ImageButton Report_message = (ImageButton) findViewById(R.id.repot_message);

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
                        if(location == null) {
                            currentPosition = new LatLng(37.3595316, 127.1052133);
                            // Got last known location. In some rare situations this can be null.
                        }else{
                            currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
                            // Logic to handle location object
                        }
                    }
                });
        String lat = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.latitude);
        String lon = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.longitude);





        JsonApi_total total_api = new JsonApi_total();
        JsonApi_bike bike_api  = new JsonApi_bike();
        total_api.execute(lat,lon,"");
        bike_api.execute(lat,lon,"");

//        new Thread(() -> {
//            setUpMap(); // network 동작, 인터넷에서 xml을 받아오는 코드
//        }).start();

        items = new ArrayList<>();
        // 핸들러



//



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
        ImageButton Report_message = (ImageButton)findViewById(R.id.repot_message);
        /*Report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현 위치 location 받아와서 서버로 넘겨줘야함
                //넘겨줄 것 : 사진, text, 닉네임, 좌표, 신고일자

                //카메라 권한요청, 내 파일 권한 요청 필요

                //카메라 화면이 먼저 나옴
                //사진 찍고
                //report detail 화면 띄워서
                //입력받고 전송하기 버튼 누르면

                //현 위치 : locationSource

                //아니 여기 왜 버튼이 안눌려렬렬려려려려려려려려려렬
                //버튼 init버튼인가 밑에 함수에서 설정하면 됩니다^^
//zzzzz

                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                Log.d("camera","Reportbutton clicked");

                Intent intent = null;
                Log.d("camera","clicked");
                setCamera(intent);
            }
        });*/
        if(overlay instanceof Marker && clickable){
//            Toast.makeText(this.getApplicationContext(),"위험지역입니다",Toast.LENGTH_LONG).show();

            LocationDetailFragment infoFragment = new LocationDetailFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.map, infoFragment).addToBackStack(null).commit();
            clickable = false;
            Call_button.setVisibility(View.INVISIBLE);
            Report_button.setVisibility(View.INVISIBLE);
            Report_message.setVisibility(View.INVISIBLE);

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
                    Report_message.setVisibility(View.VISIBLE);
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
//        ImageButton Call_button = (ImageButton)findViewById(R.id.call_button);
//        ImageButton Report_button = (ImageButton)findViewById(R.id.repot_button);
//        ImageButton Report_message = (ImageButton)findViewById(R.id.repot_message);
//        clickable = true;
//        super.onBackPressed();
//        Call_button.setVisibility(View.VISIBLE);
//        Report_button.setVisibility(View.VISIBLE);
//        Report_message.setVisibility(View.VISIBLE);
//        Log.d("clickable?", "backKeyPressed");
//        Log.d("clickable?", String.valueOf(clickable));


        if (isDrawerOpen){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            isDrawerOpen = false;
        }else{

            if (clickable) {

                long tempTime = System.currentTimeMillis();
                long intervalTime = tempTime - presstime;

                if (0 <= intervalTime && finishtimeed >= intervalTime)
                {
                    finish();
                }
                else
                {
                    presstime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
                }

            }else {
                super.onBackPressed();
                ImageButton Call_button = (ImageButton) findViewById(R.id.call_button);
                ImageButton Report_button = (ImageButton) findViewById(R.id.repot_button);
                ImageButton Report_message = (ImageButton) findViewById(R.id.repot_message);
                Call_button.setVisibility(View.VISIBLE);
                Report_button.setVisibility(View.VISIBLE);
                Report_message.setVisibility(View.VISIBLE);
                clickable = true;
                Log.d("clickable?", "backKeyPressed");
                Log.d("clickable?", String.valueOf(clickable));


            }



        }
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


        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setIcon(OverlayImage.fromResource(R.drawable.danger_location_yellow));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMap(naverMap);



    }

    private ArrayList<NaverItem> getItems() {
        LatLngBounds bounds = naverMap.getContentBounds();
        ArrayList<NaverItem> items = new ArrayList<>();

        items.add(new NaverItem(37.5246467590332, 126.92683410644531));
        items.add(new NaverItem(37.4689826965332, 126.93840026855469));
        items.add(new NaverItem(37.529083251953125, 126.92189025878906));
        items.add(new NaverItem(37.47843551635742, 126.98231506347656));
        items.add(new NaverItem(37.480621337890625, 126.98323822021484));
        items.add(new NaverItem(37.477970123291016, 126.95721435546875));
        items.add(new NaverItem(37.521175384521484, 126.9185562133789));
        items.add(new NaverItem(37.503662109375, 127.0100326538086));
        items.add(new NaverItem(37.51717758178711, 126.90557861328125));
        items.add(new NaverItem(37.52184295654297, 126.92816162109375));
        items.add(new NaverItem(37.47835159301758, 126.95608520507812));
        items.add(new NaverItem(37.474891662597656, 127.04256439208984));
        items.add(new NaverItem(37.486785888671875, 127.01634216308594));
        items.add(new NaverItem(37.50286102294922, 127.02454376220703));
        items.add(new NaverItem(37.47911834716797, 126.95285034179688));
        items.add(new NaverItem(37.515625, 126.90752410888672));
        items.add(new NaverItem(37.522945404052734, 126.92428588867188));
        items.add(new NaverItem(37.47600555419922, 126.9771499633789));
        items.add(new NaverItem(37.4664306640625, 126.93685150146484));
        items.add(new NaverItem(37.477970123291016, 126.95721435546875));
        items.add(new NaverItem(37.50643539428711, 127.0068359375));
        items.add(new NaverItem(37.48375701904297, 127.03482055664062));
        items.add(new NaverItem(37.48411178588867, 126.90324401855469));
        items.add(new NaverItem(37.48514175415039, 127.01586151123047));
        items.add(new NaverItem(37.493595123291016, 126.89849090576172));
        items.add(new NaverItem(37.512603759765625, 126.92414855957031));
        items.add(new NaverItem(37.52606964111328, 126.89155578613281));
        items.add(new NaverItem(37.48301315307617, 126.9969482421875));
        items.add(new NaverItem(37.483646392822266, 126.95520782470703));
        items.add(new NaverItem(37.482181549072266 ,126.94163513183594 ));
        items.add(new NaverItem(37.478675842285156 , 126.9821548461914));
        items.add(new NaverItem( 37.46232986450195, 126.95233154296875));
        items.add(new NaverItem(37.48004150390625 ,126.95210266113281 ));
        items.add(new NaverItem(37.47433090209961 ,126.91799926757812 ));
        items.add(new NaverItem( 37.470481872558594,126.93447875976562 ));
        items.add(new NaverItem(37.483795166015625 , 126.92927551269531));
        items.add(new NaverItem(37.4698371887207 ,127.04195404052734 ));
        items.add(new NaverItem( 37.52496337890625,126.93901824951172 ));
        items.add(new NaverItem( 37.515625,126.90752410888672 ));
        items.add(new NaverItem( 37.515625, 126.90752410888672));
        items.add(new NaverItem(37.516876220703125 ,126.90438079833984 ));
        items.add(new NaverItem(37.471500396728516 ,126.98245239257812 ));
        items.add(new NaverItem( 37.51831817626953,126.89595031738281 ));
        items.add(new NaverItem(37.47875213623047 , 126.95467376708984));
        items.add(new NaverItem(37.49344253540039 ,127.01612091064453 ));
        items.add(new NaverItem( 37.519046783447266, 126.88628387451172));
        items.add(new NaverItem( 37.50643539428711,127.0068359375 ));
        items.add(new NaverItem(37.47794723510742 ,126.95262145996094 ));
        items.add(new NaverItem(37.48775863647461 , 127.01319122314453));
        items.add(new NaverItem( 37.495384216308594, 127.01643371582031));
        items.add(new NaverItem( 37.5021858215332, 126.99009704589844));
        items.add(new NaverItem(37.473854064941406 , 126.91732788085938));
        items.add(new NaverItem( 37.48548889160156, 127.017333984375));
        items.add(new NaverItem(37.49069595336914, 126.99148559570312 ));
        items.add(new NaverItem( 37.51095199584961 , 127.02018737792969));
        items.add(new NaverItem(37.502960205078125 , 127.0101318359375));
        items.add(new NaverItem(37.5037727355957 , 127.02079772949219));
        items.add(new NaverItem(37.49629211425781 , 126.9857177734375 ));
        items.add(new NaverItem(37.47736358642578, 126.98747253417969 ));
        items.add(new NaverItem(37.4817504882812,127.00470733642578 ));
        items.add(new NaverItem(37.48357009887695 , 126.99656677246094 ));
        items.add(new NaverItem(37.486053466796875,126.93956756591797 ));
        items.add(new NaverItem( 37.482547760009766,126.94361114501953 ));
        items.add(new NaverItem(37.47623062133789 ,126.93753051757812 ));
        items.add(new NaverItem(37.48585891723633 , 126.9559555053711));
        items.add(new NaverItem(37.484336853027344 ,127.03057861328125 ));
        items.add(new NaverItem(37.46940994262695 ,127.04126739501953 ));
        items.add(new NaverItem( 37.51215744018555, 126.916259765625));
        items.add(new NaverItem( 37.50609588623047,126.91094970703125 ));
        items.add(new NaverItem( 37.512298583984375, 126.92183685302734));
        items.add(new NaverItem(37.486656188964844 , 126.91316223144531));
        items.add(new NaverItem(37.46944808959961 , 126.93767547607422));
        items.add(new NaverItem(37.47056198120117 , 126.93363952636719));
        items.add(new NaverItem( 37.48203659057617,126.9296646118164 ));
        items.add(new NaverItem( 37.500999450683594, 126.91187286376953));
        items.add(new NaverItem( 37.52552795410156, 126.91923522949219));
        items.add(new NaverItem( 37.52056884765625,126.90328979492188 ));
        items.add(new NaverItem(37.51354217529297 , 126.9047622680664));
        items.add(new NaverItem(37.470855712890625,127.02538299560547 ));
        items.add(new NaverItem( 37.514949798583984,127.01422882080078 ));
        items.add(new NaverItem(37.49470520019531 ,127.02816009521484 ));
        items.add(new NaverItem(37.48482131958008 ,126.9320068359375 ));
        items.add(new NaverItem(37.49801254272461 , 126.99810028076172));
        items.add(new NaverItem(37.49243927001953 , 127.01140594482422));
        items.add(new NaverItem(37.521366119384766 , 126.92501831054688));
        items.add(new NaverItem(37.476436614990234,127.0437240600586));
        items.add(new NaverItem(37.47782897949219,126.9626693725586));
        items.add(new NaverItem(37.52021408081055,126.93179321289062));
        items.add(new NaverItem(37.51353454589844,126.92245483398438));
        items.add(new NaverItem(37.47857666015625,126.95526885986328));
        items.add(new NaverItem(37.49344253540039,127.01612091064453));
        items.add(new NaverItem(37.532928466796875 , 126.90283203125));
        items.add(new NaverItem(37.52606964111328 , 126.89155578613281));
        items.add(new NaverItem(37.51943588256836 , 126.8912582397461));
        items.add(new NaverItem(37.53740692138672 , 126.89381408691406));
        items.add(new NaverItem(37.50643539428711 , 127.0068359375));
        items.add(new NaverItem(37.50925827026367 ,127.00743103027344));
        items.add(new NaverItem(37.48385238647461, 126.93024444580078));
        items.add(new NaverItem(37.516876220703125,126.90438079833984));
        items.add(new NaverItem(37.47769546508789,126.98223114013672));
        items.add(new NaverItem(37.47795867919922,126.95787048339844));
        items.add(new NaverItem(37.48904037475586,126.92949676513672));
        items.add(new NaverItem(37.50784683227539,126.91120147705078));
        items.add(new NaverItem(37.492034912109375,127.02880096435547));
        items.add(new NaverItem(37.51598358154297,126.90611267089844));
        items.add(new NaverItem(37.47890090942383,126.95262908935547));
        items.add(new NaverItem(37.47922897338867,126.95348358154297));
        items.add(new NaverItem(37.52505874633789,126.92593383789062));
        items.add(new NaverItem(37.516876220703125,126.90438079833984));
        items.add(new NaverItem(37.498451232910156,127.02635955810547));
        items.add(new NaverItem(37.47732162475586,126.9581298828125));
        items.add(new NaverItem(37.50643539428711,127.0068359375));
        items.add(new NaverItem(37.49309158325195,127.01834106445312));
        items.add(new NaverItem(37.52027130126953,126.88864135742188));
        items.add(new NaverItem(37.501033782958984,127.01163482666016));
        items.add(new NaverItem(37.49732208251953,126.9856948852539));
        items.add(new NaverItem(37.46182632446289,127.03538513183594));
        items.add(new NaverItem(37.52701950073242,126.89825439453125));
        items.add(new NaverItem(37.48542022705078,127.01896667480469));
        items.add(new NaverItem(37.48019027709961,126.98341369628906));
        items.add(new NaverItem(37.520652770996094,126.889892578125));
        items.add(new NaverItem(37.52788543701172,126.92913818359375));
        items.add(new NaverItem(37.484378814697266,127.01445770263672));
        items.add(new NaverItem(37.465057373046875 , 126.95162200927734));
        items.add(new NaverItem(37.46232986450195,126.95233154296875));
        items.add(new NaverItem(37.47765350341797,126.95822143554688));
        items.add(new NaverItem(37.500160217285156,127.00437927246094));
        items.add(new NaverItem(37.496238708496094,126.99763488769531));
        items.add(new NaverItem(37.50178909301758,127.02458953857422));
        items.add(new NaverItem(37.47626495361328,126.96294403076172));
        items.add(new NaverItem(37.49604034423828,127.02531433105469));


        return items;
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentPosition).animate(CameraAnimation.Fly,0);
        naverMap.moveCamera(cameraUpdate);
        this.naverMap = naverMap;
        TedNaverClustering.with(this, naverMap)
                .items(getItems())
                .make();
//        LatLng initialPosition = new LatLng(mLastlocation);
//        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
//        naverMap.moveCamera(cameraUpdate);
        naverMap.setMaxZoom(18.0);
        naverMap.setMinZoom(8.0);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(true); //줌인 줌아웃
        uiSettings.setLocationButtonEnabled(true);
//        locationButtonView2 = findViewById(R.id.navermap_location_button);
//        locationButtonView2.setMap(naverMap);


        setMarker_facility(); // network 동작, 인터넷에서 xml을 받아오는 코드
        drawMarker_bike();
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

        latLngList.add(new LatLng(37.497836016079916,126.95270728649908 )); // 상히 테스트용


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
                if(addrCut.length >= 6) {
                    location_text.setText(addrCut[2] + " " + addrCut[3] + " " + addrCut[4] + " " + addrCut[5]);
                }else if(addrCut.length >= 5){
                    location_text.setText(addrCut[2] + " " + addrCut[3] + " " + addrCut[4]);
                }else if(addrCut.length >= 4){
                    location_text.setText(addrCut[2] + " " + addrCut[3]);
               }else if(addrCut.length >= 3){
                    location_text.setText(addrCut[1] + " "+addrCut[2]);
                }else if(addrCut.length >= 2){
                    location_text.setText(addrCut[0]+ " "+addrCut[1]);
                }else if(addrCut.length >= 1){
                    location_text.setText(addrCut[0]);
                }else{
                    location_text.setText("위치 정보 없음");
                }




                String lat_str = Double.toString(latitude);
                String lon_str = Double.toString(longitude);

                /*
                textView_lat.setText(lat_str);
                textView_lon.setText(lon_str);
                 */

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



    private void setCamera(Intent intent){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(getApplicationContext(), Camera2Activity.class);
        }
        startActivity(intent);
    }

    //메시지 보내기 함수
    private void sendSms(){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage("01031142949", null, "꼭 치킨 사줄게요", null, null);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if(!dialogBounds.contains((int) ev.getX(),(int) ev.getY())){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initClickListener() {

        //긴급신고 메세지지
       ImageButton Report_message = findViewById(R.id.repot_message);
       Report_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로컬에 기록하기. 그걸 가지고 1,2,3번 시도 구분
                SharedPreferences pref2 = getSharedPreferences("Permission_touch", Activity.MODE_PRIVATE);
                boolean Permission_touch = pref2.getBoolean("Permission_touch", false);
                //입력한 값을 가져와 변수에 담는다
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS2[1])) {
                        //2번 퍼미션 시도
                        SharedPreferences.Editor editor = pref2.edit();
                        editor.putBoolean("Permission_touch",true);
                        editor.commit();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }else {
                        if( Permission_touch==true ){
                            //3번째부터
                            Toast.makeText(getApplicationContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                        }else{
                            //1번째 퍼미션 시도
                        }
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }//팝업 이어갈 예정
                }else{
                    //퍼미션 허용받으면 이쪽입니다~~~
                    SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
                    boolean first_touch = pref.getBoolean("isFirst", false);
                    if(first_touch==false){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isFirst",true);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "최초 실행", Toast.LENGTH_LONG).show();
                        //앱 최초 실행시 하고 싶은 작업
                    }else{
                        Toast.makeText(getApplicationContext(), "두번째 실행", Toast.LENGTH_LONG).show();
                        sendSms();
                    }
                }

            }
        });

       ImageButton Report_button = findViewById(R.id.repot_button);
       Report_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //현 위치 location 받아와서 서버로 넘겨줘야함
               //넘겨줄 것 : 사진, text, 닉네임, 좌표, 신고일자

               //카메라 권한요청, 내 파일 권한 요청 필요

               //카메라 화면이 먼저 나옴
               //사진 찍고
               //report detail 화면 띄워서
               //입력받고 전송하기 버튼 누르면

               //현 위치 : locationSource

               //아니 여기 왜 버튼이 안눌려렬렬려려려려려려려려려렬
               //버튼 init버튼인가 밑에 함수에서 설정하면 됩니다^^
//zzzzz

               Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               Log.d("camera","Reportbutton clicked");

               Intent intent = null;
               Log.d("camera","clicked");
               setCamera(intent);
           }
       });
       binding.layoutToolBar.ivMenu.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {            //menu 클릭 시 open drawer
               binding.drawerLayout.openDrawer(GravityCompat.START);
               isDrawerOpen = true;
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

       binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if (item.getItemId() == R.id.nav_notification) {
//                 binding.drawerLayout.closeDrawer(GravityCompat.START); //열려있는 메뉴판 닫고 화면 전환
                   Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                   startActivity(intent);
               }
               else if (item.getItemId() == R.id.nav_call) {
                   Intent intent = new Intent(getApplicationContext(), EmergencyCallActivity.class);
                   startActivity(intent);
               }
               else if (item.getItemId() == R.id.nav_report) {
                   Intent intent = null;
                   Log.d("camera","clicked");
                   setCamera(intent);
               }
               else if (item.getItemId() == R.id.nav_book) {

               }
               else if (item.getItemId() == R.id.nav_review) {

               }
               return true;
           }
       });

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

    private void setMarker_facility() {
        for (int i =0 ; i< total_list.size(); i++){
            JsonApi_total.total_item item = total_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()),"hos",naverMap);
//            TotalmarkerList.add(marker);
        }
        return;
    }

    private void drawMarker_bike() {
        System.out.println("3213"+bike_list.size());
        for (int i =0 ; i< bike_list.size(); i++){
            JsonApi_bike.bike_item item = bike_list.get(i);
            UpdateCircle((Double.parseDouble(item.getLat())), Double.parseDouble(item.getLng()));
//            TotalmarkerList.add(marker);
        }
        return;
    }




}