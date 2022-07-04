package com.abilitymap;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
                //new Intent(현재 context, 이동할 activity)
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);    //intent 에 명시된 액티비티로 이동

                finish();    //현재 액티비티 종료
            }
        }, 1000);
    }

}