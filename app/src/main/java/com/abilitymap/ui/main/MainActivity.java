package com.abilitymap.ui.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.location.Address;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.SimpleDateFormat;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abilitymap.api.JsonApi_phar;
import com.abilitymap.ui.filter.FilterActivity;
import com.abilitymap.ui.emergencyCall.InfoDialog;
import com.abilitymap.api.JsonApi_bike;
import com.abilitymap.api.JsonApi_charge;
import com.abilitymap.api.JsonApi_danger;
import com.abilitymap.api.JsonApi_ele;
import com.abilitymap.api.JsonApi_fac;
import com.abilitymap.api.JsonApi_hos;
import com.abilitymap.api.JsonApi_lift;
import com.abilitymap.api.JsonApi_slope;
import com.abilitymap.api.JsonApi_wheel;
import com.abilitymap.ui.marker.DangerDetailSheet;
import com.abilitymap.ui.marker.LocationBottomSheet;
import com.abilitymap.data.personInfo.PersonInfoDatabase;
import com.abilitymap.R;
import com.abilitymap.ui.report.Report_detail;
import com.abilitymap.ui.marker.SetMarker_facility;
import com.abilitymap.ui.marker.SetMarker_wheel;

import com.abilitymap.databinding.ActivityMainBinding;
import com.abilitymap.ui.emergencyCall.EmergencyCallActivity;
import com.abilitymap.ui.menuBook.MenuBookActivity;
import com.abilitymap.ui.notification.NotificationActivity;
import com.abilitymap.ui.oss.OssActivity;
import com.abilitymap.ui.search.Fragment_search;
import com.abilitymap.ui.search.ItemViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
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
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener, SetMarker_facility, SetMarker_wheel {
    private GpsTracker gpsTracker;
    public boolean bfragment = false;
    private NaverMap naverMap;
    public static Activity firstActivity;
    public static ArrayList<JsonApi_hos.hos_item> hos_list = new ArrayList();
    public static ArrayList<JsonApi_bike.bike_item> bike_list = new ArrayList();
    public static ArrayList<JsonApi_charge.charge_item> charge_list = new ArrayList();
    public static ArrayList<JsonApi_slope.slope_item> slope_list = new ArrayList();
    public static ArrayList<JsonApi_danger.danger_item> danger_list = new ArrayList();
    public static ArrayList<JsonApi_ele.ele_item> ele_list = new ArrayList();
    public static ArrayList<JsonApi_wheel.wheel_item> wheel_list = new ArrayList();
    public static ArrayList<JsonApi_fac.fac_item> fac_list = new ArrayList();
    public static ArrayList<JsonApi_lift.lift_item> lift_list = new ArrayList();
    public static ArrayList<JsonApi_phar.phar_item> phar_list = new ArrayList();
    BottomSheetBehavior<View> bottomSheetBehavior;
    private FusedLocationSource locationSource;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;

    private FusedLocationProviderClient fusedLocationClient;
    private Location mLastlocation = null;
    private double speed, calSpeed, getSpeed;
    private LocationButtonView locationButtonView2;

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private ArrayList<Marker> TotalmarkerList = new ArrayList();
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private LatLng currentPosition;
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
    ActivityResultLauncher<Intent> activityResultLauncher;
    List<LatLng> latLngList = new ArrayList<>();
    private boolean clickable = true;
    private boolean clickable2 = true;
    private final long finishtimeed = 1000;
    private long presstime = 0;
    private boolean isDrawerOpen = false;
    private boolean isFilter = false;
    ProgressDialog dialog; //원형 프로그레스바

    DangerDetailSheet dangerInfoFragment = null;
    LocationBottomSheet infoFragment = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) { //화면 생성과 함께 현재 위치 받아옴.
        String lat = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.latitude);
        String lon = String.valueOf(NaverMap.DEFAULT_CAMERA_POSITION.target.longitude);


        JsonApi_hos hos_api = new JsonApi_hos();
        JsonApi_bike bike_api = new JsonApi_bike();
        JsonApi_slope slope_api = new JsonApi_slope();
        JsonApi_charge charge_api = new JsonApi_charge();
        JsonApi_danger danger_api = new JsonApi_danger();
        JsonApi_ele ele_api = new JsonApi_ele();
        JsonApi_wheel wheel_api = new JsonApi_wheel();
        JsonApi_fac fac_api = new JsonApi_fac();
        JsonApi_lift lift_api = new JsonApi_lift();
        JsonApi_phar phar_api = new JsonApi_phar();


        bike_api.execute(lat, lon, "");
        charge_api.execute(lat, lon, "");
        slope_api.execute(lat, lon, "");
        danger_api.execute(lat, lon, "");
        ele_api.execute(lat, lon, "");
        wheel_api.execute(lat, lon, "");
        fac_api.execute(lat, lon, "");
        hos_api.execute(lat, lon, "");
        lift_api.execute(lat, lon, "");
        phar_api.execute(lat, lon, "");

        SharedPreferences first_open = getSharedPreferences("first_open", MODE_PRIVATE);

        Boolean isFirst = first_open.getBoolean("first_open", true);
        if (isFirst) {
            dialog = new ProgressDialog(MainActivity.this); //프로그레스 대화상자 객체 생성
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //프로그레스 대화상자 스타일 원형으로 설정
            dialog.setCancelable(false);
            dialog.setMessage("실행 준비중입니다.\n잠시만 기다려주세요."); //프로그레스 대화상자 메시지 설정
            dialog.show(); //프로그레스 대화상자 띄우기
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss(); // 3초 시간지연 후 프로그레스 대화상자 닫기
                }
            }, 2000); //최초 실행에서는 길게...
            SharedPreferences.Editor editor = first_open.edit();
            editor.putBoolean("first_open", false);
            editor.commit();

            View dialogView = getLayoutInflater().inflate(R.layout.user_first_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 24);
            alertDialog.getWindow().setBackgroundDrawable(inset);
            alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
            alertDialog.show();

            TextView noButton = alertDialog.findViewById(R.id.tv_no_dialog_user);
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            TextView yesButton = alertDialog.findViewById(R.id.tv_yes_dialog_user);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MenuBookActivity.class);
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            });
        } else {
            dialog = new ProgressDialog(MainActivity.this); //프로그레스 대화상자 객체 생성
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //프로그레스 대화상자 스타일 원형으로 설정
            dialog.setCancelable(false);
            dialog.setMessage("잠시만 기다려주세요."); //프로그레스 대화상자 메시지 설정
            dialog.show(); //프로그레스 대화상자 띄우기
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss(); // 1초 시간지연 후 프로그레스 대화상자 닫기
                }
            }, 1000);
        }

        //로딩
        firstActivity = MainActivity.this;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton Report_message = (ImageButton) findViewById(R.id.message_button);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        String reportContent = null;
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }
        initClickListener();
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            currentPosition = new LatLng(37.3595316, 127.1052133);
                            // Got last known location. In some rare situations this can be null.
                        } else {
                            currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            // Logic to handle location object
                        }
                    }
                });

