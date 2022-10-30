package com.example.enkriptor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enkriptor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public final String TAG="TAG";
    EditText metName,metEmail,metphone,metPass;
    Button mbtnregister;
    TextView maltlogin;
    ProgressBar progressBar;

    FirebaseAuth fAuth;
    String userId;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        metName=findViewById(R.id.etName);
        metEmail=findViewById(R.id.etEmail);
        metphone=findViewById(R.id.etphone);
        metPass=findViewById(R.id.etPass);
        mbtnregister=findViewById(R.id.btnregister);
        maltlogin=findViewById(R.id.altlogin);
        progressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        maltlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

        mbtnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=metEmail.getText().toString().trim();
                String password=metPass.getText().toString().trim();
                final String fullName=metName.getText().toString().trim();
                final String phone=metphone.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    metEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    metEmail.setError("Password is required");
                    return;
                }
                if (password.length()<6){
                    metEmail.setError("Password must be >=6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fuser=fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Register succesfull",Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d(TAG,"onFailure: Email not Sent"+e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(),"User created",Toast.LENGTH_SHORT).show();
                            userId=fAuth.getCurrentUser().getUid();
                            DocumentReference docrefer=fstore.collection("user").document(userId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fname",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            docrefer.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onsucess user profile is created"+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d(TAG,"onFailure: "+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else{
                            Toast.makeText(Register.this,"error"+task.getException(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



    }
}