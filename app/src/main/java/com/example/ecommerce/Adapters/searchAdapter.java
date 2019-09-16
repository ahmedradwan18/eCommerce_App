package com.example.ecommerce.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Models.products;
import com.example.ecommerce.Buyer.ProductDetails;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<products> productsList;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart List");

    public searchAdapter(ArrayList<products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new searchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final products products = productsList.get(position);
        //ref.orderByChild("name").startAt(products.getCategory()).endAt(products.getCategory());

        holder.description.setText(products.getDescription());
        holder.name.setText(products.getPname());
        holder.price.setText("Price = " + products.getPrice() + "$");
        Picasso.get().load(products.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(context, ProductDetails.class);
                ii.putExtra("pID", products.getPid());
                context.startActivity(ii);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productsList == null) return 0;
        return productsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name_row);
            price = itemView.findViewById(R.id.product_price_row);
            description = itemView.findViewById(R.id.product_description_row);
            imageView = itemView.findViewById(R.id.product_image_row);
        }
    }


}
