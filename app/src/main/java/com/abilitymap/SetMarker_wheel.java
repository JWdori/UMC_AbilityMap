package com.abilitymap;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

//경사로,충전기

public interface SetMarker_wheel extends Overlay.OnClickListener {

    public default void setMarker_wheel(double x, double y, String markerType, NaverMap naverMap, String z){
        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMinZoom(12);//줌 설정
        switch(markerType){
            case "ele": marker.setIcon(OverlayImage.fromResource(R.drawable.wheel_icon)); break;
            case "slope": marker.setIcon(OverlayImage.fromResource(R.drawable.danger_location_yellow)); break;
        }
        marker.setMap(naverMap);
        InfoWindow infoWindow = new InfoWindow();
        marker.setTag(z);
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter((Context) this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });
        infoWindow.setAlpha(0.8f);
        Overlay.OnClickListener listener = overlay -> {
            naverMap.setOnMapClickListener((coord, point) -> {
                infoWindow.close();
            });
            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
                Handler handler = new Handler();
                if(marker.getInfoWindow() != null){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            infoWindow.close();
                        }
                    },3000);	//3초 동안 딜레이
                }
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };
        marker.setOnClickListener(listener);

        marker.setOnClickListener(this);
    }

    @Override
    boolean onClick(@NonNull Overlay overlay);


}