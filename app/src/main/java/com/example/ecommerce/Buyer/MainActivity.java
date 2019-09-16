package com.example.ecommerce.Buyer;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.Models.Users;
import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.example.ecommerce.Seller.SellerLogin;
import com.example.ecommerce.Seller.SellerRegisteration;
import com.example.ecommerce.Seller.Seller_Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button login, joinNow;
    private ProgressDialog loading;
    private TextView seler_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.main_login_btn);
        joinNow = findViewById(R.id.main_join_now_btn);
        seler_login = findViewById(R.id.sellerBegin);
        seler_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SellerRegisteration.class);
                startActivity(intent);
            }
        });
        Paper.init(this);
        loading = new ProgressDialog(this);
        String phone = Paper.book().read(prevalent.UserPhoneKey);
        String pass = Paper.book().read(prevalent.UserPassword);
        if (phone != "" && pass != "") {
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass)) {

                AllowAccess(phone, pass);


                AllowAccess(phone, pass);
                loading.setTitle("Already Logged in");
                loading.setMessage("Please wait...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
            }

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.ecommerce.Buyer.login.class);
                startActivity(i);
            }
        });
        joinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent=new Intent(MainActivity.this, Seller_Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void AllowAccess(final String mPhone, final String mPass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(mPhone).exists()) {
                    Users usersdata = dataSnapshot.child("Users").child(mPhone).getValue(Users.class);

                    if (usersdata.getPhone().equals(mPhone)) {
                        if (usersdata.getPassword().equals(mPass)) {
                            Toast.makeText(MainActivity.this, "you are already logged in", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            Intent i = new Intent(MainActivity.this, Home.class);
                            prevalent.currentOnlineUser = usersdata;
                            startActivity(i);
                        } else {
                            loading.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect ...", Toast.LENGTH_SHORT).show();
                        }

                    }


                } else {
                    Toast.makeText(MainActivity.this, "Account with this " + mPhone + " number doesn't exist", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(MainActivity.this, "you need to create new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
