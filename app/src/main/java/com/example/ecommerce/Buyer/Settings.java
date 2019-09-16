package com.example.ecommerce.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {
    private CircleImageView image;
    private EditText name, address, phone;
    private TextView changeImage, close, save;
    private Uri imageUri;
    private String url = "", checker = "";
    private StorageReference storageReference;
    private StorageTask uploadTask;
    private Button securityQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        setContentView(R.layout.activity_settings);
        image = findViewById(R.id.settings_profile_image);
        name = findViewById(R.id.settings_full_name);
        phone = findViewById(R.id.settings_phone_number);
        address = findViewById(R.id.settings_address);
        changeImage = findViewById(R.id.profile_image_change_btn);
        close = findViewById(R.id.close_settings_btn);
        securityQuestion = findViewById(R.id.security_question_button);
        securityQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, resetPass.class);
                intent.putExtra("check", "Settings");
                startActivity(intent);
            }
        });
        save = findViewById(R.id.update_account_settings_btn);

        userInfoDisplay(image, name, address, phone);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    userInfoSaved();

                } else {
                    updateOnlyUserInfo();


                }
            }
        });
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri).setAspectRatio(1, 1)
                        .start(Settings.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            image.setImageURI(imageUri);

        } else {
            Toast.makeText(this, "Error , try again ...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.this, Settings.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", name.getText().toString());
        userMap.put("address", address.getText().toString());
        userMap.put("phoneOrder", phone.getText().toString());
        ref.child(prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(Settings.this, Home.class));
        Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(this, "Name is mandatory ...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(this, "Address is mandatory ...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(this, "Phone is mandatory ...", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }

    }

    private void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileRef = storageReference
                    .child(prevalent.currentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                url = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap.put("name", name.getText().toString());
                                userMap.put("address", address.getText().toString());
                                userMap.put("phoneOrder", phone.getText().toString());
                                userMap.put("image", url);
                                ref.child(prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                                progressDialog.dismiss();

                                startActivity(new Intent(Settings.this, Home.class));
                                Toast.makeText(Settings.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Settings.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }


    private void userInfoDisplay(final CircleImageView image, final EditText name, final EditText address, final EditText phone) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.currentOnlineUser.getPhone());
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("image").exists()) {
                        String Image = dataSnapshot.child("image").getValue().toString();
                        String Name = dataSnapshot.child("name").getValue().toString();
                        String Address = dataSnapshot.child("address").getValue().toString();
                        String Phone = dataSnapshot.child("phone").getValue().toString();
                        Picasso.get().load(Image).into(image);
                        name.setText(Name);
                        address.setText(Address);
                        phone.setText(Phone);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
