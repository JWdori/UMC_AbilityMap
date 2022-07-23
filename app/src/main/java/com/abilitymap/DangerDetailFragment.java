package com.abilitymap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.nio.charset.StandardCharsets;

public class DangerDetailFragment extends Fragment {
    MainActivity activity;
    private TextView dangerContentView;
    private TextView dangerNicknameView;
    private TextView dangerDateView;
    private ImageView dangerImageView;
    private ImageView closeImageView;
    private TextView changeRequestView;

    String reportContent = "default";
    String cReportDate = "default";     //클라이언트에 띄울 제보시간
    String nickName = "default";
    String reportImage = "default";

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
        closeImageView = rootView.findViewById(R.id.danger_close);
        changeRequestView = rootView.findViewById(R.id.danger_change_request);

        setDangerContentView(reportContent);
        setDangerDateView(cReportDate);
        setDangerNicknameView(nickName);




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
