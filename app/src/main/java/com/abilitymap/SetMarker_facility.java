package com.abilitymap;

import androidx.annotation.NonNull;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

import java.util.List;
//병원/약국,공기관,경사로,충전기
public interface SetMarker_facility extends Overlay.OnClickListener {
    public default void setMarker_facility(double x, double y, String markerType, NaverMap naverMap){

        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMinZoom(8);
        switch(markerType){
            case "office": marker.setIcon(OverlayImage.fromResource(R.drawable.facility_office)); break;
            case "hos": marker.setIcon(OverlayImage.fromResource(R.drawable.facility_icon)); break;
        }
        marker.setMap(naverMap);

        marker.setOnClickListener(this);
    }

    @Override
    boolean onClick(@NonNull Overlay overlay);


}