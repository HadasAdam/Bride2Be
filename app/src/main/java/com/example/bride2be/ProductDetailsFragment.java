package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;
import com.example.bride2be.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    Button moveToMyProfileBtn;
    Product product;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    //
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        moveToMyProfileBtn = view.findViewById(R.id.ProductDetailsMyProfileButton);
        moveToMyProfileBtn.setOnClickListener(v -> sendParametersAndNavigateToUserProfile());
        return  view;
    }

    private void sendParametersAndNavigateToUserProfile()
    {
        Bundle bundle = new Bundle();

        Model.instance.getProduct(product.getId(), new Model.GetProductListener() {
            @Override
            public void onComplete(Product product) {
                bundle.putString("userToOpenProfile", product.getUploaderId());

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                UserProfileFragment userProfileFragment = new UserProfileFragment();

                userProfileFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.mainactivity_fragment_container, userProfileFragment);
                fragmentTransaction.commit();
            }
        });

    }
}