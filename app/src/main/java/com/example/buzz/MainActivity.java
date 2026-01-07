package com.example.buzz;

import static com.facebook.appevents.AppEventsLogger.clearUserData;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzz.Authentication.SignIn;
import com.example.buzz.Authentication.SignUp;
import com.example.buzz.Model.ChatModel;
import com.example.buzz.Model.users;
import com.example.buzz.Recyclerview.ChatAdapter;
import com.example.buzz.Recyclerview.RecyclerAdapter;
import com.example.buzz.splash.splashLogOut;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.chatRecycler);
        ArrayList<users> list = new ArrayList<>();
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        Dialog dialog;


        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    users users = snapshot1.getValue(com.example.buzz.Model.users.class);
                    users.setUserId(snapshot1.getKey());
                    list.add(users);
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void showDialog()
    {
        TextView btn1, btn2, btn3;
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_alert_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn2 = dialog.findViewById(R.id.textView3);
        btn3 = dialog.findViewById(R.id.textView2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                dialog.dismiss();
            }
        });

dialog.show();




        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            // Sign out the user
            auth.signOut();
            clearUserData();

            // Start the splash screen
            Intent it1 = new Intent(MainActivity.this, splashLogOut.class);
            startActivity(it1);
            finish();


        }
        return super.onOptionsItemSelected(item);
    }


}