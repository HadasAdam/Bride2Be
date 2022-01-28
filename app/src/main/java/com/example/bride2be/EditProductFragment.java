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
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {

    EditText ProductName;
    EditText ProductPrice;
    TextView UserLocation;
    EditText ProductDescription;
    Button ChoosePicture;
    ImageView ProductImage;
    Button CancelEditProduct;
    Button SaveEditProduct;
    Button DeleteEditProduct;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProductFragment newInstance(String param1, String param2) {
        EditProductFragment fragment = new EditProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        ProductName = view.findViewById(R.id.ProductNameEditProductET);
        ProductPrice = view.findViewById(R.id.ProductPriceEditProductET);
        UserLocation = view.findViewById(R.id.UserLocationEditProductTV);
        ProductDescription = view.findViewById(R.id.ProductDescriptionEditProductET);
        ChoosePicture = view.findViewById(R.id.ChooseProductImageEditProductBtn);
        ProductImage = view.findViewById(R.id.ProductImageEditProductIV);
        CancelEditProduct = view.findViewById(R.id.CancelEditProductBtn);
        SaveEditProduct = view.findViewById(R.id.SaveEditProductBtn);
        DeleteEditProduct = view.findViewById(R.id.DeleteEditProductBtn);
        CancelEditProduct.setOnClickListener(v -> AbortEditProduct());
        SaveEditProduct.setOnClickListener(v -> SaveProductChanges());
        DeleteEditProduct.setOnClickListener(v -> DeleteProduct());
        return view;
    }

    private void AbortEditProduct() { // cancel changes and get back to products view

    }

    private void SaveProductChanges() { // save changes in the product and get back to products view

    }

    private void DeleteProduct() { // delete the product and get back to products view

    }

}