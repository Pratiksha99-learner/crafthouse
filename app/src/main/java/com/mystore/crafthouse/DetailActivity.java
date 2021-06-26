package com.mystore.crafthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mystore.crafthouse.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Button orderNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       /* binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            int image = getIntent().getIntExtra("image",0);
            int price = Integer.parseInt(getIntent().getStringExtra("price"));
            String name = getIntent().getStringExtra("name");

            binding.datailImage.setImageResource(image);
            binding.priceLbl.setText(String.format("Rs. %d",price));
            binding.nameBox.setText(name);*/

        orderNow = findViewById(R.id.orderBtn);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,Payment.class);
                startActivity(intent);
            }
        });



    }
}