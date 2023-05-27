package com.projects4.aqm.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projects4.aqm.MainActivity;
import com.projects4.aqm.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

        TextView login;
        EditText name_inp, org_inp, email_inp, password_inp;
        Button signup;
        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            name_inp = findViewById(R.id.name);
            org_inp = findViewById(R.id.org);
            email_inp = findViewById(R.id.email);
            password_inp = findViewById(R.id.password);
            signup = findViewById(R.id.signup_button);
            login = findViewById(R.id.signin_button);
            signup.setOnClickListener(this);
            login.setOnClickListener(this);
            mAuth = FirebaseAuth.getInstance();
        }

        @Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }

        private void updateUI(FirebaseUser currentUser) {
            if(currentUser != null){
                Toast.makeText(this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.signup_button){
                String name = name_inp.getText().toString(),
                        email = email_inp.getText().toString(),
                        password = password_inp.getText().toString();
                if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this, "Fill the required fields!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Ok", "createUserWithEmail:success");
                                    Toast.makeText(this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Error", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            });
                }
            }
            else if(view.getId() == R.id.signin_button){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }

        // FirebaseAuth.getInstance().signOut();
    }