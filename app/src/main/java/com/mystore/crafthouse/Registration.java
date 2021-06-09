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


public class Registration extends AppCompatActivity {

    EditText name;
    EditText phoneNo;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button register;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.Name);
        phoneNo = findViewById(R.id.Phone);
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

    }//END OF ONCREATE()

    private void checkUserWithUsernameAndPassword() {

        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull  Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Map<Object,String> userdata = new HashMap<>();
                                    userdata.put("Name",name.getText().toString());
                                    userdata.put("Phone No",phoneNo.getText().toString());
                                    userdata.put("Email",email.getText().toString());
                                    userdata.put("Password",password.getText().toString());
                                    userdata.put("Confirm Password",confirmPassword.getText().toString());

                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        Intent mainIntent = new Intent(Registration.this,MainActivity.class);
                                                        startActivity(mainIntent);
                                                    }else{
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(Registration.this,error,Toast.LENGTH_SHORT);
                                                    }
                                                }
                                            });

                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(Registration.this,error,Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }else {
                confirmPassword.setError("Password didn't match!");
            }
        }else{
            email.setError("Inavlid username");
        }

    }//END OF chechWithUsernameAndPassword
}//END OF MAIN()