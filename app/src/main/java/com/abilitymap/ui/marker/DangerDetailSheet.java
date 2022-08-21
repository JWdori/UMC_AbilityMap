package com.abilitymap.ui.marker;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abilitymap.R;
import com.abilitymap.ui.main.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DangerDetailSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private TextView dangerContentView;
    private TextView dangerNicknameView;
    private TextView dangerDateView;
    private ImageView dangerImageView;
    private TextView changeRequestView;

    String reportContent = "default";
    String cReportDate = "default";     //클라이언트에 띄울 제보시간
    String nickName = "default";
    String reportImage = "default";
    String reportIdx = "default";
    Integer wrong = 0;


    public DangerDetailSheet(){
    }

    public DangerDetailSheet(String tag, String reportContent,
                                String cReportDate, String nickName){
        this.reportContent = reportContent;
        this.cReportDate = cReportDate;
        this.nickName = nickName;
    }
    public DangerDetailSheet(String tag, String reportContent,
                             String cReportDate, String nickName,
                             String reportImage, String reportIdx){
        this.reportContent = reportContent;
        this.cReportDate = cReportDate;
        this.nickName = nickName;
        this.reportImage = reportImage;
        this.reportIdx = reportIdx;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.danger_detail, container, false);

        dangerContentView = view.findViewById(R.id.danger_content);
        dangerNicknameView = view.findViewById(R.id.danger_nickname);
        dangerDateView = view.findViewById(R.id.danger_date);

        dangerImageView = view.findViewById(R.id.danger_image);
        changeRequestView = view.findViewById(R.id.danger_change_request);

        setDangerContentView(reportContent);
        setDangerDateView(cReportDate);
        setDangerNicknameView(nickName);
        setDangerImageView(reportImage);


        changeRequestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.change_submit_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
                InsetDrawable inset = new InsetDrawable(back, 24);
                alertDialog.getWindow().setBackgroundDrawable(inset);
                alertDialog.setCanceledOnTouchOutside(true);//없어지지 않도록 설정
                alertDialog.show();



                TextView contentErrButton = alertDialog.findViewById(R.id.content_error);
                TextView locationErrButton = alertDialog.findViewById(R.id.location_error);
                TextView notdangerButton = alertDialog.findViewById(R.id.notdanger_error);
                TextView otherButton = alertDialog.findViewById(R.id.other_);

                contentErrButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contentErrButton.setSelected(!contentErrButton.isSelected());

                        if(contentErrButton.isSelected()){
                            wrong = 1;
                        }
                        else wrong = 0;

                        locationErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                        otherButton.setSelected(false);


                    }
                });

                locationErrButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        locationErrButton.setSelected(!locationErrButton.isSelected());

                        if(locationErrButton.isSelected()){
                            wrong = 1;
                        } else wrong = 0;

                        contentErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                        otherButton.setSelected(false);

                    }
                });

                notdangerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notdangerButton.setSelected(!notdangerButton.isSelected());

                        if(locationErrButton.isSelected()){
                            wrong = 3;
                        } else wrong = 0;

                        contentErrButton.setSelected(false);
                        locationErrButton.setSelected(false);
                        otherButton.setSelected(false);

                    }
                });

                otherButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        otherButton.setSelected(!otherButton.isSelected());

                        if(locationErrButton.isSelected()){
                            wrong = 4;
                        } else wrong = 0;

                        contentErrButton.setSelected(false);
                        locationErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                    }
                });
                TextView noButton = alertDialog.findViewById(R.id.change_no_dialog);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrong = 0;
                        alertDialog.dismiss();
                    }
                });
                TextView yesButton = alertDialog.findViewById(R.id.change_yes_dialog);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://3.35.237.29")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                WrongReportService wrongReportService = retrofit.create(WrongReportService.class);



                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(contentErrButton.isSelected()){
                            wrong = 1;
                        } else if(locationErrButton.isSelected()){
                            wrong = 2;
                        } else if(notdangerButton.isSelected()){
                            wrong = 3;
                        } else if(otherButton.isSelected()){
                            wrong = 4;
                        }

                        //String reportIdx = "66";

                        if(wrong!=0) {
                            alertDialog.dismiss();
                            wrongReportService.patchReport(reportIdx, wrong)
                                    .enqueue(new Callback<ReportPatchResponse>() {
                                        @Override
                                        public void onResponse(Call<ReportPatchResponse> call, Response<ReportPatchResponse> response) {
                                            ReportPatchResponse resp = response.body();
                                            // patch success
                                            changeRequestView.setClickable(false);
                                            changeRequestView.setOnClickListener(new View.OnClickListener() {   //수정요청을 이미 한번 보낸 마커에서 수정요청버튼을 한번 더 누를때
                                                                                     @Override
                                                                                     public void onClick(View v) {
                                                                                         Toast.makeText(getContext(), "이미 수정 요청이 보내진 제보입니다.", Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                 }
                                            );

                                        }

                                        @Override
                                        public void onFailure(Call<ReportPatchResponse> call, Throwable t) {
                                            //patch error
                                        }
                                    });

                            System.out.println("wrong report code : " + wrong);
                        }
                        else{
                            Toast.makeText(getContext(), "항목을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




        return view;
    }


    public void setDangerContentView(String reportContent){
        dangerContentView.setText(reportContent);
    }
    public void setDangerDateView(String cReportDate){
        dangerDateView.setText(cReportDate);
    }
    public void setDangerNicknameView(String nickName){
        dangerNicknameView.setText(nickName);
    }
    public void setDangerImageView(String reportImage){
        byte[] decodedString = Base64.decode(reportImage.getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // \n, \ 제거하는 코드 필요
        dangerImageView.setImageBitmap(decodedByte);
    }


    @Override
    public void onClick(View v) {

    }
}
