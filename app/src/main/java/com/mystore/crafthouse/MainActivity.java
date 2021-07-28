package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button login;
    //Button register;
    TextView register;
    TextView forgotPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.login7);
        username = findViewById(R.id.login1);
        password = findViewById(R.id.login2);

        forgotPassword = findViewById(R.id.login3);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoReset = new Intent(MainActivity.this,Reset_Password.class);
                startActivity(gotoReset);
            }
        });

        login=findViewById(R.id.login4);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this,Frame2.class);
                startActivity(intent);*/
                checkUserEmailAndPassword();
            }
        });

        register= findViewById(R.id.login6);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }//END OF ONCREATE()

    private void checkUserEmailAndPassword() {

        if(username.getText().toString().matches(emailPattern)){
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Intent intent = new Intent(MainActivity.this,navigation.class);
                                /*Intent intent = new Intent(MainActivity.this,Frame2.class);
                                startActivity(intent);*/
                                checkUserType();
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                String error=task.getException().getMessage();
                                Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT);
                            }
                        }
                    });

        }else{
            username.setError("Invalid username or password");
        }
    }

    private  void checkUserType(){

        CollectionReference reference = firebaseFirestore.collection("USERS");
        FirebaseUser documentReference = firebaseAuth.getCurrentUser();
        String currentEmail = documentReference.getEmail();
        String uid = documentReference.getUid();


        firebaseFirestore.collection("USERS")
               .get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                       for (DocumentSnapshot snapshot:snapshotList){

                           String emailVal = snapshot.getString("Email");
                           String accType = snapshot.getString("Account Type");
                           if(currentEmail.equals(emailVal)){
                               if (accType.equals("Seller")){
                                   //goto seller page
                                   //Intent intent = new Intent(MainActivity.this,Seller.class);
                                   Intent intent = new Intent(MainActivity.this,AddProductActivity.class);
                                   startActivity(intent);
                               }else {
                                   //goto customer page
                                   Intent intent = new Intent(MainActivity.this,Frame2.class);
                                   startActivity(intent);
                               }
                               //Toast.makeText(MainActivity.this,emailVal,Toast.LENGTH_SHORT).show();}
                           //else{Toast.makeText(MainActivity.this,"Not same",Toast.LENGTH_SHORT).show();}
                           //Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();

                       }
                   }
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull @NotNull Exception e) {

                   }
               });




    }//END OF CHECKUSERTYPE()

}//END OF MAIN()