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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Buyer.Home;
import com.example.ecommerce.Models.nCart;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.Buyer.ProductDetails;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<nCart> cartList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart List");

    public cartAdapter(ArrayList<nCart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public cartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new cartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartAdapter.MyViewHolder holder, int position) {
        final nCart cart = cartList.get(position);

        holder.name.setText("item's name : " + cart.getpName());
        holder.price.setText("item's price : " + cart.getPrice());
        holder.quantity.setText("item's quantity : " + cart.getQuantity());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "Edit",
                        "Remove"
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("cart Options : ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {

                            Intent intent = new Intent(context, ProductDetails.class);
                            intent.putExtra("pID", cart.getPid());
                            intent.putExtra("name", cart.getpName());
                            Toast.makeText(context, "" + cart.getpName(), Toast.LENGTH_SHORT).show();
                            context.startActivity(intent);
                        }
                        if (i == 1) {

                            ref.child("User View").child(prevalent.currentOnlineUser.getPhone())
                                    .child("Products")
                                    .child(cart.getPid())
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Item Removed ", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(context, Home.class);
                                                context.startActivity(intent);
                                            }


                                        }
                                    });
                        }

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartList == null) return 0;
        return cartList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, total;
        Button nextbtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            nextbtn = itemView.findViewById(R.id.next);
            total = itemView.findViewById(R.id.total_price);
        }
    }
}
