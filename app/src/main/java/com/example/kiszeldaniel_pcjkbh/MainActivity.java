package com.example.kiszeldaniel_pcjkbh;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    private final int[] images = {R.drawable.bb1, R.drawable.bb2, R.drawable.bb3, R.drawable.bb4, R.drawable.bb5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth.getInstance().signOut();

        ViewPager2 mozgoKep = findViewById(R.id.mozgoKep);

        MozgoKep adapter = new MozgoKep(new int[]{R.drawable.bb1, R.drawable.bb2, R.drawable.bb3, R.drawable.bb4, R.drawable.bb5});
        mozgoKep.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mozgoKep.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };

        handler.postDelayed(runnable, 3000);

        mozgoKep.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    public void  logIn(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, IndexActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void register(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    public void guest(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, IndexActivity.class);
            startActivity(intent);
            finish();
        }else {
            mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(MainActivity.this, "Kellemes időtöltést az oldalon.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Hiba: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }



}
