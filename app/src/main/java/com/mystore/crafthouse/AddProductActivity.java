package com.mystore.crafthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    EditText title, description,quantity,price;
    TextView category;
    Button addProduct;
    ImageButton backBtn;
    ImageView productImage;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initializeViews();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }//END OF ONCREATE()

    private void showImagePickDialog() {
        String[] options = {"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //camera click
                            if (checkCameraPermissions()){
                                //camera permissions allowed
                                pickFromCamera();
                                }
                            else {
                                //NOT ALLOWED,REQUEST
                                requestCameraPermission();
                            }
                            }
                        else {
                            //gallery clicked
                            if (checkStoragePermissions()){
                                //storage permission allowed
                                pickFromGallery();
                            }else{
                                //not allowed, request
                                requestStoragePermission();
                            }
                        }
                    }
                }).show();
    }

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("Image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }//END OF PICKFROMGALLERY()

    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Image_Description");

         image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermissions(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }

    private String productTitle,productDescription,productCategory,productQuantity,originalPrice;


    private void inputData() {

        productTitle = title.getText().toString().trim();
        productDescription = description.getText().toString().trim();
        productCategory = category.getText().toString().trim();
        productQuantity = quantity.getText().toString().trim();
        originalPrice = price.getText().toString().trim();

        if (TextUtils.isEmpty(productTitle)){
            Toast.makeText(this,"Title is required",Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(productDescription)){
            Toast.makeText(this,"Description is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(productCategory)){
            Toast.makeText(this,"Category is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(productQuantity)){
            Toast.makeText(this,"Quantity is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(originalPrice)){
            Toast.makeText(this,"Price is required",Toast.LENGTH_SHORT).show();
            return;
        }

        AddProduct();

    }//END OF INPUTDATA()

    private void AddProduct() {

        CollectionReference reference = firebaseFirestore.collection("USERS");
        FirebaseUser documentReference = firebaseAuth.getCurrentUser();
        String sellerEmail = documentReference.getEmail();
        String sellerId = documentReference.getUid();

        String timestamp = ""+System.currentTimeMillis();
        HashMap<String,Object> productData = new HashMap<>();
        productData.put("PId",""+timestamp);
        productData.put("SId",""+sellerId);
        productData.put("SEmail",""+sellerEmail);
        productData.put("PTitle",""+productTitle);
        productData.put("PDescription",""+productDescription);
        productData.put("PCategory",""+productCategory);
        productData.put("PQuantity",""+productQuantity);
        productData.put("Price",""+originalPrice);
        productData.put("Timestamp",""+timestamp);



        firebaseFirestore.collection("PRODUCTS")
                .add(productData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(AddProductActivity.this,"Product Added..",Toast.LENGTH_SHORT).show();
                    }
                });


    }//END OF ADDPRODUCT()

    private void categoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Product Category").setItems(Constants.productCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cat = Constants.productCategories[which];
                category.setText(cat);
            }
        }).show();

    }//END OF CATEGORYDIALOG()

    private void initializeViews() {
        productImage = findViewById(R.id.cartID);
        title = findViewById(R.id.title);
        description = findViewById(R.id.descriptionEt);
        category = findViewById(R.id.categoryTv);
        quantity = findViewById(R.id.quantityEt);
        price = findViewById(R.id.priceEt);
        backBtn = findViewById(R.id.backBtn);
        addProduct = findViewById(R.id.addBtn);
    }//END OF INTIALIZEVIEWS



}//END OF MAIN()

