package com.example.bride2be;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewProductFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    Uri imageUri;
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
        if(Model.instance.getLoggedInUser() == null)
        {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new LoginFragment());
            fragmentTransaction.commit();
        }
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

        ChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromGallery();
            }
        });
        return view;
    }

    private void AbortNewProduct() {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();

    }

    private void AddNewProduct() {

        String picturePath = Model.instance.savePictureInStorage(ProductImage.getDrawingCache(), Model.instance.getLoggedInUser().getId());

        Product currentProduct = new Product(ProductName.getText().toString(), ProductDescription.getText().toString(),
                Double.valueOf(ProductPrice.getText().toString()), picturePath, Model.instance.getLoggedInUser().getId());


        Model.instance.addProduct(currentProduct, new Model.AddProductListener() {
            @Override
            public void onComplete() {
                Log.d("TAG", "New product: '" + ProductName.getText().toString() + "' was saved with picture in path: " + picturePath);
            }
        });

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();
    }

    private void uploadImageFromGallery()
    {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select a picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                ProductImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}