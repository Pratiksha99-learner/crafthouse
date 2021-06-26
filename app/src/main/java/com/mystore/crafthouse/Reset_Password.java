package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_Password extends AppCompatActivity {

    EditText registeredEmail;
    Button resetPasswordBtn;
    TextView goBack;
    FirebaseAuth firebaseAuth;
    ViewGroup emailIconContainer;
    ImageView emailIcon;
    TextView emailIconText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        registeredEmail = (EditText) findViewById(R.id.forgot_password_email);
        resetPasswordBtn = (Button) findViewById(R.id.button);
        goBack = (TextView) findViewById(R.id.forgot_password_go_back);
        /*emailIconContainer = (ViewGroup) findViewById(R.id.forgot_password_email_icon_container);
        emailIcon = (ImageView) findViewById(R.id.forgot_email_icon);
        emailIconText = (TextView) findViewById(R.id.forgot_password_email_icon_text);
        progressBar = (ProgressBar) findViewById(R.id.forgot_password_progressbar);*/

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Reset_Password.this,MainActivity.class);
                startActivity(back);
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {

            

            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Reset_Password.this,"email sent",Toast.LENGTH_SHORT).show();

                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Reset_Password.this, error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }//END OF ONCREATE()
}//END OF MAIN()