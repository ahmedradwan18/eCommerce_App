package com.example.ecommerce.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ecommerce.R;

public class Seller_Categories extends AppCompatActivity {
    private ImageView shirts, sports_shirts, dresses, sweaters, headphones, laptops, watches, mobiles, bags, glasses, hats, shoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__categories);
        shirts = findViewById(R.id.t_shirts);
        sports_shirts = findViewById(R.id.sports_t_shirts);
        dresses = findViewById(R.id.female_dresses);
        sweaters = findViewById(R.id.sweather);
        headphones = findViewById(R.id.headPhones);
        laptops = findViewById(R.id.laptops);
        watches = findViewById(R.id.watches);
        mobiles = findViewById(R.id.mobiles);
        bags = findViewById(R.id.purses_bags);
        glasses = findViewById(R.id.glasses);
        hats = findViewById(R.id.hats);
        shoes = findViewById(R.id.shoes);

        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "T_shirts");
                startActivity(i);
            }
        });
//
        sports_shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Sports_T_shirts");
                startActivity(i);
            }
        });


        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Dresses");
                startActivity(i);
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Sweaters");
                startActivity(i);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "HeadPhones");
                startActivity(i);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Laptops");
                startActivity(i);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Watches");
                startActivity(i);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Mobiles");
                startActivity(i);
            }
        });

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Bags");
                startActivity(i);
            }
        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Glasses");
                startActivity(i);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Hats");
                startActivity(i);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Seller_Categories.this, Seller_Add_Product.class);
                i.putExtra("Category", "Shoes");
                startActivity(i);
            }
        });


    }
}
