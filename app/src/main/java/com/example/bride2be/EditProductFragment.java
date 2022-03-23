package com.example.bride2be;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProductFragment extends Fragment {

    private static final String TAG = "EditProductFragment";

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
    View view;

    public EditProductFragment() { }


    public static EditProductFragment newInstance(String param1, String param2) {
        EditProductFragment fragment = new EditProductFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_product, container, false);
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
        String productId = this.getArguments().getString("productIdToOpen");

        if(productId != null)
        {
            Model.instance.getProduct(productId, new Model.GetProductListener() {
                @Override
                public void onComplete(Product product) {
                    productToEdit = product;

                    if (!Model.instance.getLoggedInUser().getId().equals(productToEdit.getUploaderId()))
                    {
                        Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_loginFragment2);
                    }
                    else
                    {
                        Model.instance.getUser(productToEdit.getUploaderId(), new Model.GetUserListener() {
                            @Override
                            public void onComplete(User user) {
                                if(user != null)
                                {
                                    productName.setText(productToEdit.getTitle());
                                    productPrice.setText(String.valueOf(productToEdit.getPrice()));
                                    userLocation.setText(user.getCity());
                                    productDescription.setText(productToEdit.getDescription());
                                    Model.instance.loadPictureFromStorage(product.getPicture(), ProductImage);
                                }
                            }
                        });
                    }
                }
            });
        }
        else
        {
            Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_loginFragment2);
        }

        return view;
    }

    private void AbortEditProduct() {
        Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_userProfileFragment2);
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
                        Log.d(TAG, "Updated product: '" + productName.getText().toString() + "' was updated.");
                        Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_userProfileFragment2);
                    }
                });
            }
        }
        else {
            Toast.makeText(getContext(), "Product is not valid.", Toast.LENGTH_SHORT);
        }
        Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_userProfileFragment2);
    }

    private void DeleteProduct() {
        if(productToEdit != null)
        {
            Model.instance.deleteProduct(productToEdit, new Model.DeleteProductListener() {
                @Override
                public void onComplete() {
                    Log.d(TAG, "Product with id: " + productToEdit.getId() + " was deleted.");
                }
            });
        }

        Navigation.findNavController(view).navigate(R.id.action_editProductFragment_to_userProfileFragment2);
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

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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

}