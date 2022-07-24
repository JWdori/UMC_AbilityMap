package com.abilitymap;


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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.nio.charset.StandardCharsets;

public class DangerDetailFragment extends Fragment {
    MainActivity activity;
    private TextView dangerContentView;
    private TextView dangerNicknameView;
    private TextView dangerDateView;
    private ImageView dangerImageView;
//    private ImageView closeImageView;
    private TextView changeRequestView;

    String reportContent = "default";
    String cReportDate = "default";     //클라이언트에 띄울 제보시간
    String nickName = "default";
    String reportImage = "default";
    Integer wrong = 0;

    public DangerDetailFragment(){
    }

    public DangerDetailFragment(String tag, String reportContent,
                                String cReportDate, String nickName){
        this.reportContent = reportContent;
        this.cReportDate = cReportDate;
        this.nickName = nickName;
    }
    public DangerDetailFragment(String tag, String reportContent,
                                String cReportDate, String nickName, String reportImage){
        this.reportContent = reportContent;
        this.cReportDate = cReportDate;
        this.nickName = nickName;
        this.reportImage = reportImage;
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity) getActivity();

    }

    public void onDetach(){
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.danger_detail, container, false);
        dangerContentView = rootView.findViewById(R.id.danger_content);
        dangerNicknameView = rootView.findViewById(R.id.danger_nickname);
        dangerDateView = rootView.findViewById(R.id.danger_date);

        dangerImageView = rootView.findViewById(R.id.danger_image);
//        closeImageView = rootView.findViewById(R.id.danger_close);
        changeRequestView = rootView.findViewById(R.id.danger_change_request);

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

                        locationErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                        otherButton.setSelected(false);


                    }
                });

                locationErrButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrong = 2;
                        locationErrButton.setSelected(!locationErrButton.isSelected());

                        contentErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                        otherButton.setSelected(false);

                    }
                });

                notdangerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrong = 3;
                        notdangerButton.setSelected(!notdangerButton.isSelected());

                        contentErrButton.setSelected(false);
                        locationErrButton.setSelected(false);
                        otherButton.setSelected(false);

                    }
                });


                otherButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrong = 4;
                        otherButton.setSelected(!otherButton.isSelected());

                        contentErrButton.setSelected(false);
                        locationErrButton.setSelected(false);
                        notdangerButton.setSelected(false);
                    }
                });
                TextView noButton = alertDialog.findViewById(R.id.change_no_dialog);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                TextView yesButton = alertDialog.findViewById(R.id.change_yes_dialog);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //api로 전송코드
                        alertDialog.dismiss();

                        if(contentErrButton.isSelected()){
                            wrong = 1;
                        } else if(locationErrButton.isSelected()){
                            wrong = 2;
                        } else if(notdangerButton.isSelected()){
                            wrong = 3;
                        } else if(otherButton.isSelected()){
                            wrong = 4;
                        }

                        System.out.println("wrong report code : "+wrong);

                    }
                });

            }
        });




        return rootView;
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


}
