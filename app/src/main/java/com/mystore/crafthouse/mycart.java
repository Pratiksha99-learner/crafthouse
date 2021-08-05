package com.mystore.crafthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class mycart extends AppCompatActivity {

    ImageView getImage;
    TextView receiveName,receivePrice,receiveQuant;
    Button placeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        getImage = findViewById(R.id.receiveimage);
        receiveName = findViewById(R.id.receiveName);
        receivePrice = findViewById(R.id.receivePrice);
        receiveQuant = findViewById(R.id.receiveQuant);
        placeOrder = findViewById(R.id.placeOrder);

        getImage.setImageResource(getIntent().getIntExtra("cartImage",0));
        receiveName.setText(getIntent().getStringExtra("cartImageName"));
        receivePrice.setText("250");
        receiveQuant.setText("1");

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycart.this,Payment.class);
                startActivity(intent);
            }
        });

    }
}