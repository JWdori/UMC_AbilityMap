package com.abilitymap;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
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
    LabeledSwitch labeledSwitch_lift10;
    Button filter_button;
    ProgressDialog dialog; //원형 프로그레스바
    Map<String, ?> total1_;
    Map<String, ?> hos2_;
    Map<String, ?> fac3_;
    Map<String, ?> charge4_;
    Map<String, ?> wheel5_;
    Map<String, ?> ele6_;
    Map<String, ?> bike7_;
    Map<String, ?> slope8_;
    Map<String, ?> danger9_;
    Map<String, ?> lift10_;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      overridePendingTransition(R.anim.horizon_enter, R.anim.none)
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
        SharedPreferences lift10 = getSharedPreferences("lift10", Activity.MODE_PRIVATE);
        labeledSwitch_total1 = findViewById(R.id.total_toggle);
        labeledSwitch_hos2 = findViewById(R.id.hos_toggle);
        labeledSwitch_fac3 = findViewById(R.id.facility_toggle);
        labeledSwitch_charge4 = findViewById(R.id.charge_toggle);
        labeledSwitch_wheel5 = findViewById(R.id.wheel_toggle);
        labeledSwitch_ele6 = findViewById(R.id.ele_toggle);
        labeledSwitch_bike7 = findViewById(R.id.bike_toggle);
        labeledSwitch_slope8 = findViewById(R.id.slope_toggle);
        labeledSwitch_danger9 = findViewById(R.id.danger_toggle);
        labeledSwitch_lift10 = findViewById(R.id.lift_toggle);

        total1_ = total1.getAll();
        hos2_ = hos2.getAll();
        fac3_ = fac3.getAll();
        charge4_ = charge4.getAll();
        wheel5_ = wheel5.getAll();
        ele6_ = ele6.getAll();
        bike7_ = bike7.getAll();
        slope8_ = slope8.getAll();
        danger9_ = danger9.getAll();
        lift10_ = lift10.getAll();


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

        if (total1.getBoolean("total",true)) {
            labeledSwitch_total1.setOn(true);
            System.out.println(total1.getAll()+"첫번째");
        }else{
            labeledSwitch_total1.setOn(false);
        }
        System.out.println(total1.getAll()+"두번째");

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

        if (lift10.getBoolean("total",true)) {
            labeledSwitch_lift10.setOn(true);
        }else{
            labeledSwitch_lift10.setOn(false);
        }

        if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){
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
                    SharedPreferences.Editor editor10 = lift10.edit();
                    editor10.putBoolean("total",true);
                    editor10.commit();
                    labeledSwitch_lift10.setOn(true);


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
                    SharedPreferences.Editor editor10 = lift10.edit();
                    editor10.putBoolean("total",false);
                    editor10.commit();
                    labeledSwitch_lift10.setOn(false);



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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){                 labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){    labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){  labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){     labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){     labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){      labeledSwitch_total1.setOn(true);
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
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){          labeledSwitch_total1.setOn(true);
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



        labeledSwitch_lift10.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_lift10.isOn()) {
                    SharedPreferences.Editor editor = lift10.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = lift10.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                }
                if ((hos2.getBoolean("total",true))&&(fac3.getBoolean("total",true))&&(charge4.getBoolean("total",true))
                        &&(wheel5.getBoolean("total",true))&&(ele6.getBoolean("total",true))&&(bike7.getBoolean("total",true))
                        &&(slope8.getBoolean("total",true))&&(danger9.getBoolean("total",true))&&(lift10.getBoolean("total",true))){          labeledSwitch_total1.setOn(true);
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






    public void onStop(){
        super.onStop();

    }




    //필터 닫기~~
    private void Filter_close() {
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

        ImageButton filterclose = findViewById(R.id.filter_close);
        filterclose.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                finish();
                if(total1_.get("total")==null){
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(total1.getAll().equals(total1_)==false){
                    SharedPreferences.Editor editor = total1.edit();
                    editor.putBoolean("total",(boolean)total1_.get("total"));
                    editor.commit();
                }if(hos2_.get("total")==null){
                    SharedPreferences.Editor editor = hos2.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(hos2.getAll().equals(hos2_)==false){
                    SharedPreferences.Editor editor = hos2.edit();
                    editor.putBoolean("total",(boolean)hos2_.get("total"));
                    editor.commit();
                }

                if(fac3_.get("total")==null) {
                    SharedPreferences.Editor editor = fac3.edit();
                    editor.putBoolean("total", true);
                    editor.commit();
                }else if(fac3.getAll().equals(fac3_)==false){
                    SharedPreferences.Editor editor = fac3.edit();
                    editor.putBoolean("total",(boolean)fac3_.get("total"));
                    editor.commit();
                }


                if(charge4_.get("total")==null){
                    SharedPreferences.Editor editor = charge4.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(charge4.getAll().equals(charge4_)==false){
                    SharedPreferences.Editor editor = charge4.edit();
                    editor.putBoolean("total",(boolean)charge4_.get("total"));
                    editor.commit();
                }
                if(wheel5_.get("total")==null){
                    SharedPreferences.Editor editor = wheel5.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(wheel5.getAll().equals(wheel5_)==false){
                    SharedPreferences.Editor editor = wheel5.edit();
                    editor.putBoolean("total",(boolean)wheel5_.get("total"));
                    editor.commit();
                }
                if(ele6_.get("total")==null){
                    SharedPreferences.Editor editor = ele6.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(ele6.getAll().equals(ele6_)==false){
                    SharedPreferences.Editor editor = ele6.edit();
                    editor.putBoolean("total",(boolean)ele6_.get("total"));
                    editor.commit();
                }
                if(bike7_.get("total")==null){
                    SharedPreferences.Editor editor = bike7.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(bike7.getAll().equals(bike7_)==false){
                    SharedPreferences.Editor editor = bike7.edit();
                    editor.putBoolean("total",(boolean)bike7_.get("total"));
                    editor.commit();
                }

                if(slope8_.get("total")==null){
                    SharedPreferences.Editor editor = slope8.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(slope8.getAll().equals(slope8_)==false){
                    SharedPreferences.Editor editor = slope8.edit();
                    editor.putBoolean("total",(boolean)slope8_.get("total"));
                    editor.commit();
                }
                if(danger9_.get("total")==null){
                    SharedPreferences.Editor editor = danger9.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(danger9.getAll().equals(danger9_)==false){
                    SharedPreferences.Editor editor = danger9.edit();
                    editor.putBoolean("total",(boolean)danger9_.get("total"));
                    editor.commit();
                }
                if(lift10_.get("total")==null){
                    SharedPreferences.Editor editor = lift10.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                }else if(lift10.getAll().equals(lift10_)==false){
                    SharedPreferences.Editor editor = lift10.edit();
                    editor.putBoolean("total",(boolean)lift10_.get("total"));
                    editor.commit();
                }



            }
        });
    }


    public void onBackPressed(){
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
        super.onBackPressed();
        if(total1_.get("total")==null){
            SharedPreferences.Editor editor = total1.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(total1.getAll().equals(total1_)==false){
            SharedPreferences.Editor editor = total1.edit();
            editor.putBoolean("total",(boolean)total1_.get("total"));
            editor.commit();
        }if(hos2_.get("total")==null){
            SharedPreferences.Editor editor = hos2.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(hos2.getAll().equals(hos2_)==false){
            SharedPreferences.Editor editor = hos2.edit();
            editor.putBoolean("total",(boolean)hos2_.get("total"));
            editor.commit();
        }

        if(fac3_.get("total")==null) {
            SharedPreferences.Editor editor = fac3.edit();
            editor.putBoolean("total", true);
            editor.commit();
        }else if(fac3.getAll().equals(fac3_)==false){
            SharedPreferences.Editor editor = fac3.edit();
            editor.putBoolean("total",(boolean)fac3_.get("total"));
            editor.commit();
        }


        if(charge4_.get("total")==null){
            SharedPreferences.Editor editor = charge4.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(charge4.getAll().equals(charge4_)==false){
            SharedPreferences.Editor editor = charge4.edit();
            editor.putBoolean("total",(boolean)charge4_.get("total"));
            editor.commit();
        }
        if(wheel5_.get("total")==null){
            SharedPreferences.Editor editor = wheel5.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(wheel5.getAll().equals(wheel5_)==false){
            SharedPreferences.Editor editor = wheel5.edit();
            editor.putBoolean("total",(boolean)wheel5_.get("total"));
            editor.commit();
        }
        if(ele6_.get("total")==null){
            SharedPreferences.Editor editor = ele6.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(ele6.getAll().equals(ele6_)==false){
            SharedPreferences.Editor editor = ele6.edit();
            editor.putBoolean("total",(boolean)ele6_.get("total"));
            editor.commit();
        }
        if(bike7_.get("total")==null){
            SharedPreferences.Editor editor = bike7.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(bike7.getAll().equals(bike7_)==false){
            SharedPreferences.Editor editor = bike7.edit();
            editor.putBoolean("total",(boolean)bike7_.get("total"));
            editor.commit();
        }

        if(slope8_.get("total")==null){
            SharedPreferences.Editor editor = slope8.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(slope8.getAll().equals(slope8_)==false){
            SharedPreferences.Editor editor = slope8.edit();
            editor.putBoolean("total",(boolean)slope8_.get("total"));
            editor.commit();
        }
        if(danger9_.get("total")==null){
            SharedPreferences.Editor editor = danger9.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(danger9.getAll().equals(danger9_)==false){
            SharedPreferences.Editor editor = danger9.edit();
            editor.putBoolean("total",(boolean)danger9_.get("total"));
            editor.commit();
        }
        if(lift10_.get("total")==null){
            SharedPreferences.Editor editor = lift10.edit();
            editor.putBoolean("total",true);
            editor.commit();
        }else if(lift10.getAll().equals(lift10_)==false){
            SharedPreferences.Editor editor = danger9.edit();
            editor.putBoolean("total",(boolean)lift10_.get("total"));
            editor.commit();
        }
    }


    private void Filter_save() {
        SharedPreferences total1 = getSharedPreferences("total", Activity.MODE_PRIVATE);
        MainActivity firstActivity = (MainActivity) MainActivity.firstActivity;
        filter_button = findViewById(R.id.button_filter);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                firstActivity.finish(); //Code to close the previous page main activity
                startActivity(intent);
                finish();
            }

        });
    }
}