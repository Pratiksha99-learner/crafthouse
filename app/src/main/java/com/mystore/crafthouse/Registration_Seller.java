package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration_Seller extends AppCompatActivity {

    EditText name,phoneNo,address,email,password,confirmPassword;
    Button register;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_seller);

       name = findViewById(R.id.Name);
        phoneNo = findViewById(R.id.Phone);
        address = findViewById(R.id.Address);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.confPaswd);
        progressBar = findViewById(R.id.progressBar);
        register = findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserWithUsernameAndPassword();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void checkUserWithUsernameAndPassword() {
        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Map<Object,String> userdata = new HashMap<>();
                                    userdata.put("Name",name.getText().toString());
                                    userdata.put("Phone No",phoneNo.getText().toString());
                                    userdata.put("Address",address.getText().toString());
                                    userdata.put("Email",email.getText().toString());
                                    userdata.put("Password",password.getText().toString());
                                    userdata.put("Confirm Password",confirmPassword.getText().toString());
                                    userdata.put("Account Type","Seller");

                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        Intent mainIntent = new Intent(Registration_Seller.this,MainActivity.class);
                                                        startActivity(mainIntent);
                                                    }else{
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(Registration_Seller.this,error,Toast.LENGTH_SHORT);
                                                    }
                                                }
                                            });

                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(Registration_Seller.this,error,Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }else {
                confirmPassword.setError("Password didn't match!");
            }
        }else{
            email.setError("Inavlid username");
        }

    }
}