//        new Thread(() -> {
//            setUpMap(); // network 동작, 인터넷에서 xml을 받아오는 코드
//        }).start();


        // 핸들러


        final ItemViewModel viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        final Observer<LatLng> searchObserver = new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng latLng) {
                System.out.println(viewModel.getSelectedName().getValue());
                showBottomSheet(latLng, viewModel.getSelectedName().getValue());
            }
        };
        viewModel.getSelectedLatLng().observe(this, searchObserver);

    }

    private void showBottomSheet(LatLng latLng, String selectedName) {
        String name;
        String location;
        String week;
        String weekend;
        String holiday;
        String phone;

        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 16).pivot(new PointF(0.5f, 0.5f)).animate(CameraAnimation.Linear);
        //default cameraUpdate


        //클릭이벤트가 일어난 마커가 어느 타입인지 search
        if (findSearchChargerMarkerItem(latLng, selectedName, charge_list) != null) {
            JsonApi_charge.charge_item selectedChargeItem = findSearchChargerMarkerItem(latLng, selectedName, charge_list);
            location = selectedChargeItem.getLocation();
            week = selectedChargeItem.getWeek();
            weekend = selectedChargeItem.getWeekend();
            holiday = selectedChargeItem.getHoliday();

            Marker tempMarker = printTempMarker(latLng, "charge");

            System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
            infoFragment = new LocationBottomSheet("charge", location, week, holiday, tempMarker, true);

            cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 16).pivot(new PointF(0.5f, 0.3f)).animate(CameraAnimation.Linear);
        } else if (findSearchHosMarkerItem(latLng, selectedName, hos_list) != null) {
            JsonApi_hos.hos_item selectedTotalItem = findSearchHosMarkerItem(latLng, selectedName, hos_list);
            name = selectedTotalItem.getName();
            location = selectedTotalItem.getLocation();
            week = selectedTotalItem.getWeek();
            weekend = selectedTotalItem.getWeekend();
            holiday = selectedTotalItem.getHoliday();
            phone = selectedTotalItem.getPhone();

            Marker tempMarker = printTempMarker(latLng, "hos");

            System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
            infoFragment = new LocationBottomSheet("hos", name, location, week, holiday, phone, tempMarker, true);

            cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 16).pivot(new PointF(0.5f, 0.0f)).animate(CameraAnimation.Linear);

        } else if (findSearchFacilityMarkerItem(latLng, selectedName, fac_list) != null) {
            JsonApi_fac.fac_item selectedFacilityItem = findSearchFacilityMarkerItem(latLng, selectedName, fac_list);
            name = selectedFacilityItem.getName();
            location = selectedFacilityItem.getLocation();
            week = selectedFacilityItem.getWeek();
            weekend = selectedFacilityItem.getWeekend();
            holiday = selectedFacilityItem.getHoliday();
            phone = selectedFacilityItem.getPhone();

            Marker tempMarker = printTempMarker(latLng, "office");

            System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
            infoFragment = new LocationBottomSheet("office", name, location, week, holiday, phone, tempMarker, true);

            cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 16).pivot(new PointF(0.5f, 0.4f)).animate(CameraAnimation.Linear);
        } else if (findSearchPharmacyMarkerItem(latLng, selectedName, phar_list) != null) {
            JsonApi_phar.phar_item selectedPharItem = findSearchPharmacyMarkerItem(latLng, selectedName, phar_list);
            name = selectedPharItem.getName();
            location = selectedPharItem.getLocation();
            week = selectedPharItem.getWeek();
            weekend = selectedPharItem.getWeekend();
            holiday = selectedPharItem.getHoliday();
            phone = selectedPharItem.getPhone();

            Marker tempMarker = printTempMarker(latLng, "phar");

            System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
            infoFragment = new LocationBottomSheet("phar", name, location, week, holiday, phone, tempMarker, true);

            cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 16).pivot(new PointF(0.5f, 0.0f)).animate(CameraAnimation.Linear);
        }

        infoFragment.show(getSupportFragmentManager(), "infoFragment");

        naverMap.moveCamera(cameraUpdate);


    }

    public Marker printTempMarker(LatLng latLng, String markerType) {
        Marker marker = new Marker();
        marker.setPosition(latLng);
        marker.setWidth(160);
        marker.setHeight(160);
        marker.setMinZoom(13);//줌 설정
        switch (markerType) {
            case "office":
                marker.setIcon(OverlayImage.fromResource(R.drawable.facility_office));
                break;
            case "phar":
                marker.setIcon(OverlayImage.fromResource(R.drawable.hos_icon));
                break;
            case "hos":
                marker.setIcon(OverlayImage.fromResource(R.drawable.hos_icon));
                break;
            case "charge":
                marker.setIcon(OverlayImage.fromResource(R.drawable.charge_icon));
                break;
            case "danger":
                marker.setIcon(OverlayImage.fromResource(R.drawable.dnager_red));
                break;
        }
        marker.setMap(naverMap);

        return marker;
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        ImageButton repot_message = (ImageButton) findViewById(R.id.message_button);
        ImageButton Report_button = (ImageButton) findViewById(R.id.repot_button);


        LatLng selectedPosition = ((Marker) overlay).getPosition();
        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.5f)).animate(CameraAnimation.Linear);
        //default cameraUpdate

        if (overlay instanceof Marker && String.valueOf(overlay.getTag()).equals("danger")) {
            if (findThisDangerMarkerItem(((Marker) overlay).getPosition(), danger_list) == null) {
                Toast.makeText(getApplicationContext(), "삭제된 마커입니다.\n앱을 다시 실행해주세요!", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                JsonApi_danger.danger_item selectedDangerItem = findThisDangerMarkerItem(((Marker) overlay).getPosition(), danger_list);
                String reportContent = selectedDangerItem.getReportContent();
                String nickName = selectedDangerItem.getNickName();
                String serverReportDate = selectedDangerItem.getReportDate();
                String reportImage = selectedDangerItem.getReportImage();
                String reportIdx = selectedDangerItem.getIndex();
                System.out.println("리스트 검색 결과 : " + reportContent + "," + nickName + "," + serverReportDate);
                System.out.println("위험지역 인덱스 번호 : " + reportIdx);

                //수정요청 관리자 <- 참고해서, 메인 앱에서 수정요청 패치 보내는법 (안되면 @곽정아)
                //검색을 좀 해볼테니,,,가까운 위치의 데이터가 먼저 뜨는거

                String clientReportDate;
                //서버에서 보내준 시간 String을 클라이언트 형식에 맞게 파싱
                OffsetDateTime odt = OffsetDateTime.parse(serverReportDate);
                OffsetDateTime odtTruncatedToWholeSecond = odt.truncatedTo(ChronoUnit.MINUTES);
                clientReportDate = odtTruncatedToWholeSecond.format(DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm")).replace("T", " ");

                String tag = String.valueOf(overlay.getTag());
                dangerInfoFragment = new DangerDetailSheet(tag, reportContent, clientReportDate, nickName, reportImage, reportIdx);
                dangerInfoFragment.show(getSupportFragmentManager(), "dangerInfoSheet");
//            repot_message.setVisibility(View.INVISIBLE);
//            Report_button.setVisibility(View.INVISIBLE);


                cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.4f)).animate(CameraAnimation.Linear);
                naverMap.moveCamera(cameraUpdate);

                return true;
            }
        } else if (overlay instanceof Marker) {
            Object object = overlay.getTag();
            String tag = String.valueOf(object);

            String name;
            String location;
            String week;
            String weekend;
            String holiday;
            String phone;


            //클릭이벤트가 일어난 마커가 어느 타입인지 search
            switch (tag) {
                case "charge":
                    if (findThisChargerMarkerItem(((Marker) overlay).getPosition(), charge_list) == null) {
                        Toast.makeText(getApplicationContext(), "서버와 연결상태가 좋지 않습니다.\n앱을 다시 실행해주세요!", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        JsonApi_charge.charge_item selectedChargeItem = findThisChargerMarkerItem(((Marker) overlay).getPosition(), charge_list);
                        System.out.println(charge_list.size() + "ㅇㅇ");
                        location = selectedChargeItem.getLocation();
                        week = selectedChargeItem.getWeek();
                        weekend = selectedChargeItem.getWeekend();
                        holiday = selectedChargeItem.getHoliday();

                        System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
                        infoFragment = new LocationBottomSheet(tag, location, week, holiday, (Marker) overlay);
                        cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.3f)).animate(CameraAnimation.Linear);
                        break;
                    }
                case "phar":
                    if (findThisPharmacyMarkerItem(((Marker) overlay).getPosition(), phar_list) == null) {
                        Toast.makeText(getApplicationContext(), "서버와 연결상태가 좋지 않습니다.\n앱을 다시 실행해주세요!", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        JsonApi_phar.phar_item selectedPharItem = findThisPharmacyMarkerItem(((Marker) overlay).getPosition(), phar_list);
                        name = selectedPharItem.getName();
                        location = selectedPharItem.getLocation();
                        week = selectedPharItem.getWeek();
                        weekend = selectedPharItem.getWeekend();
                        holiday = selectedPharItem.getHoliday();
                        phone = selectedPharItem.getPhone();

                        System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
                        infoFragment = new LocationBottomSheet(tag, name, location, week, holiday, phone, (Marker) overlay);
                        cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.25f)).animate(CameraAnimation.Linear);

                        break;
                    }
                case "hos":
                    if (findThisHosMarkerItem(((Marker) overlay).getPosition(), hos_list) == null) {
                        Toast.makeText(getApplicationContext(), "서버와 연결상태가 좋지 않습니다.\n앱을 다시 실행해주세요!", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        JsonApi_hos.hos_item selectedHosItem = findThisHosMarkerItem(((Marker) overlay).getPosition(), hos_list);
                        name = selectedHosItem.getName();
                        location = selectedHosItem.getLocation();
                        week = selectedHosItem.getWeek();
                        weekend = selectedHosItem.getWeekend();
                        holiday = selectedHosItem.getHoliday();
                        phone = selectedHosItem.getPhone();

                        System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
                        infoFragment = new LocationBottomSheet(tag, name, location, week, holiday, phone, (Marker) overlay);
                        cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.25f)).animate(CameraAnimation.Linear);

                        break;
                    }
                case "office":
                    if (findThisFacilityMarkerItem(((Marker) overlay).getPosition(), fac_list) == null) {
                        Toast.makeText(getApplicationContext(), "서버와 연결상태가 좋지 않습니다.\n앱을 다시 실행해주세요!", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        JsonApi_fac.fac_item selectedFacilityItem = findThisFacilityMarkerItem(((Marker) overlay).getPosition(), fac_list);
                        name = selectedFacilityItem.getName();
                        location = selectedFacilityItem.getLocation();
                        week = selectedFacilityItem.getWeek();
                        weekend = selectedFacilityItem.getWeekend();
                        holiday = selectedFacilityItem.getHoliday();
                        phone = selectedFacilityItem.getPhone();
                        System.out.println("리스트 검색 결과 : " + location + "," + week + "," + weekend + "," + holiday);
                        infoFragment = new LocationBottomSheet(tag, name, location, week, holiday, phone, (Marker) overlay);
                        cameraUpdate = CameraUpdate.scrollAndZoomTo(selectedPosition, 16).pivot(new PointF(0.5f, 0.4f)).animate(CameraAnimation.Linear);
                        break;
                    }
            }
        }

        //LocationDetailFragment infoFragment = new LocationDetailFragment(tag);
        infoFragment.show(getSupportFragmentManager(), "infoFragment");
