package com.example.agmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.av.avmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp; // Add this import
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView recycle;
    UserAdpter adpter;
    ImageView logout;
    FirebaseDatabase database;
    ArrayList<Users> userdata;
    ImageView cumbut,setbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        cumbut = findViewById(R.id.camBut);
        setbut = findViewById(R.id.settingBut);
        database=FirebaseDatabase.getInstance();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        DatabaseReference reference= database.getReference().child("user");
        userdata= new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot snap:snapshot.getChildren()){
                  Users us=snap.getValue(Users.class);
                  userdata.add(us);
              }
            adpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logout=findViewById(R.id.logoutbut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(MainActivity.this, R.style.dialoge);
                dialog.setContentView(R.layout.dialog);
                Button no ,yes;
                yes= dialog.findViewById(R.id.yesbnt);
                no= dialog.findViewById(R.id.nobnt);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(MainActivity.this,login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    auth = FirebaseAuth.getInstance();
    recycle=findViewById(R.id.recycleview);
    recycle.setLayoutManager(new LinearLayoutManager(this));
    adpter= new UserAdpter(MainActivity.this, userdata);
    recycle.setAdapter(adpter);
        setbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);

            }
        });

        cumbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }
        });


    if(auth.getCurrentUser()==null){
        Intent intent= new Intent(MainActivity.this,login.class);
    }
    }
}