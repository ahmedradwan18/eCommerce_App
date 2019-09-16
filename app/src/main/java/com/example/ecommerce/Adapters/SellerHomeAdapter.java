package com.example.ecommerce.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Models.products;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SellerHomeAdapter extends RecyclerView.Adapter<SellerHomeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<products> productsList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
    public SellerHomeAdapter(ArrayList<products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_product_item, parent, false);
        final SellerHomeAdapter.MyViewHolder viewHolder = new SellerHomeAdapter.MyViewHolder(view);
        return new SellerHomeAdapter.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        final products products = productsList.get(position);


        holder.name.setText("product's name : " + products.getPname());
        holder.price.setText("product's price : " + products.getPrice());
        holder.des.setText("product's Description : " + products.getDescription());
        holder.state.setText("product's State : " + products.getState());
        Picasso.get().load(products.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "Yes",
                        "No"
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to Delete this product ?");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            deleteItem(products.getPid());

                        }
                        if (i == 1) {


                        }

                    }
                });
                builder.show();


            }
        });










    }

    private void deleteItem(String pid) {


        ref.child(pid)
              .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "this item has been deleted successfully...", Toast.LENGTH_SHORT).show();
                    }
                });







    }

    @Override
    public int getItemCount() {
        if (productsList == null) return 0;
        return productsList.size();    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, des,state;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Sproduct_name_row);
            price = itemView.findViewById(R.id.Sproduct_price_row);
            des = itemView.findViewById(R.id.Sproduct_description_row);
            imageView = itemView.findViewById(R.id.Sproduct_image_row);
            state = itemView.findViewById(R.id.Sproduct_state_row);

        }
    }





}
