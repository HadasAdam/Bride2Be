package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewProductFragment extends Fragment {

    EditText ProductName;
    EditText ProductPrice;
    TextView UserLocation;
    EditText ProductDescription;
    Button ChoosePicture;
    ImageView ProductImage;
    Button CancelNewProduct;
    Button SaveNewProduct;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddNewProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewProductFragment newInstance(String param1, String param2) {
        AddNewProductFragment fragment = new AddNewProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_product, container, false);
        ProductName = view.findViewById(R.id.ProductNameNewProductET);
        ProductPrice = view.findViewById(R.id.ProductPriceNewProductET);
        UserLocation = view.findViewById(R.id.UserLocationNewProductTV);
        ProductDescription = view.findViewById(R.id.ProductDescriptionNewProductET);
        ChoosePicture = view.findViewById(R.id.ChooseProductImageNewProductBtn);
        ProductImage = view.findViewById(R.id.ProductImageNewProductIV);
        CancelNewProduct = view.findViewById(R.id.CancelNewProductBtn);
        SaveNewProduct = view.findViewById(R.id.SaveNewProductBtn);
        CancelNewProduct.setOnClickListener(v -> AbortNewProduct());
        SaveNewProduct.setOnClickListener(v -> AddNewProduct());
        return view;
    }

    private void AbortNewProduct() { // cancel new product and get back to user profile

    }

    private void AddNewProduct() { // save the new product. need to check that user enters all attributes

    }

}