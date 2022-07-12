package com.abilitymap;

import androidx.annotation.NonNull;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

//경사로,충전기

public interface SetMarker_wheel extends Overlay.OnClickListener {
    public default void setMarker_wheel(double x, double y, String markerType, NaverMap naverMap){

        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMinZoom(11);//줌 설정
        switch(markerType){
            case "wheel": marker.setIcon(OverlayImage.fromResource(R.drawable.wheel_icon)); break;
            case "slope": marker.setIcon(OverlayImage.fromResource(R.drawable.danger_location_yellow)); break;
        }
        marker.setMap(naverMap);

        marker.setOnClickListener(this);
    }

    @Override
    boolean onClick(@NonNull Overlay overlay);


}