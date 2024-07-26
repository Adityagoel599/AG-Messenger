package com.example.agmessenger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {
    MainActivity mainActivity;
    ArrayList<Users> userdata;
    public UserAdpter(MainActivity mainActivity, ArrayList<Users> userdata) {
    this.mainActivity=mainActivity;
    this.userdata=userdata;
    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {
    Users users=userdata.get(position);
//    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUserId())){
//        holder.itemView.setVisibility(View.INVISIBLE);
//    }
    holder.namess.setText(users.username);
    holder.status.setText(users.status);
    //
    Picasso.get().load(users.profilepic).into(holder.userimg);
    //added later on
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //carefully added the names are used in chatwin
                Intent intent = new Intent(mainActivity,chatwin.class);
                intent.putExtra("namein",users.getUsername());
                intent.putExtra("imgg",users.getProfilepic());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userdata.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
       CircleImageView userimg;
       TextView namess, status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
        userimg=itemView.findViewById(R.id.userimg);
        namess=itemView.findViewById(R.id.namess);
        status=itemView.findViewById(R.id.status);
        }
    }
}
