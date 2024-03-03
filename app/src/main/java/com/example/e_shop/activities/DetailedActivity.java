package com.example.e_shop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_shop.R;
import com.example.e_shop.models.NewProductsModel;
import com.example.e_shop.models.PopularProductModel;
import com.example.e_shop.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailed_img, addItems, removeItems;
    TextView rating;
    TextView name, description, price, quantity;
    Button addToCart, buyNow;

    Toolbar toolbar;

    int totalQuantity = 1;
    int totalPrice = 0;

    //new products
    NewProductsModel newProductsModel = null;

    //popular products
    PopularProductModel popularProductModel = null;

    //showAll products
    ShowAllModel showAllModel = null;
    FirebaseAuth auth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final  Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) obj;
        }else if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailed_img = findViewById(R.id.detailed_img);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.rating);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        //New products
        if (newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailed_img);
            name .setText(newProductsModel.getName());
            rating .setText(newProductsModel.getRating());
            description .setText(newProductsModel.getDescription());
            price .setText(String.valueOf(newProductsModel.getPrice()));
            //name .setText(newProductsModel.getName());

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }


        //Popular products
        if (popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailed_img);
            name .setText(popularProductModel.getName());
            rating .setText(popularProductModel.getRating());
            description .setText(popularProductModel.getDescription());
            price .setText(String.valueOf(popularProductModel.getPrice()));
            //name .setText(newProductsModel.getName());

            totalPrice = popularProductModel.getPrice() * totalQuantity;
        }

        //ShowAll products
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailed_img);
            name .setText(showAllModel.getName());
            rating .setText(showAllModel.getRating());
            description .setText(showAllModel.getDescription());
            price .setText(String.valueOf(showAllModel.getPrice()));
            //name .setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        //buy now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

               if (newProductsModel != null){
                   intent.putExtra("item",newProductsModel);
               }
                if (popularProductModel != null){
                    intent.putExtra("item",popularProductModel);
                }
                if (showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }

               startActivity(intent);
            }
        });


        //Add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel != null){
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductModel != null){
                        totalPrice = popularProductModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 10){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addToCart() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
}