package com.example.bride2be;
import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

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
import com.example.bride2be.models.User;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewProductFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final String TAG = "AddNewProductFragment";

    TextView userLocation;
    EditText productName;
    EditText productPrice;
    EditText productDescription;
    Button choosePictureButton;
    Button cancelNewProductButton;
    Button saveNewProductButton;
    ImageView productImage;
    Uri imageUri;
    User loggedInUser = Model.instance.getLoggedInUser();
    View view;

    public AddNewProductFragment() {
    }
    public static AddNewProductFragment newInstance(String param1, String param2) {
        AddNewProductFragment fragment = new AddNewProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_new_product, container, false);
        if(loggedInUser == null)
        {
            Navigation.findNavController(view).navigate(R.id.action_addNewProductFragment_to_loginFragment2);
        }
        productName = view.findViewById(R.id.ProductNameNewProductET);
        productPrice = view.findViewById(R.id.ProductPriceNewProductET);
        userLocation = view.findViewById(R.id.UserLocationNewProductTV);
        productDescription = view.findViewById(R.id.ProductDescriptionNewProductET);
        choosePictureButton = view.findViewById(R.id.ChooseProductImageNewProductBtn);
        productImage = view.findViewById(R.id.ProductImageNewProductIV);
        cancelNewProductButton = view.findViewById(R.id.CancelNewProductBtn);
        saveNewProductButton = view.findViewById(R.id.SaveNewProductBtn);
        cancelNewProductButton.setOnClickListener(v -> AbortNewProduct());
        saveNewProductButton.setOnClickListener(v -> AddNewProduct());

        userLocation.setText(loggedInUser.getCity());

        choosePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFromGallery();
            }
        });

        return view;
    }

    private void AbortNewProduct() {
        Navigation.findNavController(view).navigate(R.id.action_addNewProductFragment_to_userProfileFragment2);
    }

    private void AddNewProduct() {
        saveNewProductButton.setEnabled(false);
        if(isProductValid())
        {
            String picturePath = uploadImageToStorage();
            if(!picturePath.equals(""))
            {
                Product currentProduct = new Product(productName.getText().toString(), productDescription.getText().toString(),
                        Double.valueOf(productPrice.getText().toString()), picturePath, loggedInUser.getId());
                Model.instance.addProduct(currentProduct, new Model.AddProductListener() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Picture of product was saved in path: " + picturePath);
                        Model.instance.addProduct(currentProduct, new Model.AddProductListener() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "New product created: '" + productName.getText().toString());
                            }
                        });
                    }
                });
                saveNewProductButton.setEnabled(true);
                Navigation.findNavController(view).navigate(R.id.action_addNewProductFragment_to_userProfileFragment2);
            }
        }
        else {
            saveNewProductButton.setEnabled(true);
            Toast.makeText(getContext(), "Product is not valid.", Toast.LENGTH_SHORT);
        }
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
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(productImage);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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



    private AlertDialog.Builder getAlertDialogBuilder() {

        {
            Context context = view.getContext();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click ok to exit and try again")
                    .setCancelable(false)
                    .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            return alertDialogBuilder;
        }
    }

    private boolean isProductValid()
    {
        AlertDialog.Builder alertDialogBuilder = getAlertDialogBuilder();

        if (!GeneralUtils.isProductNameValid(productName.getText().toString())){
            Log.d(TAG, "Product must be between 1 and 20 letters.");
            alertDialogBuilder.setTitle("Product must be between 1 and 20 letters.");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }

        if (!GeneralUtils.isProductPriceValid(productPrice.getText().toString())){
            Log.d(TAG, "Product price is not valid, Product Price must be a number between 0 and 999.");
            alertDialogBuilder.setTitle("Product price must be a number between 0 and 999.");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }

        if (imageUri == null){
            Log.d(TAG, "No image was selected.");
            alertDialogBuilder.setTitle("No image was selected.");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }

        return true;
    }
}