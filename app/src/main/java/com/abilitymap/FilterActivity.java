package com.abilitymap;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.abilitymap.databinding.FirstPopupBinding;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.naver.maps.map.overlay.Overlay;

import java.util.Map;

public class FilterActivity extends AppCompatActivity {
    LabeledSwitch labeledSwitch_total1;
    LabeledSwitch labeledSwitch_hos2;
    LabeledSwitch labeledSwitch_fac3;
    LabeledSwitch labeledSwitch_charge4;
    LabeledSwitch labeledSwitch_wheel5;
    LabeledSwitch labeledSwitch_ele6;
    LabeledSwitch labeledSwitch_bike7;
    LabeledSwitch labeledSwitch_slope8;
    LabeledSwitch labeledSwitch_danger9;
    Button filter_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        overridePendingTransition(R.anim.horizon_enter, R.anim.none)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Filter_close();
        Filter_save();
        filter_button = findViewById(R.id.button_filter);
        SharedPreferences total1 = getSharedPreferences("total", Activity.MODE_PRIVATE);
        SharedPreferences hos2 = getSharedPreferences("hos2", Activity.MODE_PRIVATE);
        SharedPreferences fac3 = getSharedPreferences("fac3", Activity.MODE_PRIVATE);
        SharedPreferences charge4 = getSharedPreferences("charge4", Activity.MODE_PRIVATE);
        SharedPreferences wheel5 = getSharedPreferences("wheel5", Activity.MODE_PRIVATE);
        SharedPreferences ele6 = getSharedPreferences("ele6", Activity.MODE_PRIVATE);
        SharedPreferences bike7 = getSharedPreferences("bike7", Activity.MODE_PRIVATE);
        SharedPreferences slope8 = getSharedPreferences("slope8", Activity.MODE_PRIVATE);
        SharedPreferences danger9 = getSharedPreferences("danger9", Activity.MODE_PRIVATE);
        labeledSwitch_total1 = findViewById(R.id.total_toggle);
        labeledSwitch_hos2 = findViewById(R.id.hos_toggle);
        labeledSwitch_fac3 = findViewById(R.id.facility_toggle);
        labeledSwitch_charge4 = findViewById(R.id.charge_toggle);
        labeledSwitch_wheel5 = findViewById(R.id.wheel_toggle);
        labeledSwitch_ele6 = findViewById(R.id.ele_toggle);
        labeledSwitch_bike7 = findViewById(R.id.bike_toggle);
        labeledSwitch_slope8 = findViewById(R.id.slope_toggle);
        labeledSwitch_danger9 = findViewById(R.id.danger_toggle);

        System.out.println(total1.getAll()+"ㅎㅇ");



//        if (total1.getBoolean("total",true)) {
//            labeledSwitch_total1.setOn(true);
//            labeledSwitch_hos2.setOn(true);
//            labeledSwitch_fac3.setOn(true);
//            labeledSwitch_charge4.setOn(true);
//            labeledSwitch_wheel5.setOn(true);
//            labeledSwitch_ele6.setOn(true);
//            labeledSwitch_bike7.setOn(true);
//            labeledSwitch_slope8.setOn(true);
//            labeledSwitch_danger9.setOn(true);
//        }else if(total1.getBoolean("total",false)){
//            labeledSwitch_total1.setOn(false);
//            labeledSwitch_hos2.setOn(false);
//            labeledSwitch_fac3.setOn(false);
//            labeledSwitch_charge4.setOn(false);
//            labeledSwitch_wheel5.setOn(false);
//            labeledSwitch_ele6.setOn(false);
//            labeledSwitch_bike7.setOn(false);
//            labeledSwitch_slope8.setOn(false);
//            labeledSwitch_danger9.setOn(false);
//        }



        System.out.println(total1.getAll()+"외외외외");
        Map<String, ?> total2 = total1.getAll();
        System.out.println(total2+"외외외외");
        System.out.println(total1.getAll()+"외외외외");



        if (hos2.getBoolean("total",true)) {
            labeledSwitch_hos2.setOn(true);
        }else{
            labeledSwitch_hos2.setOn(false);
        }

        if (fac3.getBoolean("total",true)) {
            labeledSwitch_fac3.setOn(true);
        }else{
            labeledSwitch_fac3.setOn(false);
        }

        if (charge4.getBoolean("total",true)) {
            labeledSwitch_charge4.setOn(true);
        }else{
            labeledSwitch_charge4.setOn(false);
        }

        if (wheel5.getBoolean("total",true)) {
            labeledSwitch_wheel5.setOn(true);
        }else{
            labeledSwitch_wheel5.setOn(false);
        }

        if (ele6.getBoolean("total",true)) {
            labeledSwitch_ele6.setOn(true);
        }else{
            labeledSwitch_ele6.setOn(false);
        }

        if (bike7.getBoolean("total",true)) {
            labeledSwitch_bike7.setOn(true);
        }else{
            labeledSwitch_bike7.setOn(false);
        }

        if (slope8.getBoolean("total",true)) {
            labeledSwitch_slope8.setOn(true);
        }else{
            labeledSwitch_slope8.setOn(false);
        }

