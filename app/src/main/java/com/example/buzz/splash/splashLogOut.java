package com.example.buzz.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.buzz.Authentication.SignIn;
import com.example.buzz.Authentication.SignUp;
import com.example.buzz.R;

public class splashLogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_log_out);

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent it = new Intent(splashLogOut.this, SignIn.class);
            }
        });
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent it = new Intent(splashLogOut.this, SignIn.class);
               startActivity(it);
               finish();
           }
       },2000);


    }

}