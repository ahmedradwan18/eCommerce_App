package com.example.ecommerce.Seller;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerce.Adapters.SellerHomeAdapter;
import com.example.ecommerce.Adapters.checkProductsAdapter;
import com.example.ecommerce.Admin.AdminCheckProducts;
import com.example.ecommerce.Buyer.MainActivity;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Seller_Home extends AppCompatActivity {
    private TextView mTextMessage;

    private RecyclerView recyclerView;
    private DatabaseReference unverifiedProductsRef;
    ArrayList<products> productsList;
    SellerHomeAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intenttt=new Intent(Seller_Home.this, Seller_Home.class);
                    startActivity(intenttt);
                    return true;
                case R.id.Add:
                    Intent intentt=new Intent(Seller_Home.this, Seller_Categories.class);
                    startActivity(intentt);

                    return true;
                case R.id.Logout:
                    final FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent intent=new Intent(Seller_Home.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recyclerView = findViewById(R.id.sellerHomeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsList = new ArrayList<>();
        unverifiedProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    String Mid = (String) dataSnapshot1.child("seller id").getValue();

                    if (Mid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                        String price = (String) dataSnapshot1.child("price").getValue();
                        String des = (String) dataSnapshot1.child("description").getValue();
                        String image = (String) dataSnapshot1.child("image").getValue();
                        String id = (String) dataSnapshot1.child("Pid").getValue();
                        String name = (String) dataSnapshot1.child("name").getValue();
                        String state = (String) dataSnapshot1.child("product State").getValue();

                        products productss = new products();
                        productss.setPname(name);
                        productss.setPrice(price);
                        productss.setDescription(des);
                        productss.setImage(image);
                        productss.setPid(id);
                        productss.setState(state);

                        productsList.add(productss);


                    } else {
                        continue;
                    }


                }
                adapter = new SellerHomeAdapter(productsList, Seller_Home.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
