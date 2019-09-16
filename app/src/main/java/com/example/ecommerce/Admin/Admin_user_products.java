package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce.Adapters.productsAdapter;
import com.example.ecommerce.Models.nCart;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_user_products extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference cartListRef;
    String userID = "";
    ArrayList<nCart> productsList;
    productsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        userID = getIntent().getStringExtra("Uid");
        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        productsList = new ArrayList<>();
        recyclerView = findViewById(R.id.productsList2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);


        cartListRef.child("Admin View").child(userID).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = (String) dataSnapshot1.child("product name").getValue();
                    String price = (String) dataSnapshot1.child("product price").getValue();
                    String quantity = (String) dataSnapshot1.child("product quantity").getValue();
                    nCart nCart = new nCart();
                    nCart.setpName(name);
                    nCart.setPrice(price);
                    nCart.setQuantity(quantity);

                    productsList.add(nCart);
                }
                adapter = new productsAdapter(productsList, Admin_user_products.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
