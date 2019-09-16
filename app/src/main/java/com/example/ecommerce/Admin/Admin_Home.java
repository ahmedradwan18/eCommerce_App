package com.example.ecommerce.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.Buyer.Home;
import com.example.ecommerce.Buyer.MainActivity;
import com.example.ecommerce.R;

public class Admin_Home extends AppCompatActivity {
    private Button logout, check, maintain,checkProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);


          logout = findViewById(R.id.logout_admin);
        maintain = findViewById(R.id.maintainProducts);
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Home.this, Home.class);

                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });
        checkProducts=findViewById(R.id.CheckApproveProducts);
        checkProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Admin_Home.this,AdminCheckProducts.class);
                startActivity(intent);

            }
        });
        check = findViewById(R.id.checkOrders);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Home.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Home.this, AdminCheckOrders.class);
                startActivity(intent);
            }
        });
    }
}
