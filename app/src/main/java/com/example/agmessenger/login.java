package com.example.agmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
Button button;
EditText email,password;
TextView signupp;
FirebaseAuth auth ;
String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
android.app.ProgressDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       prog=new ProgressDialog(this);
       prog.setMessage("Please Wait ...");
       prog.setCancelable(false);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        auth= FirebaseAuth.getInstance();
        button = findViewById(R.id.logbutton);
        email=findViewById(R.id.editTextLogEmail);
        password=findViewById(R.id.editTextLogPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String pass= password.getText().toString();
                if(Email.isEmpty()){
                    prog.dismiss();
                    prog.dismiss();
                    Toast.makeText(login.this,"Please enter the Email",Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    prog.dismiss();
                    Toast.makeText(login.this,"Please enter the Password",Toast.LENGTH_SHORT).show();
                }
                else if (!Email.matches(emailpattern)){
                    prog.dismiss();
                    email.setError("Give proper Email");
                }
                else if(pass.length()<8){
                    prog.dismiss();
                    password.setError("please enter at least 8 characters");
                    Toast.makeText(login.this,"Password is minimum of 8 characters",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                prog.show();
                                try{

                                    Intent intent= new Intent(login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                catch (Exception e){
                                    Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    //was stupidly added by me
        signupp=findViewById(R.id.textView7);
        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}