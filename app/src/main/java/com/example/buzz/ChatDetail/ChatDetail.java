package com.example.buzz.ChatDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzz.Authentication.Mobile;
import com.example.buzz.MainActivity;
import com.example.buzz.Model.ChatModel;
import com.example.buzz.R;
import com.example.buzz.Recyclerview.ChatAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetail extends AppCompatActivity {

    FirebaseDatabase database ;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        ImageView imageView = findViewById(R.id.chatprofilePic);
        TextView textView = findViewById(R.id.Chatusername);
        ImageView back = findViewById(R.id.back);
        TextView sendMessage = findViewById(R.id.message);
        ImageView send = findViewById(R.id.send);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView call = findViewById(R.id.call1);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ChatDetail.this, Mobile.class);
                startActivity(it);
            }
        });


database = FirebaseDatabase.getInstance();
auth = FirebaseAuth.getInstance();
        Intent it = getIntent();


       String SenderId= auth.getUid();


        String recieverId = it.getStringExtra("userId");

        String recieveUsername = it.getStringExtra("username");
        String recieveProfilePic = it.getStringExtra("profilePic");
         final  String SenderRoom = SenderId+recieverId;
         final String ReciverRoom = recieverId+SenderId;
        ArrayList<ChatModel> list2 = new ArrayList<>();
        RecyclerView recyclerView1 = findViewById(R.id.chatRecycler1);
        ChatAdapter chatAdapter = new ChatAdapter(list2 , this);
        recyclerView1.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();



        database.getReference().child("chats").child(SenderRoom).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    list2.add(chatModel);
                    sendMessage.setText("");


                }

                StringBuilder sb = new StringBuilder();
                for (ChatModel chat : list2) {
                    sb.append("Message: ").append(chat.getSenderUId()).append("\n");  // Assuming getMessage() is the method to fetch the message text
                }

                // String ko Toast me show karte hain
                Toast.makeText(ChatDetail.this, sb.toString(), Toast.LENGTH_LONG).show();
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        Picasso.get().load(recieveProfilePic).placeholder(R.drawable.avatars).into(imageView);
        textView.setText(recieveUsername);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ChatDetail.this, MainActivity.class);
                startActivity(it);
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String text = sendMessage.getText().toString();


                ChatModel chatModel = new ChatModel(SenderId,text , new Date().getTime());

                database.getReference().child("chats").child(SenderRoom).push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        database.getReference().child("chats").child(ReciverRoom).push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused)
                            {

                            }
                        });


                    }
                });

            }
        });







    }
}