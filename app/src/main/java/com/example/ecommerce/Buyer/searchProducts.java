package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecommerce.Adapters.searchAdapter;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchProducts extends AppCompatActivity {
    private Button searchBTN;
    private EditText searchEDT;
    private RecyclerView recyclerView;
    private String searchInput;
    ArrayList<products> productsList, productsArrayList;
    searchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        searchBTN = findViewById(R.id.search_btn);
        searchEDT = findViewById(R.id.searchProduct);
        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Products");
        final DatabaseReference ref2 = database.getReference().child("Products");
        productsList = new ArrayList<>();
        productsArrayList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = (String) dataSnapshot1.child("name").getValue();
                    String price = (String) dataSnapshot1.child("price").getValue();
                    String des = (String) dataSnapshot1.child("description").getValue();
                    String image = (String) dataSnapshot1.child("image").getValue();
                    String id = (String) dataSnapshot1.child("Pid").getValue();
                    products products = new products();
                    products.setPname(name);
                    products.setPrice(price);
                    products.setDescription(des);
                    products.setImage(image);
                    products.setPid(id);

                    productsList.add(products);
                }
                adapter = new searchAdapter(productsList, searchProducts.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = searchEDT.getText().toString();

                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                            String name = (String) dataSnapshot1.child("name").getValue();
                            if (name.toLowerCase().contains(searchInput.toLowerCase())) {
                                String price = (String) dataSnapshot1.child("price").getValue();
                                String des = (String) dataSnapshot1.child("description").getValue();
                                String image = (String) dataSnapshot1.child("image").getValue();
                                String id = (String) dataSnapshot1.child("Pid").getValue();

                                products productss = new products();
                                productss.setPname(name);
                                productss.setPrice(price);
                                productss.setDescription(des);
                                productss.setImage(image);
                                productss.setPid(id);

                                productsArrayList.add(productss);


                            } else {
                                continue;
                            }


                        }
                        adapter = new searchAdapter(productsArrayList, searchProducts.this);
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<products> options =
                new FirebaseRecyclerOptions.Builder<products>()
                        .setQuery(databaseReference.orderByChild("name").startAt(searchInput).endAt(searchInput), products.class)
                        .build();
        FirebaseRecyclerAdapter<products, productViewHolder> adapter =
                new FirebaseRecyclerAdapter<products, productViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final productViewHolder productViewHolder, int i, @NonNull final products products) {


                        productViewHolder.productdescription.setText(products.getDescription());
                        productViewHolder.productPrice.setText("Price = " + products.getPrice() + "$");
                        Picasso.get().load(products.getImage()).into(productViewHolder.productImg);


                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent ii = new Intent(searchProducts.this, ProductDetails.class);
                                ii.putExtra("pID", products.getPid());
                                startActivity(ii);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
                        productViewHolder holder = new productViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }*/
}
