package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.Seller.Seller_Categories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProducts extends AppCompatActivity {
    private Button apply, delete;
    private ImageView imageView;
    private EditText nameEDT, priceEDT, desEDT;
    private String productID;
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        apply = findViewById(R.id.applyChanges);
        imageView = findViewById(R.id.product_image_maintain);
        delete = findViewById(R.id.DeleteProduct);

        nameEDT = findViewById(R.id.product_name_maintain);
        priceEDT = findViewById(R.id.product_price_maintain);
        desEDT = findViewById(R.id.product_description_maintain);
        productID = getIntent().getStringExtra("pID");
        Toast.makeText(this, "aho" + productID, Toast.LENGTH_SHORT).show();
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        deleteProduct();
            }
        });
        displaySpecificProductDeatils();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyChanges();
            }
        });

    }

    private void deleteProduct() {
productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Intent intent = new Intent(AdminMaintainProducts.this, Seller_Categories.class);
            startActivity(intent);
            finish();
            Toast.makeText(AdminMaintainProducts.this, "product removes successfully ..", Toast.LENGTH_SHORT).show();
        }
    }
});







    }

    private void ApplyChanges() {
        String name2 = nameEDT.getText().toString();
        String price2 = priceEDT.getText().toString();
        String des2 = desEDT.getText().toString();

        if (name2.equals("")) {
            Toast.makeText(this, "write down product name ..", Toast.LENGTH_SHORT).show();
        } else if (price2.equals("")) {
            Toast.makeText(this, "write down product price ..", Toast.LENGTH_SHORT).show();
        } else if (des2.equals("")) {
            Toast.makeText(this, "write down product description ..", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> productsMap = new HashMap<>();
            productsMap.put("id", productID);
            productsMap.put("name", name2);
            productsMap.put("price", price2);
            productsMap.put("description", des2);

            productsRef.updateChildren(productsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminMaintainProducts.this, "changes applies successfully..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminMaintainProducts.this, Seller_Categories.class);
                        startActivity(intent);
                        finish();

                    }
                }
            });
        }


    }

    private void displaySpecificProductDeatils() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String price = dataSnapshot.child("price").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();


                    nameEDT.setText(name);
                    priceEDT.setText(price);
                    desEDT.setText(description);
                    Picasso.get().load(image).into(imageView);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
