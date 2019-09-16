package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecommerce.Adapters.SellerHomeAdapter;
import com.example.ecommerce.Adapters.categoriesAdapter;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.R;
import com.example.ecommerce.Seller.Seller_Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class displayCategoryProducts extends AppCompatActivity {
    private String productname;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    ArrayList<products> productsList;
    categoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_products);
        productname = getIntent().getStringExtra("Category");
        recyclerView = findViewById(R.id.displayCategoryProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        productsList = new ArrayList<>();


        if (productname.equals("shirts")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("T_shirts")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("sportive Shirts")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Sports_T_shirts")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("dresses")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Dresses")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("sweaters")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Sweaters")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("headphones")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("HeadPhones")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("laptops")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Laptops")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("watches")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Watches")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("mobiles")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Mobiles")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("glasses")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Glasses")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("bags")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Bags")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("hats")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Hats")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (productname.equals("shose")) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String type = (String) dataSnapshot1.child("category").getValue();

                        if (type.equals("Shoes")) {

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
                    adapter = new categoriesAdapter(productsList, displayCategoryProducts.this);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}
