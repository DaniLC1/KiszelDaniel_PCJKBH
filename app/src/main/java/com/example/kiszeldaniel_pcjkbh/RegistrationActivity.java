package com.example.kiszeldaniel_pcjkbh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    public void register(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    public void  logIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
