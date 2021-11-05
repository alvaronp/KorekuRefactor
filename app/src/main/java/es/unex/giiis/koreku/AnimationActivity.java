package es.unex.giiis.koreku;
/// HolaMultiverso
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        getSupportActionBar().hide();

        Thread td = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(AnimationActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };td.start();

    }
}