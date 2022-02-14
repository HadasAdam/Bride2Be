package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NavGraphFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_graph, container, false);
        Button loginBtn = view.findViewById(R.id.nav_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_loginFragment);
            }

        });

        Button editProfile = view.findViewById(R.id.nav_edit_profile);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_editProfileFragment);
            }

        });

        Button signUp = view.findViewById(R.id.nav_signup);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_signUpFragment);
            }

        });

        Button proList = view.findViewById(R.id.nav_pro_list);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_productsListFragment);
            }

        });

        Button editProd = view.findViewById(R.id.nav_edit_pro);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_editProductFragment);
            }

        });

        Button proDe = view.findViewById(R.id.nav_pro_de);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_productDetailsFragment);
            }

        });


        Button addPro = view.findViewById(R.id.nav_add_pro);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_addNewProductFragment);
            }

        });

        Button userPro = view.findViewById(R.id.nav_user_pro);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_userProfileFragment);
            }

        });


        return view;
    }
}