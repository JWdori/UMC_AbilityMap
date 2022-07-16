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



        if (total1.getBoolean("total",true)) {
            labeledSwitch_total1.setOn(true);
            labeledSwitch_hos2.setOn(true);
            labeledSwitch_fac3.setOn(true);
            labeledSwitch_charge4.setOn(true);
            labeledSwitch_wheel5.setOn(true);
            labeledSwitch_ele6.setOn(true);
            labeledSwitch_bike7.setOn(true);
            labeledSwitch_slope8.setOn(true);
            labeledSwitch_danger9.setOn(true);
        }else{
            labeledSwitch_total1.setOn(false);
            labeledSwitch_hos2.setOn(false);
            labeledSwitch_fac3.setOn(false);
            labeledSwitch_charge4.setOn(false);
            labeledSwitch_wheel5.setOn(false);
            labeledSwitch_ele6.setOn(false);
            labeledSwitch_bike7.setOn(false);
            labeledSwitch_slope8.setOn(false);
            labeledSwitch_danger9.setOn(false);
        }

        if (hos2.getBoolean("total",true)) {
            labeledSwitch_hos2.setOn(true);
            System.out.println(hos2.getAll()+"ㅎㅇ");
            System.out.println("토탈트루");
        }else{
            labeledSwitch_hos2.setOn(false);
        }

        if (fac3.getBoolean("total",true)) {
            labeledSwitch_fac3.setOn(true);
        }else{
            labeledSwitch_fac3.setOn(false);
        }

        if ((charge4==null) || (charge4.getBoolean("total",true))) {
            labeledSwitch_charge4.setOn(true);
        }else{
            labeledSwitch_charge4.setOn(false);
        }

        if ((wheel5==null) || (wheel5.getBoolean("total",true))) {
            labeledSwitch_wheel5.setOn(true);
        }else{
            labeledSwitch_wheel5.setOn(false);
        }

        if ((ele6==null) || (ele6.getBoolean("total",true))) {
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



        if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&(charge4==null) || (charge4.getBoolean("total",true))
                &&(wheel5==null) || (wheel5.getBoolean("total",true))&&(ele6==null) || (ele6.getBoolean("total",true))&&bike7.getBoolean("total",true)
                &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
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
                }else{
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                System.out.println(total1.getAll());

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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
                }
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                if (hos2.getBoolean("total",true)&&fac3.getBoolean("total",true)&&((charge4==null) || (charge4.getBoolean("total",true)))
                        &&((wheel5==null) || (wheel5.getBoolean("total",true)))&&((ele6==null) || (ele6.getBoolean("total",true)))&&bike7.getBoolean("total",true)
                        &&slope8.getBoolean("total",true)&&danger9.getBoolean("total",true)){
                    labeledSwitch_total1.setOn(true);
                }else{
                    labeledSwitch_total1.setOn(false);
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
                startActivity(intent); //액티비티 열기
                firstActivity.finish();
                finish();//인텐트 종료
            }

        });
    }
}