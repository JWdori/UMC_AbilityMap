package com.abilitymap;

import android.content.Context;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abilitymap.MainActivity;

public class LocationDetailFragment extends Fragment {
    MainActivity activity;
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
    String phone=" ";
    String finalPhone;


    public LocationDetailFragment(){
    }
    public LocationDetailFragment(String tag){
        categoryKey = tag;
    }

    public LocationDetailFragment(String tag,String location,String week,String holiday){
        this.categoryKey = tag;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
    }
    public LocationDetailFragment(String tag,String name, String location,
                                  String week,String holiday,String phone){
        this.categoryKey = tag;
        this.name = name;
        this.location = location;
        this.week = week;
        this.holiday = holiday;
        this.phone = phone;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity) getActivity();

    }
    public void onDetach() {
        super.onDetach();
        activity = null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.location_detail, container, false);
        categoryTextView = (TextView) rootView.findViewById(R.id.detail_category);
        locationNameView = (TextView) rootView.findViewById(R.id.location_name_textView);
        locationDetailView = (TextView) rootView.findViewById(R.id.location_detail_textView);
        weekTextView = (TextView) rootView.findViewById(R.id.hour_detail_textView);
        phoneTextView = (TextView) rootView.findViewById(R.id.phone_detail_textView);
        phoneImageView = (ImageView) rootView.findViewById(R.id.phone_icon_imageView);



        setCategory(categoryKey);

        String finalName = setDefault(name);
        String finalLocation = setDefault(location);
        String finalWeek = setDefault_blank(week);
        finalPhone = setDefault_blank(phone);

        setName(finalName);
        setLocationDetail(finalLocation);
        setWeek(finalWeek);
        setPhone(finalPhone);


        return rootView;
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

