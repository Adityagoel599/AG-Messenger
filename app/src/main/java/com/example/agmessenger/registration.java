package com.example.agmessenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {
TextView loginbut;
EditText rgusername,   rgemail, rgrepass, rgpass;
Button signup;
CircleImageView profileimg;
FirebaseAuth auth;
Uri imageURI;
String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
String imageuri;
FirebaseDatabase database;
FirebaseStorage storage;
ProgressDialog progressDialog;
  //FirebaseApp.initializeApp(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        //database=FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        loginbut=findViewById(R.id.loginbut);
        rgusername=findViewById(R.id.rgUsername);
        rgemail=findViewById(R.id.rgEmail);
        rgpass=findViewById(R.id.rgPassword);
        rgrepass=findViewById(R.id.rgConfirmPassword);
        profileimg=findViewById(R.id.profileimg);
        signup=findViewById(R.id.signup);
        loginbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              Intent intent= new Intent(registration.this,login.class);
              startActivity(intent);
              finish();
            }
        });

   signup.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String namee=rgusername.getText().toString();
           String emaill=rgemail.getText().toString();
           String passs=rgpass.getText().toString();
           String repasss=rgrepass.getText().toString();
           String status="Hey I am using this application";
           if(TextUtils.isEmpty(namee)|| TextUtils.isEmpty(emaill)||TextUtils.isEmpty(passs)||TextUtils.isEmpty(repasss)){
               Toast.makeText(registration.this,"Please Enter the Valid Information",Toast.LENGTH_SHORT).show();
           }else if(!emaill.matches(emailpattern)){
               rgemail.setError("Please Enter the Valid Email");
           }
           else if(passs.length()<8){
              rgpass.setError("Please Enter the Valid Password");
           }
           else if(!passs.equals(repasss)){
               rgpass.setError("Password do not match");
           }
       else{
           auth.createUserWithEmailAndPassword(emaill,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       String id=task.getResult().getUser().getUid();
                       DatabaseReference reference=database.getReference().child("user").child(id);
                       StorageReference storageReference=storage.getReference().child("upload").child(id);
                   if(imageURI!=null){
                       storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                               if(task.isSuccessful()){
                                   storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           imageuri=uri.toString();
                                           Users users = new Users(id, namee, emaill, passs,  imageuri, status);
                                           reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    progressDialog.show();
                                                    Intent intent = new Intent(registration.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else{
                                                    Toast.makeText(registration.this,"Error in creating the user ",Toast.LENGTH_SHORT).show();
                                                }
                                               }
                                           });
                                       }
                                   });
                               }
                           }
                       });
                   }else {
                       String status="Hey I am using this app";
                       imageuri="https://firebasestorage.googleapis.com/v0/b/ag-messenger-6d200.appspot.com/o/man.png?alt=media&token=93e565a8-e768-4de7-a834-431e25d88c60";
                       Users users = new Users(id, namee, emaill, passs, imageuri, status);
                       reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   progressDialog.show();
                                   Intent intent = new Intent(registration.this, MainActivity.class);
                                   startActivity(intent);
                                   finish();
                               }else{
                                   Toast.makeText(registration.this,"Error in creating the user ",Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }
                   }else{
                       Toast.makeText(registration.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                   }
               }
           });
           }
       }

   });
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==10){
            if(data!=null){
            imageURI=data.getData();
            profileimg.setImageURI(imageURI);
            }
        }
    }
}