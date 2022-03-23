package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;
import com.example.bride2be.models.User;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    private final String TAG = "ProductDetailsFragment";

    Button moveToMyProfileBtn;
    View view;
    ImageView productImageIV;
    TextView productTitleTV;
    TextView productDescriptionTV;
    TextView userCityTV;
    TextView userPhoneTV;
    TextView userEmailTV;


    public ProductDetailsFragment() { }


    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product_details, container, false);
        productImageIV = view.findViewById(R.id.ProductDetails_ProductDetailsIV);
        productTitleTV = view.findViewById(R.id.ProductDetails_ProductNameDetailsET);
        productDescriptionTV = view.findViewById(R.id.ProductDetails_ProductDescriptionDetailsET);
        userCityTV = view.findViewById(R.id.ProductDetails_UserLocationDetailsTV);
        userEmailTV = view.findViewById(R.id.ProductDetails_ProductDetailsEmailAdrress);
        userPhoneTV = view.findViewById(R.id.ProductDetails_ProductDetailsPhone);

        moveToMyProfileBtn = view.findViewById(R.id.ProductDetails_ProductDetailsMyProfileButton);
        String productId = this.getArguments().getString("productIdToOpen");
        if(productId != null)
        {
            Model.instance.getProduct(productId, new Model.GetProductListener() {
                @Override
                public void onComplete(Product product) {
                    if (product != null && product.getPicture() != null) {
                        Model.instance.loadPictureFromStorage(product.getPicture(), productImageIV);
                        initializeProductFields(product);
                        moveToMyProfileBtn.setOnClickListener(v -> sendParametersAndNavigateToUserProfile(product));
                    }
                    if(product != null && product.getUploaderId() != null)
                    {
                        Model.instance.getUser(product.getUploaderId(), new Model.GetUserListener() {
                            @Override
                            public void onComplete(User user) {
                                if(user != null)
                                {
                                    initializeUserFields(user);
                                }
                            }
                        });
                    }
                    else {
                        Navigation.findNavController(view).navigate(R.id.action_productDetailsFragment_to_productsListFragment);
                    }
                }
            });
        }
        else{
            Navigation.findNavController(view).navigate(R.id.action_productDetailsFragment_to_productsListFragment);
        }


        return view;
    }

    private void sendParametersAndNavigateToUserProfile(Product product)
    {

        Model.instance.getProduct(product.getId(), new Model.GetProductListener() {
            @Override
            public void onComplete(Product product) {
                NavDirections action = (NavDirections)ProductDetailsFragmentDirections
                        .actionProductDetailsFragmentToUserProfileFragment2(product.getUploaderId());
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    private void initializeProductFields(Product product)
    {
        productTitleTV.setText(product.getTitle());
        productDescriptionTV.setText(product.getDescription());
    }

    private void initializeUserFields(User user)
    {
        userCityTV.setText(user.getCity());
        userEmailTV.setText(user.getEmail());
        userPhoneTV.setText(user.getPhoneNumber());
    }
}