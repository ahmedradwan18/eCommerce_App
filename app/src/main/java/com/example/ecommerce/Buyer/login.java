package com.example.ecommerce.Buyer;

import com.example.ecommerce.Admin.Admin_Home;
import com.example.ecommerce.Seller.Seller_Categories;
import com.example.ecommerce.R;
import com.rey.material.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Models.Users;
import com.example.ecommerce.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class login extends AppCompatActivity {
    private EditText Phone, pass;
    private Button login;
    private ProgressDialog loading;
    private String parentDB = "Users";
    private CheckBox checkBoxRemember;
    private TextView adminLink, notAdminLink,forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Phone = findViewById(R.id.login_phoneNum);
        pass = findViewById(R.id.login_password);
        login = findViewById(R.id.login_btn);
        adminLink = findViewById(R.id.admin_pannel_link);
        notAdminLink = findViewById(R.id.not_admin_pannel_link);
        forgotPass = findViewById(R.id.forgetPass_Link);
        checkBoxRemember = findViewById(R.id.rememberME_check);
        Paper.init(this);
        loading = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDB = "Admins";
            }
        });




        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(login.this, resetPass.class);
                intent.putExtra("check", "Login");
                startActivity(intent);





            }
        });








notAdminLink.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        login.setText("Login");
        adminLink.setVisibility(View.VISIBLE);
        notAdminLink.setVisibility(View.INVISIBLE);
        parentDB = "Users";

    }
});
    }

    private void LoginUser() {
        String mPhone = Phone.getText().toString();
        String mPass = pass.getText().toString();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mPass)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {

            loading.setTitle("Login Account");
            loading.setMessage("Please wait, while we are checking the credentials.");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            AllowAccessToAccount(mPhone, mPass);
        }
    }

    private void AllowAccessToAccount(final String mPhone, final String mPass) {
        if (checkBoxRemember.isChecked()) {
            Paper.book().write(prevalent.UserPhoneKey, mPhone);
            Paper.book().write(prevalent.UserPassword, mPass);

        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDB).child(mPhone).exists()) {
                    Users usersdata = dataSnapshot.child(parentDB).child(mPhone).getValue(Users.class);

                    if (usersdata.getPhone().equals(mPhone)) {
                        if (usersdata.getPassword().equals(mPass)) {
                           if(parentDB.equals("Admins")){

                               loading.dismiss();
                               Intent i = new Intent(login.this, Admin_Home.class);
                               startActivity(i);
                           }
                           else if(parentDB.equals("Users")){

                               Toast.makeText(login.this, "Successful Logging ...", Toast.LENGTH_SHORT).show();
                               loading.dismiss();
                               Intent i = new Intent(login.this, Home.class);
                               prevalent.currentOnlineUser=usersdata;
                               startActivity(i);

                           }
                        } else {
                            loading.dismiss();
                            Toast.makeText(login.this, "Password is incorrect ...", Toast.LENGTH_SHORT).show();
                        }

                    }


                } else {
                    Toast.makeText(login.this, "Account with this " + mPhone + " number doesn't exist", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(login.this, "you need to create new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
