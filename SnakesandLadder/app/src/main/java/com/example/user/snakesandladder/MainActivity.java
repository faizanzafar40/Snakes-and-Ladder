package com.example.user.snakesandladder;

//Android Libraries
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

//Main home class
public class MainActivity extends AppCompatActivity {

        private static int SPLASH_TIME_OUT = 2000; //Time out variable and value

        @Override
        protected void onCreate(Bundle savedInstanceState) { //OnCreate method
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE); //No Title bar in application
            setContentView(R.layout.activity_main);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class); //Shift from MainActivity to HomeActivity
                    startActivity(homeIntent); //Start Activity of the shift
                    finish(); //End Activity
                }

            }, SPLASH_TIME_OUT); //Run for timer value
        } //end OnCreate
    } //End Class