//        repot_message.setVisibility(View.INVISIBLE);
//        Report_button.setVisibility(View.INVISIBLE);

        Log.d("clickable?", String.valueOf(clickable));

        naverMap.moveCamera(cameraUpdate);

        return true;
    }


    JsonApi_charge.charge_item findThisChargerMarkerItem(LatLng location, ArrayList<JsonApi_charge.charge_item> list) {
        JsonApi_charge.charge_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_charge.charge_item item = list.get(i);
            if (location.equals(item.getLatLng())) {
                selectedItem = item;
            }
        }
        return selectedItem;
    }

    JsonApi_hos.hos_item findThisHosMarkerItem(LatLng location, ArrayList<JsonApi_hos.hos_item> list) {
        JsonApi_hos.hos_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_hos.hos_item item = list.get(i);
            if (location.equals(item.getLatLng())) {
                selectedItem = item;
                System.out.println("hospital item found!");
            }
        }
        return selectedItem;
    }

    JsonApi_fac.fac_item findThisFacilityMarkerItem(LatLng location, ArrayList<JsonApi_fac.fac_item> list) {
        JsonApi_fac.fac_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_fac.fac_item item = list.get(i);
            if (location.equals(item.getLatLng())) {
                selectedItem = item;
                System.out.println("facility item found!");
            }
        }
        return selectedItem;
    }

    JsonApi_phar.phar_item findThisPharmacyMarkerItem(LatLng location, ArrayList<JsonApi_phar.phar_item> list) {
        JsonApi_phar.phar_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_phar.phar_item item = list.get(i);
            if (location.equals(item.getLatLng())) {
                selectedItem = item;
                System.out.println("pharmacy item found!");
            }
        }
        return selectedItem;
    }

    JsonApi_danger.danger_item findThisDangerMarkerItem(LatLng location, ArrayList<JsonApi_danger.danger_item> list) {
        String thisLat = String.valueOf(location.latitude);
        String thisLng = String.valueOf(location.longitude);
        JsonApi_danger.danger_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_danger.danger_item item = list.get(i);
            if (location.equals(item.getLatLng())) {
                selectedItem = item;
                System.out.println("danger item found!");
            }
        }
        return selectedItem;
    }


    JsonApi_charge.charge_item findSearchChargerMarkerItem(LatLng location, String name, ArrayList<JsonApi_charge.charge_item> list) {
        JsonApi_charge.charge_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_charge.charge_item item = list.get(i);

            if (location.equals(item.getLatLng()) && name.equals(item.getLocation())) {
                selectedItem = item;
            }
        }
        return selectedItem;
    }

    JsonApi_hos.hos_item findSearchHosMarkerItem(LatLng location, String name, ArrayList<JsonApi_hos.hos_item> list) {
        JsonApi_hos.hos_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_hos.hos_item item = list.get(i);
            if (location.equals(item.getLatLng()) && name.equals(item.getName())) {
                selectedItem = item;
                System.out.println("hospital item found!");
            }
        }
        return selectedItem;
    }

    JsonApi_fac.fac_item findSearchFacilityMarkerItem(LatLng location, String name, ArrayList<JsonApi_fac.fac_item> list) {
        JsonApi_fac.fac_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_fac.fac_item item = list.get(i);
            if (location.equals(item.getLatLng()) && name.equals(item.getName())) {
                selectedItem = item;
                System.out.println("facility item found!");
            }
        }
        return selectedItem;
    }

    JsonApi_phar.phar_item findSearchPharmacyMarkerItem(LatLng location, String name, ArrayList<JsonApi_phar.phar_item> list) {
        JsonApi_phar.phar_item selectedItem = null;

        for (int i = 0; i < list.size(); i++) {
            JsonApi_phar.phar_item item = list.get(i);
            if (location.equals(item.getLatLng()) && name.equals(item.getName())) {
                selectedItem = item;
                System.out.println("pharmacy item found!");
            }
        }
        return selectedItem;
    }


    @Override
    public void onBackPressed() {
        if (isDrawerOpen) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            isDrawerOpen = false;
        } else if (bfragment == true) {
            getSupportFragmentManager().popBackStack();
            bfragment = false;
        } else {
            if (clickable) {
                long tempTime = System.currentTimeMillis();
                long intervalTime = tempTime - presstime;

                if (0 <= intervalTime && finishtimeed >= intervalTime) {
                    finish();
                } else {
                    presstime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                super.onBackPressed();
                ImageButton message_button = (ImageButton) findViewById(R.id.message_button);
                ImageButton Report_button = (ImageButton) findViewById(R.id.repot_button);
                message_button.setVisibility(View.VISIBLE);
                Report_button.setVisibility(View.VISIBLE);
                clickable = true;

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

    public String getSimpleCurrentAddress(String currAddress) {
        String addrCut[] = currAddress.split(" ");
        String simpleAdress;

        if (addrCut.length >= 5) {
            simpleAdress = addrCut[2] + " " + addrCut[3] + " " + addrCut[4];
        } else if (addrCut.length >= 4) {
            simpleAdress = addrCut[2] + " " + addrCut[3];
        } else if (addrCut.length >= 3) {
            simpleAdress = addrCut[1] + " " + addrCut[2];
        } else if (addrCut.length >= 2) {
            simpleAdress = addrCut[0] + " " + addrCut[1];
        } else if (addrCut.length >= 1) {
            simpleAdress = addrCut[0];
        } else {
            simpleAdress = "위치 정보 없음";
        }

        return simpleAdress;
    }


    public String getCurrentAddress(double latitude, double longitude) {
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

        return address.getAddressLine(0);
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

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, id);
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

    public void Dead() {
        finish();
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


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentPosition).animate(CameraAnimation.Fly, 0);
        naverMap.moveCamera(cameraUpdate);
        this.naverMap = naverMap;

        SharedPreferences total1 = getSharedPreferences("total", Activity.MODE_PRIVATE);
        SharedPreferences hos2 = getSharedPreferences("hos2", Activity.MODE_PRIVATE);
        SharedPreferences fac3 = getSharedPreferences("fac3", Activity.MODE_PRIVATE);
        SharedPreferences charge4 = getSharedPreferences("charge4", Activity.MODE_PRIVATE);
        SharedPreferences wheel5 = getSharedPreferences("wheel5", Activity.MODE_PRIVATE);
        SharedPreferences ele6 = getSharedPreferences("ele6", Activity.MODE_PRIVATE);
        SharedPreferences bike7 = getSharedPreferences("bike7", Activity.MODE_PRIVATE);
        SharedPreferences slope8 = getSharedPreferences("slope8", Activity.MODE_PRIVATE);
        SharedPreferences danger9 = getSharedPreferences("danger9", Activity.MODE_PRIVATE);
        SharedPreferences lift10 = getSharedPreferences("lift10", Activity.MODE_PRIVATE);
        SharedPreferences phar11 = getSharedPreferences("phar11", Activity.MODE_PRIVATE);


        if (total1.getBoolean("total", true)) {
            setMarker_hos(); //병원
            drawMarker_bike();
            setMarker_Charge();
            drawMarker_slope();
            setMarker_danger();
            drawMarker_ele();
            drawMarker_lift();
            drawMarker_wheel();
            setMarker_fac();
            setMarker_phar();
            System.out.println(hos_list.size() + "왜3");
            System.out.println(phar_list.size() + "왜3");
            System.out.println(lift_list.size() + "왜3");
            System.out.println(fac_list.size() + "왜3");
            System.out.println(charge_list.size() + "왜3");
            System.out.println(ele_list.size() + "왜3");
            MainActivity.hos_list.clear();

        } else {

            if (hos2.getBoolean("total", true)) {
                setMarker_hos(); //병원
            }
            if (fac3.getBoolean("total", true)) {
                setMarker_fac();
            }
            if (charge4.getBoolean("total", true)) {
                setMarker_Charge();
            }
            if (wheel5.getBoolean("total", true)) {
                drawMarker_wheel();
            }
            if (ele6.getBoolean("total", true)) {
                drawMarker_ele();
            }
            if (lift10.getBoolean("total", true)) {
                drawMarker_lift();
            }
            if (bike7.getBoolean("total", true)) {
                drawMarker_bike();
            }
            if (slope8.getBoolean("total", true)) {
                drawMarker_slope();
            }
            if (danger9.getBoolean("total", true)) {
                setMarker_danger();
            }
            if (phar11.getBoolean("total", true)) {
                setMarker_phar();
            }
            System.out.println(hos_list.size() + "왜2");
            System.out.println(phar_list.size() + "왜2");
            System.out.println(lift_list.size() + "왜2");
            System.out.println(fac_list.size() + "왜2");
            System.out.println(charge_list.size() + "왜2");
            System.out.println(ele_list.size() + "왜2");
            MainActivity.hos_list.clear();

        }

        //충전기
        naverMap.setMaxZoom(19.0);
        naverMap.setMinZoom(2.0);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(true); //줌인 줌아웃
        uiSettings.setLocationButtonEnabled(true);


        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        final TextView location_text = findViewById(R.id.location_text);



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
                if (mLastlocation != null) {
                    deltaTime = (location.getTime() - mLastlocation.getTime());
                    // 속도 계산(시간=ms, 거리=m -> km/h)
                    speed = (mLastlocation.distanceTo(location) / deltaTime) * 3600;
                    calSpeed = Double.parseDouble(String.format("%.3f", speed));
                }
                //현재위치를 지난 위치로 변경
                mLastlocation = location;

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getSimpleCurrentAddress(getCurrentAddress(latitude, longitude));
                location_text.setText(address);

                //0719

                String lat_str = Double.toString(latitude);
                String lon_str = Double.toString(longitude);

                /*
                textView_lat.setText(lat_str);
                textView_lon.setText(lon_str);
                 */

                String gs_str = Double.toString(getSpeed);
                String cs_str = Double.toString(calSpeed);


            }
        });
    }


    //메시지 보내기 함수
    private void sendSms() {
        SmsManager manager = SmsManager.getDefault();

        SharedPreferences spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE); //선택된 연락처 정보 가져오기
        SharedPreferences spfMode = getSharedPreferences("mode", MODE_PRIVATE);     //선택된 유저 모드 정보 가져오기
        SharedPreferences spfAddress = getSharedPreferences("location", MODE_PRIVATE);     //위치 정보 보내기
        String text = spfMode.getString("text", ""); //교통약자인지 판별 후 그에 맞는 기본 메세지 가져오기

        int personId = spfPersonInfo.getInt("personId", -1);   //선택된 연락처의 유저 특정하기 위한 id

        PersonInfoDatabase personInfoDatabase = PersonInfoDatabase.Companion.getInstance(this);
        double latitude;
        double longitude;

        try {
            latitude = gpsTracker.getLatitude();
        } catch (Exception e) {
            latitude = 37.496787860046965;
        }
        try {
            longitude = gpsTracker.getLongitude();
        } catch (Exception e) {
            longitude = 126.94575323439247;
        }

        String address = getSimpleCurrentAddress(getCurrentAddress(latitude, longitude));
        if (personId != -1) {    //선택된 연락처가 있을 때

            Log.d("데이타 베이스 확인 ! ! !", personInfoDatabase.personInfoDao().getPersonList().toString());
            Log.d("데이타 베이스 번호", personInfoDatabase.personInfoDao().getPhoneNumber(personId));
            Log.d("데이타 베이스 텍스트", personInfoDatabase.personInfoDao().getText(personId));

            if (!(personInfoDatabase.personInfoDao().getText(personId).equals(""))) {  //텍스트 입력한 기록이 있는 연락처에 한정
                //선택된 연락처의 번호로 기본 메세지 + 기록된 메세지 전송
                manager.sendTextMessage(personInfoDatabase.personInfoDao().getPhoneNumber(personId), null, text + "\n" + "위치: " + address +"\n"+ personInfoDatabase.personInfoDao().getText(personId), null, null);
            } else {   //선택된 연락처의 번호로 기본 메세지만 전송
                manager.sendTextMessage(personInfoDatabase.personInfoDao().getPhoneNumber(personId), null, text + "\n" + "위치: "+ address +"\n", null, null);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initClickListener() {

        //긴급신고 메세지지
        ImageButton Report_message = findViewById(R.id.message_button);
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
                        editor.putBoolean("Permission_touch", true);
                        editor.commit();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    } else {
                        if (Permission_touch == true) {
                            //3번째부터
                            Toast.makeText(getApplicationContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                        } else {
                            //1번째 퍼미션 시도
                        }
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }//팝업 이어갈 예정
                } else {
                    //퍼미션 허용받으면 이쪽입니다~~~
                    SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
                    boolean first_touch = pref.getBoolean("isFirst", false);

                    System.out.println("허용");
                    //이전 선택된 연락처 기록 가져오기
                    SharedPreferences spfPersonInfo = getSharedPreferences("personInfo", MODE_PRIVATE);
                    int personId = spfPersonInfo.getInt("personId", -1);

                    PersonInfoDatabase personInfoDatabase = PersonInfoDatabase.Companion.getInstance(getApplicationContext());
                    String name = personInfoDatabase.personInfoDao().getName(personId);
                    String phoneNumber = personInfoDatabase.personInfoDao().getPhoneNumber(personId);

//                    if(name.equals("") && phoneNumber.equals("")){    //연락처 선택한 기록이 없을 시 연락처 추가하기로 이동 (저장된 연락처 확인하고 이것도 없으면 추가하기로 이동하는게 낫지 않을까요?)
//                        Intent intent = new Intent(getApplicationContext(), AddPhoneBookActivity.class);
//                        startActivity(intent);
//                    }


                    if (personId == -1) {
                        //앱 최초 실행시 하고 싶은 작업
                        View dialogView = getLayoutInflater().inflate(R.layout.first_popup, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();
                        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
                        InsetDrawable inset = new InsetDrawable(back, 24);
                        alertDialog.getWindow().setBackgroundDrawable(inset);
                        alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
                        alertDialog.show();

                        TextView noButton = alertDialog.findViewById(R.id.first_no_button);
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        TextView yesButton = alertDialog.findViewById(R.id.first_yes_button);
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), EmergencyCallActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "연락처를 클릭해서 문자를 전송할 연락처를 선택해주세요!", Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("isFirst", true);
                                editor.commit();
                                alertDialog.dismiss();


                            }
                        });
//                        alertDialog.show();
                    } else {

                        View dialogView = getLayoutInflater().inflate(R.layout.mesaage_dialog, null);
                        TextView set11;
                        set11 = (TextView) dialogView.findViewById(R.id.text_dialog_);
                        set11.setText(name + set11.getText());


                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();
                        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
                        InsetDrawable inset = new InsetDrawable(back, 24);
                        alertDialog.getWindow().setBackgroundDrawable(inset);
                        alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
                        alertDialog.show();

//                        String nameText = TextView(mContext)
//                        nameText.S(name);
//                        text.setText(nameText.text.toString() + text.text.toString());

                        TextView noButton = alertDialog.findViewById(R.id.tv_no_dialog);
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        TextView yesButton = alertDialog.findViewById(R.id.tv_yes_dialog);
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendSms();
                                Toast.makeText(getApplicationContext(), "긴급 문자가 전송되었습니다!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();

                            }
                        });
                        /*                        alertDialog.show();*/
                    }
                }

            }
        });

        ImageButton Report_button = findViewById(R.id.repot_button);
        Report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.camera_detail, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
                InsetDrawable inset = new InsetDrawable(back, 24);
                alertDialog.getWindow().setBackgroundDrawable(inset);
                alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
                alertDialog.show();

                TextView noButton = alertDialog.findViewById(R.id.tv_no_camera);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                TextView yesButton = alertDialog.findViewById(R.id.tv_yes_camera);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double latitude;
                        double longitude;

                        try {
                            latitude = gpsTracker.getLatitude();
                        } catch (Exception e) {
                            latitude = 37.496787860046965;
                        }
                        try {
                            longitude = gpsTracker.getLongitude();
                        } catch (Exception e) {
                            longitude = 126.94575323439247;
                        }

                        String address = getSimpleCurrentAddress(getCurrentAddress(latitude, longitude));


                        SimpleDateFormat timeForServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String sReportDate = timeForServer.format(new Date());

                        SimpleDateFormat timeForClient = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                        String cReportDate = timeForClient.format(new Date());

                        System.out.println("현재 위치 : " + address);

                        Intent reportIntent = new Intent(getApplicationContext(), Report_detail.class);


                        reportIntent.putExtra("reportLat", latitude);    //서버 위도 경도
                        reportIntent.putExtra("reportLng", longitude);      // 여기부분 gps traker에서 가져오게 해달라고 하셨었나?
                        // 이거 값 이상하면 바로 윗줄 latitude,longitude로 주기
                        reportIntent.putExtra("address", address);   //사용자 화면 주소
                        reportIntent.putExtra("sReportDate", sReportDate);
                        reportIntent.putExtra("cReportDate", cReportDate);


                        startActivity(reportIntent);
                        alertDialog.dismiss();
                    }
                });

            }
        });


        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {            //menu 클릭 시 open drawer
                binding.drawerLayout.openDrawer(GravityCompat.START);
                isDrawerOpen = true;
            }
        });

        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bfragment = true;
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.main_frame, new Fragment_search()).addToBackStack(null).commit();
            }
        });


        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(intent);
                isFilter = true;
                //바텀 시트로 구현. 룸디비 사용시에 이걸로 변경
