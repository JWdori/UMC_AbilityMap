package com.abilitymap;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class Report_detail extends AppCompatActivity {

    View report_cancel_btn = findViewById(R.id.report_cancel_btn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        overridePendingTransition(R.anim.horizon_enter, R.anim.none)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0,arr.length);
        ImageView reported = (ImageView)findViewById(R.id.report_pic_iv);
        reported.setImageBitmap(image);


        report_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //여기부터는 GPS 활성화를 위한 메소드들

}