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
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.naver.maps.map.overlay.Overlay;

public class Report_detail extends AppCompatActivity {
    private TextView reportLocationView;
    private TextView reportDateView;
    private TextView contentLimitView;
    private TextView nicknameLimitView;
    private EditText contentView;
    private EditText nicknameView;

    String reportLocation;
    String sReportDate;     //서버로 넘겨줄 제보시간
    String cReportDate;     //클라이언트에 띄울 제보시간
    Double latitude;
    Double longitude;

    Bitmap bitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        overridePendingTransition(R.anim.horizon_enter, R.anim.none)
        super.onCreate(savedInstanceState);


        setContentView(R.layout.report_detail);
        View report_cancel_btn = findViewById(R.id.report_cancel_btn);
        View report_submit_btn = findViewById(R.id.submit_button_background);
        reportLocationView = findViewById(R.id.report_location_tv);
        reportDateView = findViewById(R.id.report_date_tv);
        contentLimitView = findViewById(R.id.report_content_limit_tv);
        nicknameLimitView = findViewById(R.id.report_nickname_limit_tv);
        contentView = (EditText) findViewById(R.id.report_content_ev);
        nicknameView = (EditText) findViewById(R.id.report_nickname_ev);


        Intent reportData = getIntent();
        reportLocation = reportData.getStringExtra("address");
        sReportDate = reportData.getStringExtra("sReportDate");
        cReportDate = reportData.getStringExtra("cReportDate");
        latitude = reportData.getDoubleExtra("reportLat", 0.0);
        longitude = reportData.getDoubleExtra("reportLng", 0.0);


        reportLocationView.setText("위치 : " + reportLocation);
        reportDateView.setText("신고일자 : " + cReportDate);

/*
        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0,arr.length);
        ImageView reported = (ImageView)findViewById(R.id.report_pic_iv);
        reported.setImageBitmap(image);
*/

        imageView = (ImageView) findViewById(R.id.report_pic_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultPicture.launch(intent);
            }
        });

        report_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        report_submit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //

                System.out.println("신고 내용 :"+contentView.getText());


                System.out.println("닉네임 내용 :"+nicknameView.getText());


            }
        });

        contentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                contentLimitView.setText(contentView.getText().length()+"/30 자");
            }
        });

        nicknameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nicknameLimitView.setText(nicknameView.getText().length()+"/6 자");
            }
        });



    }
    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //데이터 널 아니면 ㄱㄱ
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){

                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(bitmap);

                    }
                }
            }
    );
}