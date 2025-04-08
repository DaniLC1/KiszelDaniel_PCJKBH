package com.example.kiszeldaniel_pcjkbh;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class IndexActivity extends AppCompatActivity {
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(IndexActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(IndexActivity.this, "Autentikált felhasználó", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_bar1:
                Toast.makeText(this, "Keresés kiválasztva!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.shopping_cart:
                Toast.makeText(this, "Kosár megnyitva!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.log_out:
                Toast.makeText(this, "Kijelentkezés...", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
