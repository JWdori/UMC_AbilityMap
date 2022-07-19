package com.abilitymap;
import androidx.appcompat.app.AppCompatActivity;
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

                if (isFirst){   //최초 실행일 시 onBoarding 화면 pop up

                    //onBoarding 화면으로 이동
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    finish();

                    spfOnBoarding = getSharedPreferences("onBoarding", MODE_PRIVATE);
                    SharedPreferences.Editor editor = spfOnBoarding.edit();
                    editor.putBoolean("isFirst", false);
                    editor.apply();
                    editor.commit();    //이후 실행부터는 else문으로 가도록 isFirst == false로 지정
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