package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce.Adapters.checkProductsAdapter;
import com.example.ecommerce.Adapters.searchAdapter;
import com.example.ecommerce.Buyer.searchProducts;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCheckProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference unverifiedProductsRef;
    ArrayList<products> productsList;
    checkProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_products);
        recyclerView = findViewById(R.id.adminCheckProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsList = new ArrayList<>();

        unverifiedProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    String state = (String) dataSnapshot1.child("product State").getValue();
                    if (state.equals("Not Approved")) {
                        String price = (String) dataSnapshot1.child("price").getValue();
                        String des = (String) dataSnapshot1.child("description").getValue();
                        String image = (String) dataSnapshot1.child("image").getValue();
                        String id = (String) dataSnapshot1.child("Pid").getValue();
                        String name = (String) dataSnapshot1.child("name").getValue();

                        products productss = new products();
                        productss.setPname(name);
                        productss.setPrice(price);
                        productss.setDescription(des);
                        productss.setImage(image);
                        productss.setPid(id);

                        productsList.add(productss);


                    } else {
                        continue;
                    }


                }
                adapter = new checkProductsAdapter(productsList, AdminCheckProducts.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
