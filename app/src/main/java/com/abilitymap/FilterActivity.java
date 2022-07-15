package com.abilitymap;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.naver.maps.map.overlay.Overlay;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        overridePendingTransition(R.anim.horizon_enter, R.anim.none)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Filter_close();


    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void Filter_close() {
        ImageButton filterclose = findViewById(R.id.filter_close);
        filterclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }
}