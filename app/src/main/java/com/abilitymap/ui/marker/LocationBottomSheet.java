package com.abilitymap.ui.marker;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abilitymap.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

public class LocationBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    public static LocationBottomSheet getInstance(){
        return new LocationBottomSheet();
    }
    private TextView categoryTextView;
    private TextView locationNameView;
    private TextView locationDetailView;
    private TextView weekTextView;
    private TextView phoneTextView;
    private ImageView phoneImageView;



    String categoryKey;
    //String category;
    String name="default";
    String location="default";
    String week="default";
    String holiday="default";
    String phone="";
    String finalPhone;
    Marker marker;

    public LocationBottomSheet(){

    }

    public LocationBottomSheet(String tag,String location,String week,String holiday, Marker marker){
        this.categoryKey = tag;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
        this.marker = marker;
    }
    public LocationBottomSheet(String tag,String location,String week,String holiday){
        this.categoryKey = tag;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
    }
    public LocationBottomSheet(String tag,String name, String location,
                                  String week,String holiday,String phone){
        this.categoryKey = tag;
        this.name = name;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
        this.phone = phone;
    }

    public LocationBottomSheet(String tag, String name, String location,
                               String week, String holiday, String phone, Marker marker){
        this.categoryKey = tag;
        this.name = name;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
        this.phone = phone;
        this.marker = marker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.location_detail, container, false);

        categoryTextView = (TextView) view.findViewById(R.id.detail_category);
        locationNameView = (TextView) view.findViewById(R.id.location_name_textView);
        locationDetailView = (TextView) view.findViewById(R.id.location_detail_textView);
        weekTextView = (TextView) view.findViewById(R.id.hour_detail_textView);
        phoneTextView = (TextView) view.findViewById(R.id.phone_detail_textView);
        phoneImageView = (ImageView) view.findViewById(R.id.phone_icon_imageView);

        setCategory(categoryKey);

        String finalName = setDefault(name);
        String finalLocation = setDefault(location);
        String finalWeek = setDefault_blank(week);
        finalPhone = setDefault_blank(phone);

        setName(finalName);
        setLocationDetail(finalLocation);
        setWeek(finalWeek);
        setPhone(finalPhone);

        if(marker!=null) {
            marker.setWidth(200);
            marker.setHeight(200);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);

        if(marker!=null) {
            marker.setWidth(80);
            marker.setHeight(80);
        }
    }


    public void setCategory(String key){
        String category;
        switch (key){
            case "hos":
                category = "보건의료시설";
                break;
            case "office":
                category = "공공/복지시설";
                break;
            case "charge":
                category = " ";
                name = "전동휠체어 급속 충전기";
                finalPhone = " ";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        categoryTextView.setText(category);
    }

    public void setName(String name){

        locationNameView.setText(name); }
    public void setLocationDetail(String location) {
        locationDetailView.setText(location);
    }
    public void setWeek(String week){ weekTextView.setText(week); }
    public void setPhone(String phone){
        phoneTextView.setText(phone);
        if(categoryKey.equals("charge")){
        }
        else{
            phoneImageView.setImageResource(R.drawable.icon_24_call);
        }
    }
    public String setDefault(String input){
        if(input==null){
            return "(정보없음)";
        }
        else return input;
    }

    public String setDefault_blank(String input){
        if(input=="" || input==null){
            return "(정보없음)";
        }
        else return input;
    }

}
