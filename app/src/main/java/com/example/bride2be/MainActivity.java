package com.example.bride2be;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    Button mapButton;

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
        MapsActivity mapsActivity = new MapsActivity();
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        mapButton = findViewById(R.id.button);
        mapButton.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainactivity_fragment_container, signUpFragment);
        transaction.commit();
    }

    public void openMapActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}