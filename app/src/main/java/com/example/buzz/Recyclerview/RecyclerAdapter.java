package com.example.buzz.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzz.ChatDetail.ChatDetail;
import com.example.buzz.Model.users;
import com.example.buzz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<users> list;
    private Context context;

    public RecyclerAdapter(List<users> list, Context context)
    {
        this.list = list;
        this.context = context.getApplicationContext(); // Use application context
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_layout_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        users user = list.get(position);

        // Load profile picture with placeholder
        Picasso.get()
                .load(user.getProfilePic())
                .placeholder(R.drawable.avatars) // Add your placeholder image
                .error(R.drawable.baseline_error_24) // Add your error image
                .into(holder.profilePic);

        holder.Username.setText(user.getUsername());


        holder.profilePic.setOnClickListener(view ->
        {
            Intent it = new Intent(context, ChatDetail.class);
            it.putExtra("userId", user.getUserId());
            it.putExtra("profilePic", user.getProfilePic());
            it.putExtra("username", user.getUsername());
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        });
        holder.Username.setOnClickListener(view ->
        {
            Intent it = new Intent(context, ChatDetail.class);
            it.putExtra("userId", user.getUserId());
            it.putExtra("profilePic", user.getProfilePic());
            it.putExtra("username", user.getUsername());
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        });
        holder.linearLayout.setOnClickListener(view ->
        {
            Intent it = new Intent(context, ChatDetail.class);
            it.putExtra("userId", user.getUserId());
            it.putExtra("profilePic", user.getProfilePic());
            it.putExtra("username", user.getUsername());
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profilePic;
        TextView Username;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            profilePic = itemView.findViewById(R.id.chatprofilePic);
            Username = itemView.findViewById(R.id.username);
            linearLayout = itemView.findViewById(R.id.linear2222);
        }
    }
}
