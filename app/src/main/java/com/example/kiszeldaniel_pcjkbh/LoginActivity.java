package com.example.kiszeldaniel_pcjkbh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés Google fiókjával!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Hiba a bejelentkezéskor: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (ApiException e) {
            Toast.makeText(LoginActivity.this, "Bejelentkezés hiba: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void logIn(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Kérlek, add meg az emailt és a jelszót!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Hiba: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    
    public void google(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        Intent googleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, RC_SIGN_IN);
    }

    public void register(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    
}