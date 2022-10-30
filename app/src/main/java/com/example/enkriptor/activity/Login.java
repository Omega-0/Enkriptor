package com.example.enkriptor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enkriptor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class Login extends AppCompatActivity {

    EditText metemailadd,metPassword;
    Button mbtLogin;
    TextView mtxtRegister;

    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        metemailadd=findViewById(R.id.etemailadd);
        metPassword=findViewById(R.id.etPassword);
        mbtLogin=findViewById(R.id.btLogin);
        mtxtRegister=findViewById(R.id.txtRegister);

        fAuth=FirebaseAuth.getInstance();


        mtxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        mbtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=metemailadd.getText().toString().trim();
                String password=metPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    metemailadd.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    metemailadd.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    metemailadd.setError("Password must be >= 6 characters");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Loggin successfull",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        else{
                            Toast.makeText(getApplicationContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}