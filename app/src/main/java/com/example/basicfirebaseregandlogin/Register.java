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
public class Register extends AppCompatActivity {
    final String TAG = "FIRESTORE";
    FirebaseFirestore db;
    TextInputEditText usernameInput, passwordInput, firstNameInput, lastNameInput, emailInput;
    AppCompatButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        usernameInput = findViewById(R.id.usernameRegInput);
        passwordInput = findViewById(R.id.passwordRegInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password, firstname, lastname, email;
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                firstname = firstNameInput.getText().toString();
                lastname = lastNameInput.getText().toString();
                email = emailInput.getText().toString();

                if(!username.isEmpty() && !password.isEmpty())
                {
                    addUser(username, password, firstname, lastname, email);
                }
                else
                {
                    Toast.makeText(Register.this,"Please make sure there are no empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void addUser(String uname, String password, String firstName, String lastName, String email)
    {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("username", uname);
        user.put("password", password);
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);

        // Add a new document with a generated ID
        db.collection("users").document(uname)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + uname);
                        Toast.makeText(Register.this,"Successfully Added " + uname, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,"Error adding user " + e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error adding document", e);
                    }
                });
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                        Toast.makeText(MainActivity.this,"Successfully Added " + uname, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this,"Error adding document " + e, Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "Error adding document", e);
//                    }
//                });

    }
}