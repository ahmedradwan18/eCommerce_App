package com.example.ecommerce.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;

public class chooseCategories extends AppCompatActivity {
    Button shirts, sportivShirts, dresses, sweaters, headphones, laptops, watches, mobiles, glasses, bags, hats, shose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        shirts = findViewById(R.id.t_shirtsBTN);
        sportivShirts = findViewById(R.id.sports_t_shirtsBTN);
        dresses = findViewById(R.id.female_dressesBTN);
        sweaters = findViewById(R.id.sweaterBTN);
        headphones = findViewById(R.id.headPhonesBTN);
        laptops = findViewById(R.id.laptopsBTN);
        watches = findViewById(R.id.watchesBTN);
        mobiles = findViewById(R.id.mobilesBTN);
        glasses = findViewById(R.id.glassesBTN);
        bags = findViewById(R.id.purses_bagsBTN);
        hats = findViewById(R.id.hatsBTN);
        shose = findViewById(R.id.shoesBTN);

        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "shirts");
                startActivity(intent);
            }
        });
        sportivShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "sportive Shirts");
                startActivity(intent);
            }
        });
        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "dresses");
                startActivity(intent);
            }
        });
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "sweaters");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "headphones");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "watches");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "mobiles");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "glasses");
                startActivity(intent);
            }
        });
        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "bags");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "hats");
                startActivity(intent);
            }
        });
        shose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseCategories.this, displayCategoryProducts.class);
                intent.putExtra("Category", "shose");
                startActivity(intent);
            }
        });



    }
}
