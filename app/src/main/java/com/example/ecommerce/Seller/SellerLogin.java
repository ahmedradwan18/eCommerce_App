package com.example.ecommerce.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class SellerLogin extends AppCompatActivity {
    private Button login;
    private EditText mail, pass;
    private ProgressDialog loading;
    private FirebaseAuth mAuth;
private DatabaseReference UsersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        login = findViewById(R.id.sellerLoginBTN);
        mail = findViewById(R.id.seller_login_email);
        pass = findViewById(R.id.seller_login_password);
        loading = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference("Sellers");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckDataAndLogin();
            }
        });
    }

    private void loginSeller() {

        final String mail2 = mail.getText().toString();
        final String pass2 = pass.getText().toString();
        if (!pass2.equals("") && !mail2.equals("")) {
            loading.setTitle("Logging into your account..");
            loading.setMessage("Please wait...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            mAuth.signInWithEmailAndPassword(mail2,pass2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Intent intent = new Intent(SellerLogin.this, Seller_Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }



                }
            });

        } else {
            Toast.makeText(this, "please complete your data...", Toast.LENGTH_SHORT).show();
        }


    }


    private void CheckDataAndLogin() {
        String str_email = mail.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        if (str_email.isEmpty()){
            mail.setError("Insert Your Mail Please");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            mail.setError("Invalid Email !");
            mail.requestFocus();
            return;
        }
        if (str_pass.isEmpty()){
            pass.setError("Insert Your Password Please");
            pass.requestFocus();
            return;
        }
        if (str_pass.length() < 6){
            pass.setError("Required 6 char at least");
            pass.requestFocus();
            return;
        }
        //-------------------------------------------
        Query query = UsersRef.orderByChild("mail").equalTo(mail.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    LoginWithUser(mail,pass);
                }else{
                    Toast.makeText(getApplicationContext(),"This Email doesn't exist !",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //-------------------------------------------
    }

    private void LoginWithUser(final EditText mail, final EditText password) {
        loading.setTitle("Logging into your account..");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        mAuth.signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(SellerLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SellerLogin.this, "ay 7aga", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SellerLogin.this, Seller_Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    SellerLogin.this.startActivity(intent);
                                    SellerLogin.this.finish();
                                    loading.dismiss();
                                    mail.setText("");
                                    password.setText("");

                                }

                        else {
                            loading.dismiss();
                            Toast.makeText(SellerLogin.this, "Authentication failed because " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


