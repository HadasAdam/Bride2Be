package com.example.bride2be;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Building Fragments:
        ProductsListFragment productsListFragment = new ProductsListFragment();
        LoginFragment loginFragment = new LoginFragment();
        AddNewProductFragment addNewProductFragment = new AddNewProductFragment();
        EditProductFragment editProductFragment = new EditProductFragment();
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        SignUpFragment signUpFragment = new SignUpFragment();
        WishlistFragment wishlistFragment = new WishlistFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainactivity_fragment_container, loginFragment);
        transaction.commit();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://bride2be-database-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("users");
        DatabaseReference usersx = myRef.child("x");
        usersx.child("name").setValue("liam");
        usersx.child("family").setValue("golan");
    }
}