//                filter_bottom_sheet bottomSheetFragment = new filter_bottom_sheet();
//                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });


        View header = binding.navigationView.getHeaderView(0);
        ImageView closeImage = header.findViewById(R.id.iv_close);

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // X 클릭 시 close drawer
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //메뉴판 클릭 시
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_notification) {    //알림
//                 binding.drawerLayout.closeDrawer(GravityCompat.START); //열려있는 메뉴판 닫고 화면 전환

                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_call) {     //긴급연락처
                    Intent intent = new Intent(getApplicationContext(), EmergencyCallActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_report) {   //위험 제보하기
                    cameraDialog();
                } else if (item.getItemId() == R.id.nav_book) {        //이용 설명서
                    Intent intent = new Intent(getApplicationContext(), MenuBookActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_review) {      //사용자 리뷰
                    popDialog("모아도에 대한 리뷰를 남기시겠습니까?");
                } else if (item.getItemId() == R.id.nav_oss) {         //오픈소스 라이선스
                    Intent intent = new Intent(getApplicationContext(), OssActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }


    private void cameraDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.camera_detail, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 24);
        alertDialog.getWindow().setBackgroundDrawable(inset);
        alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
        alertDialog.show();

        TextView noButton = alertDialog.findViewById(R.id.tv_no_camera);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        TextView yesButton = alertDialog.findViewById(R.id.tv_yes_camera);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude;
                double longitude;

                try {
                    latitude = gpsTracker.getLatitude();
                } catch (Exception e) {
                    latitude = 37.496787860046965;
                }
                try {
                    longitude = gpsTracker.getLongitude();
                } catch (Exception e) {
                    longitude = 126.94575323439247;
                }

                String address = getSimpleCurrentAddress(getCurrentAddress(latitude, longitude));


                SimpleDateFormat timeForServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String sReportDate = timeForServer.format(new Date());

                SimpleDateFormat timeForClient = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                String cReportDate = timeForClient.format(new Date());


                Intent reportIntent = new Intent(getApplicationContext(), Report_detail.class);


                reportIntent.putExtra("reportLat", latitude);    //서버 위도 경도
                reportIntent.putExtra("reportLng", longitude);      // 여기부분 gps traker에서 가져오게 해달라고 하셨었나?
                // 이거 값 이상하면 바로 윗줄 latitude,longitude로 주기
                reportIntent.putExtra("address", address);   //사용자 화면 주소
                reportIntent.putExtra("sReportDate", sReportDate);
                reportIntent.putExtra("cReportDate", cReportDate);


                startActivity(reportIntent);
                alertDialog.dismiss();
            }
        });


    }


    private void popDialog(String text) {
        Dialog dialog = new InfoDialog(this);
        dialog.show();

        TextView tv = (TextView) dialog.findViewById(R.id.text_dialog);
        tv.setText(text);

        TextView noButton = dialog.findViewById(R.id.tv_no_dialog);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView yesButton = dialog.findViewById(R.id.tv_yes_dialog);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com")));    //출시 전에 이동 주소 구글 마켓으로 바꾸기
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    private void removeMarkerAll() {
        for (Marker marker : TotalmarkerList) {
            marker.setMap(null); // 삭제
        }

    }

    //의료기관setMarker_facility_delete
    public void setMarker_hos() {
        for (int i = 0; i < hos_list.size(); i++) {
            JsonApi_hos.hos_item item = hos_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "hos", naverMap);
        }

        return;
    }

    private void setMarker_fac() {
        for (int i = 0; i < fac_list.size(); i++) {
            JsonApi_fac.fac_item item = fac_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "office", naverMap);
        }
        return;
    }


    private void setMarker_phar() {
        for (int i = 0; i < phar_list.size(); i++) {
            JsonApi_phar.phar_item item = phar_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "phar", naverMap);
        }
        return;
    }


    //충전기
    private void setMarker_Charge() {
        for (int i = 0; i < charge_list.size(); i++) {
            JsonApi_charge.charge_item item = charge_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "charge", naverMap);
        }
        return;
    }

    //위험지역
    public void setMarker_danger() {
        for (int i = 0; i < danger_list.size(); i++) {
            JsonApi_danger.danger_item item = danger_list.get(i);
            setMarker_facility(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "danger", naverMap);
        }
        return;
    }


    //자전거 사고 다발지역 만들기
    private void drawMarker_bike() {
        for (int i = 0; i < bike_list.size(); i++) {
            JsonApi_bike.bike_item item = bike_list.get(i);
            AccidentCircle_bike((Double.parseDouble(item.getLat())), Double.parseDouble(item.getLng()), "자전거 사고다발 지역");
        }
        return;
    }

    //승강기
    private void drawMarker_ele() {
        for (int i = 0; i < ele_list.size(); i++) {
            JsonApi_ele.ele_item item = ele_list.get(i);
            AccidentCircle(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "외부 승강기");
        }
        return;
    }

    //휠체어 리프트
    private void drawMarker_lift() {
        for (int i = 0; i < lift_list.size(); i++) {
            JsonApi_lift.lift_item item = lift_list.get(i);
            AccidentCircle(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "휠체어 리프트");
        }
        return;
    }


    //경사로
    private void drawMarker_wheel() {
        for (int i = 0; i < wheel_list.size(); i++) {
            JsonApi_wheel.wheel_item item = wheel_list.get(i);
            AccidentCircle(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()), "경사로");
        }
        return;
    }


    //급경사로 지역 만들기
    private void drawMarker_slope() {
        for (int i = 0; i < slope_list.size(); i++) {
            JsonApi_slope.slope_item item = slope_list.get(i);
            AccidentCircle((Double.parseDouble(item.getLat())), Double.parseDouble(item.getLng()), "급경사 지역");
        }
        return;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    //자전거 사고 다발지역 info window
    private void AccidentCircle_bike(double x, double y, String z) {
        CircleOverlay circle = new CircleOverlay();
        circle.setCenter(new LatLng(x, y));
        circle.setRadius(30);
        circle.setColor(Color.parseColor("#20FF7B00"));
        circle.setOutlineColor(Color.parseColor("#30FF7B00"));
        circle.setMinZoom(13);//줌 설정
        circle.setMap(naverMap);

        InfoWindow infoWindow = new InfoWindow();
        Marker marker = new Marker();

        marker.setPosition(new LatLng(x, y));
        marker.setMinZoom(13);//줌 설정

        marker.setIcon(OverlayImage.fromResource(R.drawable.danger_location_yellow));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMap(naverMap);

        marker.setTag(z);
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });
        infoWindow.setAlpha(0.8f);
        Overlay.OnClickListener listener = overlay -> {
            naverMap.setOnMapClickListener((coord, point) -> {
                infoWindow.close();
                if (infoFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(infoFragment).commit();
                }
                if (dangerInfoFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(dangerInfoFragment).commit();
                }

                getSupportFragmentManager().popBackStack();
                dangerInfoFragment = null;
                infoFragment = null;

                clickable = true;

                ImageButton repot_message = (ImageButton) findViewById(R.id.message_button);
                ImageButton Report_button = (ImageButton) findViewById(R.id.repot_button);

                repot_message.setVisibility(View.VISIBLE);
                Report_button.setVisibility(View.VISIBLE);
            });
            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
                Handler handler = new Handler();
                if (marker.getInfoWindow() != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            infoWindow.close();
                        }
                    }, 3000);    //3초 동안 딜레이
                }
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };
        marker.setOnClickListener(listener);

    }


    //경사로 info window
    private void AccidentCircle(double x, double y, String z) {
        InfoWindow infoWindow = new InfoWindow();
        Marker marker = new Marker();
        marker.setPosition(new LatLng(x, y));
        marker.setMinZoom(13);//줌 설정
        int resourceId = setMarkerIconResource(z);
        marker.setIcon(OverlayImage.fromResource(resourceId));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMap(naverMap);

        marker.setTag(z);
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence) infoWindow.getMarker().getTag();
            }
        });
        infoWindow.setAlpha(0.8f);
        Overlay.OnClickListener listener = overlay -> {
            naverMap.setOnMapClickListener((coord, point) -> {
                infoWindow.close();
                if (infoFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(infoFragment).commit();
                }
                if (dangerInfoFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(dangerInfoFragment).commit();
                }

                getSupportFragmentManager().popBackStack();
                dangerInfoFragment = null;
                infoFragment = null;

                clickable = true;

                ImageButton repot_message = (ImageButton) findViewById(R.id.message_button);
                ImageButton Report_button = (ImageButton) findViewById(R.id.repot_button);

                repot_message.setVisibility(View.VISIBLE);
                Report_button.setVisibility(View.VISIBLE);
            });
            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
                Handler handler = new Handler();
                if (marker.getInfoWindow() != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            infoWindow.close();
                        }
                    }, 3000);    //3초 동안 딜레이
                }
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };
        marker.setOnClickListener(listener);

    }

    private int setMarkerIconResource(String tag) {
        int ResourceId;
        switch (tag) {
            case "급경사 지역":
                ResourceId = R.drawable.danger_location_yellow;
                break;
            case "외부 승강기":
                ResourceId = R.drawable.wheel_icon;
                break;
            case "경사로":
                ResourceId = R.drawable.wheel_icon;
                break;
            case "휠체어 리프트":
                ResourceId = R.drawable.wheel_icon;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tag);
        }
        return ResourceId;
    }
}