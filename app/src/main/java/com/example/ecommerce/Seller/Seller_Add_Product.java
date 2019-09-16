package com.example.ecommerce.Seller;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Seller_Add_Product extends AppCompatActivity {
    private String Category_name, Pdescription, Pname, Pprice, saveCurrentDate, saveCurrentTime;
    private String categoryName;
    private EditText name, des, price;
    private Button add_product;
    private ProgressDialog loading;

    private ImageView select_image;
    private static final int gallerypick = 1;
    private Uri imageUri;
    private String RandomProductKey, downloadImgUrl, Sname, Sphone, Sid, Saddress, Smail;
    private StorageReference productImgRef;
    private DatabaseReference productRef, sellerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__add__product);

        productImgRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        sellerRef = FirebaseDatabase.getInstance().getReference().child("Sellers");
        categoryName = getIntent().getExtras().get("Category").toString();
        name = findViewById(R.id.product_name);
        loading = new ProgressDialog(this);

        des = findViewById(R.id.product_description);
        price = findViewById(R.id.product_price);
        select_image = findViewById(R.id.select_product_image);
        add_product = findViewById(R.id.add_new_product_btn);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });

        sellerRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Sname = dataSnapshot.child("name").getValue().toString();
                            Sphone = dataSnapshot.child("phone").getValue().toString();
                            Smail = dataSnapshot.child("mail").getValue().toString();
                            Sid = dataSnapshot.child("seller ID").getValue().toString();
                            Saddress = dataSnapshot.child("address").getValue().toString();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void ValidateProductData() {
        Pname = name.getText().toString();
        Pdescription = des.getText().toString();
        Pprice = price.getText().toString();
        if (imageUri == null) {
            Toast.makeText(this, "Product Image is mandatory ....", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write the product name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pdescription)) {
            Toast.makeText(this, "Please write the product description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pprice)) {
            Toast.makeText(this, "Please write the product price", Toast.LENGTH_SHORT).show();
        } else {
            storeProductnformation();
        }

    }

    private void storeProductnformation() {

        loading.setTitle("Add new product");
        loading.setMessage("Please Seller wait while we are Adding the new product.");
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        RandomProductKey = saveCurrentDate + saveCurrentTime;
        final StorageReference filePath = productImgRef.child(imageUri.getLastPathSegment() + RandomProductKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(Seller_Add_Product.this, "" + message, Toast.LENGTH_SHORT).show();

                loading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Seller_Add_Product.this, "Product Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }
                        downloadImgUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            downloadImgUrl = task.getResult().toString();

                            Toast.makeText(Seller_Add_Product.this, "Got the Product Image URL successfully...", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDB();
                        }
                    }
                });
            }
        });

    }

    private void saveProductInfoToDB() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("Pid", RandomProductKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Pdescription);
        productMap.put("image", downloadImgUrl);
        productMap.put("category", categoryName);
        productMap.put("price", Pprice);
        productMap.put("name", Pname);
        productMap.put("product State", "Not Approved");
        productMap.put("seller name", Sname);
        productMap.put("seller address", Saddress);
        productMap.put("seller phone", Sphone);
        productMap.put("seller email", Smail);
        productMap.put("seller id", Sid);

        productRef.child(RandomProductKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(Seller_Add_Product.this, Seller_Home.class);
                    startActivity(i);

                    loading.dismiss();

                    Toast.makeText(Seller_Add_Product.this, "product added successfully...", Toast.LENGTH_SHORT).show();
                } else {
                    loading.dismiss();

                    String msg = task.getException().toString();
                    Toast.makeText(Seller_Add_Product.this, "" + msg, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallerypick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == gallerypick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            select_image.setImageURI(imageUri);

        }
    }
}
