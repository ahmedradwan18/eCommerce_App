package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Adapters.cartAdapter;
import com.example.ecommerce.Admin.confirmedFinalOrder;
import com.example.ecommerce.Models.nCart;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private Button next;
    ArrayList<nCart> cartList;
    cartAdapter adapter;
    private RecyclerView recyclerView;
    private int totalPrice = 0;
    private TextView total, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        checkOrderState();
        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        next = findViewById(R.id.next);
        total = findViewById(R.id.total_price);
        msg = findViewById(R.id.msg1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Cart List");





        cartList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);


        ref.child("User View").child(prevalent.currentOnlineUser.getPhone()).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = (String) dataSnapshot1.child("product name").getValue();
                    String price = (String) dataSnapshot1.child("product price").getValue();
                    String quantity = (String) dataSnapshot1.child("product quantity").getValue();
                    String id = (String) dataSnapshot1.child("product ID").getValue();
                    nCart nCart = new nCart();
                    nCart.setpName(name);
                    nCart.setPrice(price);
                    nCart.setQuantity(quantity);
                    nCart.setPid(id);

                    int oneTypeProductPrice = Integer.valueOf(price) * Integer.valueOf(quantity);
                    totalPrice = totalPrice + oneTypeProductPrice;

                    cartList.add(nCart);
                }
                adapter = new cartAdapter(cartList, Cart.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalPrice == 0) {
                    Toast.makeText(Cart.this, "You have no item in the cart ", Toast.LENGTH_SHORT).show();
                } else {
                    total.setText("Total Price = " + String.valueOf(totalPrice) + " $");
                    Intent intent = new Intent(Cart.this, confirmedFinalOrder.class);
                    intent.putExtra("Total Price", String.valueOf(totalPrice));
                    Toast.makeText(Cart.this, "Total Price is " + totalPrice + " $", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

    }

    private void checkOrderState() {
        DatabaseReference stateRef;
        stateRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(prevalent.currentOnlineUser.getPhone());
        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String shippedState = dataSnapshot.child("Stated").getValue().toString();
                    String name = dataSnapshot.child("Name").getValue().toString();
                    if(shippedState.equals("shipped"))
                    {
                        total.setText("Dear "+name+"\n your order has been shipped successfully ..");
                        recyclerView.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        msg.setVisibility(View.VISIBLE);
                        Toast.makeText(Cart.this, "you can purchase more products , once you received your order", Toast.LENGTH_LONG).show();




                    }
                    else if(shippedState.equals("not shipped"))
                    {
                        total.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        msg.setVisibility(View.VISIBLE);
                        Toast.makeText(Cart.this, "you can purchase more products , once you received your order", Toast.LENGTH_LONG).show();


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
