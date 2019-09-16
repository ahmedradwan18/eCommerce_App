package com.example.ecommerce.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Buyer.MainActivity;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class SellerRegisteration extends AppCompatActivity {
    private Button hasAccount, register;
    private EditText name, phone, address, password, mail;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registeration);
        hasAccount = findViewById(R.id.seller_have_Account);
        register = findViewById(R.id.sellerRegisterBTN);
        name = findViewById(R.id.seller_name);
        phone = findViewById(R.id.seller_phone);
        loading = new ProgressDialog(this);

        address = findViewById(R.id.seller_address);
        password = findViewById(R.id.seller_password);
        mail = findViewById(R.id.seller_email);
        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    name.setError("enter your name");

                }if(phone.getText().toString().isEmpty()){
                    phone.setError("enter your phone");

                }if(mail.getText().toString().isEmpty()){
                    mail.setError("enter your mail");

                }

                if (password.getText().toString().length() <= 6) {
                    password.setError("password is too short");

                }
                if(address.getText().toString().isEmpty()){
                    address.setError("enter your address");

                }


                else
                CreateNewUser();

            }
        });
        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerRegisteration.this, SellerLogin.class);
                startActivity(intent);
            }
        });
    }


    private void CreateNewUser() {



        mAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(SellerRegisteration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SellerRegisteration.this.getApplicationContext(), "This Email already exists", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                return;
                            }
                        } else {


                                loading.setTitle("Creating Seller Account..");
                                loading.setMessage("Please wait...");
                                loading.setCanceledOnTouchOutside(false);
                                loading.show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();
                                //*************************************************************
                                String userID = user.getUid();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("seller ID", userID);
                                map.put("name", name.getText().toString());
                                map.put("phone", phone.getText().toString());
                                map.put("mail", mail.getText().toString());
                                map.put("password", password.getText().toString());
                                map.put("address", address.getText().toString());


                                rootRef.child("Sellers").child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task1) {
                                        Toast.makeText(SellerRegisteration.this, "Account Created Successfully..", Toast.LENGTH_LONG).show();
                                        loading.dismiss();
                                        Intent intent = new Intent(SellerRegisteration.this, SellerLogin.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        ClearTools();


                                    }
                                });

                        }
                    }
                });


    }

    private void ClearTools() {

        mail.setText("");
        password.setText("");
        phone.setText("");
        name.setText("");
        address.setText("");
    }


    private void registerSeller() {
        final String name2 = name.getText().toString();
        final String phone2 = phone.getText().toString();
        final String pass2 = password.getText().toString();
        final String mail2 = mail.getText().toString();
        final String address2 = address.getText().toString();

        if (!name2.equals("") && !phone2.equals("") && !pass2.equals("") && !mail2.equals("") && !address2.equals("")) {
            loading.setTitle("Creating Seller Account..");
            loading.setMessage("Please wait...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            mAuth.createUserWithEmailAndPassword(mail2, pass2)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SellerRegisteration.this, "this Email already exist...", Toast.LENGTH_SHORT).show();
                            } else {

                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();
                                FirebaseUser user = mAuth.getCurrentUser();

                                String userID = user.getUid();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("seller ID", userID);
                                map.put("name", name2);
                                map.put("phone", phone2);
                                map.put("mail", mail2);
                                map.put("password", pass2);
                                map.put("address", address2);

                                rootRef.child("Sellers").child(userID).updateChildren(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                loading.dismiss();
                                                Toast.makeText(SellerRegisteration.this, "you are registered successfully ..", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SellerRegisteration.this, Seller_Home.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            }
                                        });

                            }


                        }
                    });

        } else {
            Toast.makeText(this, "Please complete the data", Toast.LENGTH_SHORT).show();
        }
    }
}
