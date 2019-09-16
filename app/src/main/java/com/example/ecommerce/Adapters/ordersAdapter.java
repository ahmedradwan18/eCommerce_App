package com.example.ecommerce.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Admin.AdminCheckOrders;
import com.example.ecommerce.Admin.Admin_user_products;
import com.example.ecommerce.Models.adminOrders;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ordersAdapter extends RecyclerView.Adapter<ordersAdapter.MyViewHolder> {
    private Context context;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Orders");
    private ArrayList<adminOrders> ordersList;

    public ordersAdapter(ArrayList<adminOrders> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row, parent, false);
        return new ordersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final adminOrders orders = ordersList.get(position);

        holder.Oname.setText("Name: " + orders.getName());
        holder.Ophone.setText("Phone: " + orders.getPhone());
        holder.Oprice.setText("Total Price: " + orders.getTotalAmount() + " $");
        holder.Otime.setText("Ordered at: " + orders.getDate() + " , " + orders.getTime());
        holder.OshippingAddress.setText("Shipping Address: " + orders.getAddress() + " , " + orders.getCity());
        holder.showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_user_products.class);
                intent.putExtra("Uid", orders.getPhone());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "Yes",
                        "No"
                };
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Have you shipped this order products?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {
                            reference.child(orders.getPhone()).removeValue();
                            Intent intent=new Intent(context, AdminCheckOrders.class);
                            context.startActivity(intent);
                            



                        } else {

                        }


                    }
                });
                builder.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        if (ordersList == null) return 0;
        return ordersList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Oname, Ophone, Oprice, Otime, OshippingAddress;
        public Button showAll;

        public MyViewHolder(View itemView) {
            super(itemView);
            Oname = itemView.findViewById(R.id.order_username);
            Ophone = itemView.findViewById(R.id.order_Phone);
            Oprice = itemView.findViewById(R.id.order_total_price);
            Otime = itemView.findViewById(R.id.order_date_time);
            OshippingAddress = itemView.findViewById(R.id.order_address_city);
            showAll = itemView.findViewById(R.id.show_all_products_btn);
        }
    }

}