        if (danger9.getBoolean("total",true)) {
            labeledSwitch_danger9.setOn(true);
        }else{
            labeledSwitch_danger9.setOn(false);
        }

        if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
            labeledSwitch_total1.setOn(true);
        }else{
            labeledSwitch_total1.setOn(false);
        }





        labeledSwitch_total1.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_total1.isOn()) {
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                    SharedPreferences.Editor editor2 = hos2.edit();
                    editor2.putBoolean("total",true);
                    editor2.commit();
                    labeledSwitch_hos2.setOn(true);
                    SharedPreferences.Editor editor3 = fac3.edit();
                    editor3.putBoolean("total",true);
                    editor3.commit();
                    labeledSwitch_fac3.setOn(true);
                    SharedPreferences.Editor editor4 = charge4.edit();
                    editor4.putBoolean("total",true);
                    editor4.commit();
                    labeledSwitch_charge4.setOn(true);
                    SharedPreferences.Editor editor5 = wheel5.edit();
                    editor5.putBoolean("total",true);
                    editor5.commit();
                    labeledSwitch_wheel5.setOn(true);
                    SharedPreferences.Editor editor6 = ele6.edit();
                    editor6.putBoolean("total",true);
                    editor6.commit();
                    labeledSwitch_ele6.setOn(true);
                    SharedPreferences.Editor editor7 = bike7.edit();
                    editor7.putBoolean("total",true);
                    editor7.commit();
                    labeledSwitch_bike7.setOn(true);
                    SharedPreferences.Editor editor8 = slope8.edit();
                    editor8.putBoolean("total",true);
                    editor8.commit();
                    labeledSwitch_slope8.setOn(true);
                    SharedPreferences.Editor editor9 = danger9.edit();
                    editor9.putBoolean("total",true);
                    editor9.commit();
                    labeledSwitch_danger9.setOn(true);


                }else{
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                    SharedPreferences.Editor editor2 = hos2.edit();
                    editor2.putBoolean("total",false);
                    editor2.commit();
                    labeledSwitch_hos2.setOn(false);
                    SharedPreferences.Editor editor3 = fac3.edit();
                    editor3.putBoolean("total",false);
                    editor3.commit();
                    labeledSwitch_fac3.setOn(false);
                    SharedPreferences.Editor editor4 = charge4.edit();
                    editor4.putBoolean("total",false);
                    editor4.commit();
                    labeledSwitch_charge4.setOn(false);
                    SharedPreferences.Editor editor5 = wheel5.edit();
                    editor5.putBoolean("total",false);
                    editor5.commit();
                    labeledSwitch_wheel5.setOn(false);
                    SharedPreferences.Editor editor6 = ele6.edit();
                    editor6.putBoolean("total",false);
                    editor6.commit();
                    labeledSwitch_ele6.setOn(false);
                    SharedPreferences.Editor editor7 = bike7.edit();
                    editor7.putBoolean("total",false);
                    editor7.commit();
                    labeledSwitch_bike7.setOn(false);
                    SharedPreferences.Editor editor8 = slope8.edit();
                    editor8.putBoolean("total",false);
                    editor8.commit();
                    labeledSwitch_slope8.setOn(false);
                    SharedPreferences.Editor editor9 = danger9.edit();
                    editor9.putBoolean("total",false);
                    editor9.commit();
                    labeledSwitch_danger9.setOn(false);


                }


            }

        });


        labeledSwitch_hos2.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_hos2.isOn()) {
                    SharedPreferences.Editor editor = hos2.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = hos2.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                System.out.println("hos"+hos2.getAll());
            }

        });


        labeledSwitch_fac3.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_fac3.isOn()) {
                    SharedPreferences.Editor editor = fac3.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = fac3.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_charge4.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_charge4.isOn()) {
                    SharedPreferences.Editor editor = charge4.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = charge4.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_wheel5.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_wheel5.isOn()) {
                    SharedPreferences.Editor editor = wheel5.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = wheel5.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_ele6.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_ele6.isOn()) {
                    SharedPreferences.Editor editor = ele6.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = ele6.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_bike7.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_bike7.isOn()) {
                    SharedPreferences.Editor editor = bike7.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = bike7.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_slope8.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_slope8.isOn()) {
                    SharedPreferences.Editor editor = slope8.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = slope8.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });


        labeledSwitch_danger9.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_danger9.isOn()) {
                    SharedPreferences.Editor editor = danger9.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = danger9.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))){
                    labeledSwitch_total1.setOn(true);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    labeledSwitch_total1.setOn(false);
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
            }

        });







    }



    //필터 닫기~~
    private void Filter_close() {
        ImageButton filterclose = findViewById(R.id.filter_close);
        filterclose.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                finish();
            }
        });
    }


    private void Filter_save() {
        MainActivity firstActivity = (MainActivity) MainActivity.firstActivity;
        filter_button = findViewById(R.id.button_filter);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                firstActivity.finish(); //Code to close the previous page main activity
                startActivity(intent); //Go to Main Activity
                finish();//Current activity closed
            }

        });
    }
}