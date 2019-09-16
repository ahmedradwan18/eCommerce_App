package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Buyer.Home;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class confirmedFinalOrder extends AppCompatActivity {
    private EditText name, phone, city, address;
    private Button confirm;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_final_order);
        name = findViewById(R.id.shipment_name);
        phone = findViewById(R.id.shipment_phone);
        city = findViewById(R.id.shipment_city);
        address = findViewById(R.id.shipment_address);
        confirm = findViewById(R.id.confirm_final_order_btn);
        totalAmount = getIntent().getStringExtra("Total Price");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });


    }

    private void check() {

        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(confirmedFinalOrder.this, "Please provide your full name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(confirmedFinalOrder.this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(confirmedFinalOrder.this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(city.getText().toString())) {
            Toast.makeText(confirmedFinalOrder.this, "Please provide your City name", Toast.LENGTH_SHORT).show();
        } else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        final String saveDate, saveTime;
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveDate = currentDate.format(callForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(callForDate.getTime());
        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(prevalent.currentOnlineUser.getPhone());
        HashMap<String, Object> ordersMap = new HashMap<>();

        ordersMap.put("Total Amount", totalAmount);
        ordersMap.put("Name", name.getText().toString());
        ordersMap.put("Phone", phone.getText().toString());
        ordersMap.put("Address", address.getText().toString());
        ordersMap.put("City", city.getText().toString());
        ordersMap.put("Date", saveDate);
        ordersMap.put("Time", saveTime);
        ordersMap.put("Stated", "not shipped");
        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(confirmedFinalOrder.this, "Your final order has been placed successfully ..", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(confirmedFinalOrder.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }
}
