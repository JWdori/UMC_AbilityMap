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
    private TextView weekendTextView;
    private TextView weekTextView;
    private TextView phoneTextView;
    private ImageView phoneImageView;

    String categoryKey;
    //String category;
    String name="default";
    String location="A";
    String week="B";
    String weekend="C";
    String holiday="D";
    String phone="01000000000";


    public LocationDetailFragment(){
    }
    public LocationDetailFragment(String tag){
        categoryKey = tag;
    }

    public LocationDetailFragment(String tag,String location,String week,String weekend,String holiday){
        this.categoryKey = tag;
        this.location = location;
        this.week = week;
        this.weekend = weekend;
        this.holiday = holiday;
    }
    public LocationDetailFragment(String tag,String name, String location,
                                  String week,String weekend,String holiday,String phone){
        this.categoryKey = tag;
        this.name = name;
        this.location = location;
        this.week = week;
        this.weekend = weekend;
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
        weekTextView = (TextView) rootView.findViewById(R.id.week_hour_detail_textView);
        weekendTextView = (TextView) rootView.findViewById(R.id.weekend_hour_detail_textView);
        phoneTextView = (TextView) rootView.findViewById(R.id.phone_detail_textView);
        phoneImageView = (ImageView) rootView.findViewById(R.id.phone_icon_imageView);



        setCategory(categoryKey);
        setName(name);
        setLocationDetail(location);
        setWeek(week);
        setWeekend(weekend);
        setPhone(phone);


        return rootView;
    }

    public void setCategory(String key){
        String category;
        switch (key){
            case "hos":
                category = "보건의료시설";
                break;
            case "office":
                category = "관공서";
                break;
            case "charge":
                category = " ";
                name = "전동휠체어 급속 충전기";
                phone = " ";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        categoryTextView.setText(category);
    }

    public void setName(String name){ locationNameView.setText(name); }
    public void setLocationDetail(String location) {
        locationDetailView.setText(location);
    }
    public void setWeek(String week){ weekTextView.setText("월~금 " + week); }
    public void setWeekend(String weekend){ weekendTextView.setText(weekend); }
    public void setPhone(String phone){
        phoneTextView.setText(phone);
        if(categoryKey.equals("charge")){
        }
        else{
            phoneImageView.setImageResource(R.drawable.icon_24_call);
        }
    }



}

