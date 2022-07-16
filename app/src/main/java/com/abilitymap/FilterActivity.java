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
    LabeledSwitch labeledSwitch_total;
    Button filter_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        overridePendingTransition(R.anim.horizon_enter, R.anim.none)
        super.onCreate(savedInstanceState);
        SharedPreferences save = getSharedPreferences("total", Activity.MODE_PRIVATE);
        setContentView(R.layout.activity_filter);
        Filter_close();
        Filter_save();
        labeledSwitch_total = findViewById(R.id.total_toggle);
        filter_button = findViewById(R.id.button_filter);
        if ((save==null) || (save.getBoolean("total",true))) {
            labeledSwitch_total.setOn(true);
        }


        labeledSwitch_total.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (labeledSwitch_total.isOn()) {
                    Toast.makeText(FilterActivity.this, "확인", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = save.edit();
                    editor.putBoolean("total",true);
                    editor.commit();
                    System.out.println("5555"+save.getAll());
                }else{
                    SharedPreferences.Editor editor = save.edit();
                    editor.putBoolean("total",false);
                    editor.commit();
                    System.out.println("5555"+save.getAll());
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