package com.example.ecommerce.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Models.nCart;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<nCart> cartList;


    public productsAdapter(ArrayList<nCart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        final productsAdapter.MyViewHolder viewHolder = new productsAdapter.MyViewHolder(view);
        return new productsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final nCart cart = cartList.get(position);

        holder.name.setText("item's name : " + cart.getpName());
        holder.price.setText("item's price : " + cart.getPrice());
        holder.quantity.setText("item's quantity : " + cart.getQuantity());


    }

    @Override
    public int getItemCount() {
        if (cartList == null) return 0;
        return cartList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity,total;
        Button nextbtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            nextbtn = itemView.findViewById(R.id.next);
            total=itemView.findViewById(R.id.total_price);
        }
    }
}
