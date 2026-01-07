package com.example.buzz.Recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzz.Model.ChatModel;
import com.example.buzz.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends  RecyclerView.Adapter
{
    ArrayList<ChatModel> list;
    Context context;
    int Sender_Type= 1;
    int Reciver_Type = 0;

    public ChatAdapter(ArrayList<ChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == Sender_Type)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);

        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver,parent,false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ChatModel chatModel = list.get(position);
        if (holder.getClass()== SenderViewHolder.class)

        {
            Long timestamp = chatModel.getTimestamp();

            Date date = new Date(timestamp);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // Format: 12-hour format with AM/PM

            String formattedTime = sdf.format(date);
            ((SenderViewHolder)holder).message.setText(chatModel.getMessage());
            ((SenderViewHolder)holder).date.setText(formattedTime);
        }
        else
        {
            Long timestamp = chatModel.getTimestamp();

            Date date = new Date(timestamp);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // Format: 12-hour format with AM/PM

            String formattedTime = sdf.format(date);
            ((RecieverViewHolder)holder).message.setText(chatModel.getMessage());
            ((RecieverViewHolder)holder).date1.setText(formattedTime);
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        if (list.get(position).getSenderUId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return Sender_Type;
        }
        else {
            return Reciver_Type;
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class  SenderViewHolder extends RecyclerView.ViewHolder
    {
        TextView message  ;
        TextView date;

        public SenderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            message = itemView.findViewById(R.id.SenderMessage);
            date = itemView.findViewById(R.id.sender_message_Time);
        }
    }
    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {
        TextView message;
        TextView date1;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.ReciverrMessage);
            date1 = itemView.findViewById(R.id.reciver_message_TIme);
        }
    }
}
