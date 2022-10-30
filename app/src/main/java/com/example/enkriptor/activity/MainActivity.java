package com.example.enkriptor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.enkriptor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btnLogout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout=findViewById(R.id.btnlogout);

        db=FirebaseFirestore.getInstance();

        mAuth=FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this,"Logout Successful",Toast.LENGTH_SHORT).show();

            }
        });

    }
}