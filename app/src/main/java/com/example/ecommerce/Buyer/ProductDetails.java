package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.Models.products;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {
    private FloatingActionButton actionButton;
    private ImageView imageView;
    private TextView name, des, price;
    private ElegantNumberButton elegantNumberButton;
    private String productID, productname, productname2,productname3, state = "normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pID");
        productname = getIntent().getStringExtra("Name");
        productname2 = getIntent().getStringExtra("name");
        productname3 = getIntent().getStringExtra("namee");
        actionButton = findViewById(R.id.add_product_card);
        imageView = findViewById(R.id.product_image_details);
        name = findViewById(R.id.product_name_details);
        des = findViewById(R.id.product_description_details);
        price = findViewById(R.id.product_price_details);
        elegantNumberButton = findViewById(R.id.numberButton);


        getProductDetails(productID);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals("Order Placed") || state.equals("Order Shipped")) {
                    Toast.makeText(ProductDetails.this, "you can purchase more products , once your order is shipped or confirmed", Toast.LENGTH_LONG).show();
                } else {
                    addingToCart();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();

    }

    private void addingToCart() {
        String saveDate, saveTime;

        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveDate = currentDate.format(callForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(callForDate.getTime());
        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();


        cartMap.put("product ID", productID);

        if (!TextUtils.isEmpty(productname2)) {
            cartMap.put("product name", productname2);
        } else if(!TextUtils.isEmpty(productname)) {
            cartMap.put("product name", productname);
        }
        else{            cartMap.put("product name", productname3);
        }
        cartMap.put("product price", price.getText().toString());
        cartMap.put("product date", saveDate);
        cartMap.put("product time", saveTime);
        cartMap.put("product quantity", elegantNumberButton.getNumber());
        cartMap.put("product discount", "");
        cartRef.child("User View").child(prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartRef.child("Admin View").child(prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productID).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(ProductDetails.this, "Added To Cart List..", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(ProductDetails.this, Home.class);
                                                startActivity(i);

                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    products products = dataSnapshot.getValue(products.class);

                    name.setText(products.getPname());
                    des.setText(products.getDescription());
                    price.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                    if (shippedState.equals("shipped")) {
                        state = "Order Shipped";

                    } else if (shippedState.equals("not shipped")) {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


