package com.mystore.crafthouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.mystore.crafthouse.DetailActivity;
import com.mystore.crafthouse.Frame2;
import com.mystore.crafthouse.R;
import com.mystore.crafthouse.model.BestForYou;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BestForYouAdapter extends RecyclerView.Adapter<BestForYouAdapter.BestForYouViewHolder> {

    Context context;
    List<BestForYou> bestForYouList;

    public BestForYouAdapter(Context context, List<BestForYou> bestForYouList) {
        this.context = context;
        this.bestForYouList = bestForYouList;
    }


    @NonNull
    @NotNull
    @Override
    public BestForYouViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new BestForYouViewHolder(LayoutInflater.from(context).inflate(R.layout.best_for_you_row_item , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BestForYouAdapter.BestForYouViewHolder holder, int position) {

        final BestForYou model = bestForYouList.get(position);

        holder.itemImage.setImageResource(bestForYouList.get(position).getImageURL());
        holder.itemName.setText(bestForYouList.get(position).getName());
        holder.itemTime.setText(bestForYouList.get(position).getTime());
        holder.itemCurrency.setText(bestForYouList.get(position).getCurrency());
        holder.itemPrice.setText(Integer.toString(bestForYouList.get(position).getPrice()));
        holder.itemRating.setRating(Float.parseFloat(bestForYouList.get(position).getRating()));
        // holder.itemCart.(bestForYouList.get(position).);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                //intent.putExtra("image",model.getImageURL());
                //intent.putExtra("price",model.getPrice());
                //intent.putExtra("name",model.getName());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return bestForYouList.size();
    }

    public static final class BestForYouViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemCurrency,itemPrice, itemTime, itemName;
        RatingBar itemRating;
        ImageButton itemCart;
       // MenuView.ItemView itemView;

        public BestForYouViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemCurrency=itemView.findViewById(R.id.item_currency);
            itemTime = itemView.findViewById(R.id.item_time);
            itemName = itemView.findViewById(R.id.item_name);
            itemRating = itemView.findViewById(R.id.item_rating);
            // itemCart = itemView.findViewById(R.id.item_cart);


        }

    }
}
