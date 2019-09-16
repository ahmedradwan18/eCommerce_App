package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class resetPass extends AppCompatActivity {
    private String check = "";
    private EditText phone, q1, q2;
    private Button verify;
    private TextView pageTitle, questionsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        phone = findViewById(R.id.find_phone_number);
        q1 = findViewById(R.id.question1);
        q2 = findViewById(R.id.question2);
        questionsTitle = findViewById(R.id.title_questions);
        pageTitle = findViewById(R.id.page_title);
        verify = findViewById(R.id.verifyBTN);

        check = getIntent().getStringExtra("check");

    }

    private void setAnswers() {
        String question1 = q1.getText().toString().toLowerCase();
        String question2 = q2.getText().toString().toLowerCase();

        if (question1.equals("")) {
            Toast.makeText(resetPass.this, "please answer first question..", Toast.LENGTH_SHORT).show();
        } else if (question2.equals("")) {
            Toast.makeText(resetPass.this, "please answer second question..", Toast.LENGTH_SHORT).show();
        } else {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", question1);
            userdataMap.put("answer2", question2);

            ref.child("Security Questions").updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(resetPass.this, "Set Successfully..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(resetPass.this, Home.class);
                                startActivity(intent);

                            }
                        }
                    });


        }


    }

    private void displayPreviousAnswers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(prevalent.currentOnlineUser.getPhone());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String q11 = dataSnapshot.child("answer1").getValue().toString();
                    String q22 = dataSnapshot.child("answer2").getValue().toString();
                    q1.setText(q11);
                    q2.setText(q22);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void VerifyUser() {


        String Phone = phone.getText().toString();
        final String question1 = q1.getText().toString().toLowerCase();
        final String question2 = q2.getText().toString().toLowerCase();

        if (!Phone.equals("") && !question1.equals("") && !question2.equals("")) {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String mPhone = dataSnapshot.child("phone").getValue().toString();

                        if (dataSnapshot.hasChild("Security Questions")) {
                            String q11 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String q22 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                            if (!question1.equals(q11)) {
                                Toast.makeText(resetPass.this, "Your 1st Answer is wrong", Toast.LENGTH_SHORT).show();
                            } else if (!question2.equals(q22)) {
                                Toast.makeText(resetPass.this, "Your 2nd Answer is wrong", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(resetPass.this);
                                builder.setTitle("New Password");
                                final EditText pass = new EditText(resetPass.this);
                                pass.setHint("Write new password here..");
                                builder.setView(pass);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (!pass.getText().toString().equals("")) {
                                            ref.child("password").setValue(pass.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(resetPass.this, "Password changed successfully..", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(resetPass.this, login.class);
                                                        startActivity(intent);
                                                            }
                                                        }
                                                    });

                                        }

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.cancel();

                                    }
                                });
                                builder.show();
                            }

                        } else {
                            Toast.makeText(resetPass.this, "you have not set the security questions ...", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(resetPass.this, "this phone number is not exist ...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(this, "please complete the form...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        phone.setVisibility(View.GONE);

        if (check != null && check.equals("Login")) {
            phone.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    VerifyUser();
                }
            });

        } else if (check != null && check.equals("Settings")) {

            pageTitle.setText("Set Questions ");
            questionsTitle.setText("Please Set the Answers for the following security Questions");
            verify.setText("SET");
            displayPreviousAnswers();


            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setAnswers();

                }
            });


        }
    }
}
