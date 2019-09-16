package com.example.ecommerce.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Buyer.Home;
import com.example.ecommerce.Buyer.ProductDetails;
import com.example.ecommerce.Models.nCart;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class checkProductsAdapter extends RecyclerView.Adapter<checkProductsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<products> productsList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");

    public checkProductsAdapter(ArrayList<products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        final checkProductsAdapter.MyViewHolder viewHolder = new checkProductsAdapter.MyViewHolder(view);
        return new checkProductsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final products products = productsList.get(position);


        holder.name.setText("product's name : " + products.getPname());
        holder.price.setText("product's price : " + products.getPrice());
        holder.des.setText("product's Description : " + products.getDescription());
        Picasso.get().load(products.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "Yes",
                        "No"
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to approve this product ?");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            changeProductState(products.getPid());

                        }
                        if (i == 1) {


                        }

                    }
                });
                builder.show();


            }
        });


    }

    private void changeProductState(String pid) {

        ref.child(pid).child("product State")
                .setValue("Approved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "this item has been approved , and it's now available for sale..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (productsList == null) return 0;
        return productsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, des;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name_row);
            price = itemView.findViewById(R.id.product_price_row);
            des = itemView.findViewById(R.id.product_description_row);
            imageView = itemView.findViewById(R.id.product_image_row);

        }
    }


}
