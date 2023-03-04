package com.example.basicfirebaseregandlogin;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
public class Login extends AppCompatActivity {
    final String TAG = "FIRESTORE";
    FirebaseFirestore db;
    TextInputEditText usernameInput, passwordInput;
    Button loginButton;
    TextView regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        regButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()){
                    searchUser(username, password);

                }
                else{
                    Toast.makeText(Login.this,"check empty fields", Toast.LENGTH_SHORT).show();

                }
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }

    public void searchUser(String username, String password)
    {

        db.collection("users")
                .document(username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String queriedPassword = documentSnapshot.getString("password");
                        String queriedUsername = documentSnapshot.getString("username");
                        Log.d("Firestore", "Data read from Firestore: " + documentSnapshot.getData());
                        Log.d("passwprd: ", password + queriedPassword);

                        if(queriedPassword != null && !(queriedPassword != password)) {
                            startActivity(new Intent(Login.this, MainActivity.class));

                        }
                        else {

                            Toast.makeText(Login.this," username or password incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firestore", "Error reading data from Firestore: " + e.getMessage());
                    }
                });
    }
}