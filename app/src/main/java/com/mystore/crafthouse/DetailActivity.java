package com.mystore.crafthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystore.crafthouse.databinding.ActivityDetailBinding;
import com.mystore.crafthouse.model.BestForYou;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Button orderNow;

    ImageView imageView,back,cart;
    TextView price,title;
    Button addToCart;
    ImageView logout;

    List<BestForYou> bestForYouList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        logout = findViewById(R.id.whitelogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

       /* binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            int image = getIntent().getIntExtra("image",0);
            int price = Integer.parseInt(getIntent().getStringExtra("price"));
            String name = getIntent().getStringExtra("name");

            binding.datailImage.setImageResource(image);
            binding.priceLbl.setText(String.format("Rs. %d",price));
            binding.nameBox.setText(name);*/

        imageView = findViewById(R.id.detailImage);
        price = findViewById(R.id.priceLbl);
        title = findViewById(R.id.nameBox);

        //back = findViewById(R.id.myBackBtn);
        addToCart = findViewById(R.id.orderBtn);
        cart = findViewById(R.id.mycart);

        imageView.setImageResource(getIntent().getIntExtra("image",0));
        price.setText(getIntent().getStringExtra("price"));
        title.setText(getIntent().getStringExtra("name"));

        orderNow = findViewById(R.id.orderBtn);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(DetailActivity.this,Payment.class);
                startActivity(intent);*/

                Intent intent = new Intent(DetailActivity.this,mycart.class);
                startActivity(intent);
            }
        });

        int imagesrc = getIntent().getIntExtra("image",0);
        String imagename = getIntent().getStringExtra("name");

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,mycart.class);
                intent.putExtra("cartImage",imagesrc);
                intent.putExtra("cartImageName",imagename);
                startActivity(intent);
            }
        });

    }
}