package com.abilitymap;

import androidx.annotation.NonNull;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

//병원/약국 + 관공서에 이용되는 마커 파일
public interface SetMarker_facility extends Overlay.OnClickListener {
    public default void setMarker_facility(double x, double y, String markerType, NaverMap naverMap){

        Marker marker = new Marker();
        marker.setPosition(new LatLng(x,y));
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMinZoom(13);//줌 설정
        switch(markerType){
            case "office": marker.setIcon(OverlayImage.fromResource(R.drawable.facility_office)); break;
            case "hos": marker.setIcon(OverlayImage.fromResource(R.drawable.hos_icon)); break;
            case "charge": marker.setIcon(OverlayImage.fromResource(R.drawable.charge_icon)); break;
            case "danger": marker.setIcon(OverlayImage.fromResource(R.drawable.dnager_red)); break;
        }
        marker.setMap(naverMap);
        marker.setTag(markerType);
        marker.setOnClickListener(this);
    }

    @Override
    boolean onClick(@NonNull Overlay overlay);

}