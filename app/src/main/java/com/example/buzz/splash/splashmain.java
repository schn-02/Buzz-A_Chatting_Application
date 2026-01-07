package com.example.buzz.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buzz.Authentication.SignUp;
import com.example.buzz.MainActivity;
import com.example.buzz.R;
import com.google.firebase.auth.FirebaseAuth;

public class splashmain extends AppCompatActivity
{
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run()
                      {
                          if (auth.getCurrentUser()!=null)
                          {
                              Intent it = new Intent(splashmain.this, MainActivity.class);
                              startActivity(it);
                              finish();
                          }
                          else
                          {

                              Intent it = new Intent(splashmain.this, SignUp.class);
                              startActivity(it);
                              finish();
                          }
                      }
                  },1000);


    }
}