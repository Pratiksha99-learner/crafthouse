package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class CategoryActivity extends AppCompatActivity {

   /* private Menu menu;
    RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //String title  = getIntent().getStringExtra("CategoryName");
        //getSupportActionBar().setTitle(title);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.navigation,menu);
        return true;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }*/

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.main_search_icon){
            //todo:search
            return true;
        }
        if (id == R.id.cart_white){
            //todo:cart
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}