package com.example.ecommerce.Buyer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.ecommerce.Admin.AdminMaintainProducts;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.example.ecommerce.viewHolders.productViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, productsRef;
    String CurrentUserID, productID, name;
    TextView userNameTextView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String productname = "", productPrice = "", productquantity = "", type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        productPrice = getIntent().getStringExtra("price");
        type = getIntent().getStringExtra("Admin");
       // Toast.makeText(this, "ahi "+type, Toast.LENGTH_SHORT).show();
        productname = getIntent().getStringExtra("name");
        productquantity = getIntent().getStringExtra("quantity");
        Paper.init(this);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type != null && !type.equals("Admin")) {
                    Intent i = new Intent(Home.this, Cart.class);
                    i.putExtra("Name", productname);
                    i.putExtra("price", productPrice);
                    i.putExtra("quantity", productquantity);
                    startActivity(i);
                }


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.username);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile);
        // userNameTextView.setText(prevalent.currentOnlineUser.getPassword());


        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");


       if (type == null ) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference Ref = database.getReference("Users");
            Ref.child(prevalent.currentOnlineUser.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.child("name").getValue();
                    userNameTextView.setText(name + "");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Picasso.get().load(prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        }


        recyclerView = findViewById(R.id.Recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(productsRef.orderByChild("product State").equalTo("Approved"), products.class).build();


        FirebaseRecyclerAdapter<products, productViewHolder> adapter =
                new FirebaseRecyclerAdapter<products, productViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final productViewHolder productViewHolder, int i, @NonNull final products products) {
                        productID = products.getDate() + products.getTime();

                        final Intent ii = new Intent(Home.this, ProductDetails.class);
                        final Intent iii = new Intent(Home.this, AdminMaintainProducts.class);
                        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                name = (String) dataSnapshot.child("name").getValue();
                                productViewHolder.productName.setText(name);
                                ii.putExtra("Name", name);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        productViewHolder.productdescription.setText(products.getDescription());
                        productViewHolder.productPrice.setText("Price = " + products.getPrice() + "$");
                        Picasso.get().load(products.getImage()).into(productViewHolder.productImg);
                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (type != null && type.equals("Admin")) {
                                    productID = products.getDate() + products.getTime();

                                    iii.putExtra("pID", productID);
                                    startActivity(iii);

                                } else {
                                    productID = products.getDate() + products.getTime();
                                    Toast.makeText(Home.this, "" + productID, Toast.LENGTH_SHORT).show();
                                    ii.putExtra("pID", productID);
                                    startActivity(ii);
                                }


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

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_card) {
            if (type == null) {
                Intent i = new Intent(Home.this, Cart.class);
                startActivity(i);
            }


        } else if (id == R.id.nav_search) {
            if (type == null ) {
                Intent i = new Intent(Home.this, searchProducts.class);
                startActivity(i);
            }


        } else if (id == R.id.nav_categories) {

            if (type == null ) {
                Intent i = new Intent(Home.this, chooseCategories.class);
                startActivity(i);
            }


        } else if (id == R.id.nav_setting) {
            if (type == null ) {
                Intent i = new Intent(Home.this, Settings.class);
                startActivity(i);
            }


        } else if (id == R.id.nav_logout) {
            if (type == null) {
                Paper.book().destroy();
                Intent i = new Intent(Home.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
