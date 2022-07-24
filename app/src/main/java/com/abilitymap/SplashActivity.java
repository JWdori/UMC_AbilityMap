package com.abilitymap;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_logo);//1초 후 main activity 로 넘어감

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences spfOnBoarding = getSharedPreferences("onBoarding", MODE_PRIVATE);
                Boolean isFirst = spfOnBoarding.getBoolean("isFirst", true);
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
                SharedPreferences.Editor editor = total1.edit();
                editor.putBoolean("total",false);
                editor.commit();
                SharedPreferences.Editor editor2 = hos2.edit();
                editor2.putBoolean("total",false);
                editor2.commit();
                SharedPreferences.Editor editor3 = fac3.edit();
                editor3.putBoolean("total",false);
                editor3.commit();
                SharedPreferences.Editor editor4 = charge4.edit();
                editor4.putBoolean("total",false);
                editor4.commit();
                SharedPreferences.Editor editor5 = wheel5.edit();
                editor5.putBoolean("total",false);
                editor5.commit();
                SharedPreferences.Editor editor6 = ele6.edit();
                editor6.putBoolean("total",false);
                editor6.commit();
                SharedPreferences.Editor editor7 = bike7.edit();
                editor7.putBoolean("total",false);
                editor7.commit();
                SharedPreferences.Editor editor8 = slope8.edit();
                editor8.putBoolean("total",false);
                editor8.commit();
                SharedPreferences.Editor editor9 = danger9.edit();
                editor9.putBoolean("total",false);
                editor9.commit();
                SharedPreferences.Editor editor10 = lift10.edit();
                editor10.putBoolean("total",false);
                editor10.commit();
                if (isFirst){   //최초 실행일 시 onBoarding 화면 pop up
                    //onBoarding 화면으로 이동
                    startActivity(new Intent(getApplicationContext(), OnboardingActivity.class));
                    finish();
                }
                else{
                    //new Intent(현재 context, 이동할 activity)
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);    //intent 에 명시된 액티비티로 이동
                    finish();    //현재 액티비티 종료
                }
            }
        }, 1000);
    }

}