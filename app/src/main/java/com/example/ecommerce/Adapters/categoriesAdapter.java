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

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<products> productsList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");

    public categoriesAdapter(ArrayList<products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        final categoriesAdapter.MyViewHolder viewHolder = new categoriesAdapter.MyViewHolder(view);
        return new categoriesAdapter.MyViewHolder(view);
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
        Intent intent=new Intent(context,ProductDetails.class);

        intent.putExtra("pID",products.getPid());
        intent.putExtra("namee",products.getPname());
        context.startActivity(intent);
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
