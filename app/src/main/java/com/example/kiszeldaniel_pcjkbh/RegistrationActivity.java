package com.example.kiszeldaniel_pcjkbh;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private EditText firstnameInput;
    private EditText lastnameInput;
    private EditText phoneInput;
    private EditText zipCodeInput;
    private EditText cityInput;
    private EditText streetAddressInput;
    private CheckBox termsCheckbox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirmInput = findViewById(R.id.passwordConfirmInput);
        firstnameInput = findViewById(R.id.firstnameInput);
        lastnameInput = findViewById(R.id.lastnameInput);
        phoneInput = findViewById(R.id.phoneInput);
        zipCodeInput = findViewById(R.id.zipCodeInput);
        cityInput = findViewById(R.id.cityInput);
        streetAddressInput = findViewById(R.id.streetAddressInput);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        mAuth= FirebaseAuth.getInstance();
    }
    public void register(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String passwordConfirm = passwordConfirmInput.getText().toString().trim();
        String firstname = firstnameInput.getText().toString().trim();
        String lastname = lastnameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String zip = zipCodeInput.getText().toString().trim();
        String city = cityInput.getText().toString().trim();
        String address = streetAddressInput.getText().toString().trim();
        boolean termsAccepted = termsCheckbox.isChecked();

        if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || firstname.isEmpty() ||
                lastname.isEmpty() || phone.isEmpty() || zip.isEmpty() || city.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Kérlek tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordConfirm)) {
            Toast.makeText(this, "A jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!termsAccepted) {
            Toast.makeText(this, "El kell fogadnod az Általános Szerződési Feltételeket!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistrationActivity.this, IndexActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Hiba: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void  logIn(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
