package com.example.bride2be;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {

    TextView userLocation;
    EditText productName;
    EditText productPrice;
    EditText productDescription;
    Button choosePictureButton;
    Button cancelEditProductButton;
    Button saveEditProductButton;
    Button deleteEditProductButton;
    ImageView ProductImage;
    Product productToEdit;
    Uri imageUri;

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
        productName = view.findViewById(R.id.ProductNameEditProductET);
        productPrice = view.findViewById(R.id.ProductPriceEditProductET);
        userLocation = view.findViewById(R.id.UserLocationEditProductTV);
        productDescription = view.findViewById(R.id.ProductDescriptionEditProductET);
        choosePictureButton = view.findViewById(R.id.ChooseProductImageEditProductBtn);
        ProductImage = view.findViewById(R.id.ProductImageEditProductIV);
        cancelEditProductButton = view.findViewById(R.id.CancelEditProductBtn);
        saveEditProductButton = view.findViewById(R.id.SaveEditProductBtn);
        deleteEditProductButton = view.findViewById(R.id.DeleteEditProductBtn);
        cancelEditProductButton.setOnClickListener(v -> AbortEditProduct());
        saveEditProductButton.setOnClickListener(v -> SaveProductChanges());
        deleteEditProductButton.setOnClickListener(v -> DeleteProduct());

        if(!Model.instance.getLoggedInUser().getId().equals(productToEdit.getUploaderId()))
        {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new LoginFragment());
            fragmentTransaction.commit();
        }

        productName.setText(productToEdit.getTitle());
        productPrice.setText(String.valueOf(productToEdit.getPrice()));
        // TODO: USER LOCATION
        productDescription.setText(productToEdit.getDescription());
        // TODO: call loadImageFromStorage();

        return view;
    }

    private void AbortEditProduct() { // cancel changes and get back to profile
        //cancel

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();


    }

    private void SaveProductChanges() {

        if(isProductValid())
        {
            String picturePath = uploadImageToStorage();
            if(!picturePath.equals(""))
            {
                Product currentProduct = new Product(productName.getText().toString(), productDescription.getText().toString(),
                        Double.valueOf(productPrice.getText().toString()), picturePath, Model.instance.getLoggedInUser().getId());
                Model.instance.updateProduct(currentProduct, new Model.UpdateProductListener() {
                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Updated product: '" + productName.getText().toString() + "' was updated.");

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
                        fragmentTransaction.commit();
                    }
                });
            }
        }
        else {
            Toast.makeText(getContext(), "Product is not valid.", Toast.LENGTH_SHORT);
        }

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();

    }

    private void DeleteProduct() {
        if(productToEdit != null)
        {
            Model.instance.deleteProduct(productToEdit, new Model.DeleteProductListener() {
                @Override
                public void onComplete() {
                    Log.d("TAG", "Product with id: " + productToEdit.getId() + " was deleted.");
                }
            });
        }

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();

    }

    private String uploadImageToStorage()
    {
        String filePath = "";
        if(imageUri != null) {
            filePath = Model.instance.uploadPictureInStorage(getFileExtension(imageUri), imageUri);
        }
        else {
            Toast.makeText(getActivity() ,"No image selected.", Toast.LENGTH_SHORT).show();
        }
        return filePath;
    }

    private void loadImageFromStorage(String path, ImageView imageView)
    {
        Model.instance.loadPictureFromStorage(path, imageView);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private boolean isProductValid() {
        // TODO: add validation to product fields
        return true;
    }

}