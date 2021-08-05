package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.mystore.crafthouse.adapter.BestForYouAdapter;
import com.mystore.crafthouse.model.BestForYou;

import java.util.ArrayList;
import java.util.List;

public class Frame2 extends AppCompatActivity {

    RecyclerView bestForYouRecycler;
    BestForYouAdapter bestForYouAdapter;
    ImageView logout;

    /*private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame2);
        logout = findViewById(R.id.whitelogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Frame2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        /*categoryRecyclerView = findViewById(R.id.category_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        List<CategoryModel> categoryModelList = new ArrayList<>();//CategoryModel
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Wall Art"));
        categoryModelList.add(new CategoryModel("link","Fashion"));
        categoryModelList.add(new CategoryModel("link","Toys"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryAdapter = new CategoryAdapter(this,categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }*/
        List<BestForYou> bestForYouList=new ArrayList<>();
        bestForYouList.add(new BestForYou("Bags","3.5","34min","Rs.",250,R.drawable.craft1));
        bestForYouList.add(new BestForYou("Pot","4.2","35min","Rs.",200, R.drawable.pot));
        bestForYouList.add(new BestForYou("Wall hanging ","2.0","55min","Rs.",100, R.drawable.paper_wall_hanging));
        bestForYouList.add(new BestForYou("Box","2.0","55min","Rs.",50,R.drawable.box));
        bestForYouList.add(new BestForYou("Trikona","5.0","56min","Rs.",280,R.drawable.trikona));
        bestForYouList.add(new BestForYou("Earrings","3.0","57min","Rs.",40,R.drawable.earrings));
        bestForYouList.add(new BestForYou("Box","2.0","58min","Rs.",50,R.drawable.box));
        setBestForYouRecycler(bestForYouList);
    }//END OF ONCREATE()


    private void setBestForYouRecycler(List<BestForYou> bestForYouList){
        bestForYouRecycler=findViewById(R.id.best_for_you_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        bestForYouRecycler.setLayoutManager(layoutManager);
        bestForYouAdapter=new BestForYouAdapter(this,bestForYouList);
        bestForYouRecycler.setAdapter(bestForYouAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.myOrders){
            //todo:search
            return true;
        }
        if (id == R.id.logout){
            //todo:cart
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}//END OF FRAME2 CLASSS