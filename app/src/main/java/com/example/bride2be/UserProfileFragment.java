package com.example.bride2be;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bride2be.adapters.ProductAdapter;
import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;
import com.example.bride2be.models.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    TextView UserName;
    TextView UserEmail;
    TextView UserPhoneNumber;
    TextView UserAddress;
    Button LogoutBtn;
    Button EditProfileBtn;
    Button AddNewProductBtn;
    Button searchBtn;
    Button mapBtn;
    User loggedInUser = Model.instance.getLoggedInUser();
    ArrayList<Product> userProducts;
    View view;
    ProductAdapter adapter;

    public UserProfileFragment() { }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        UserName = view.findViewById(R.id.UserNameProfileTV);
        UserEmail = view.findViewById(R.id.UserEmailProfileTV);
        UserPhoneNumber = view.findViewById(R.id.UserPhoneNumProfileTV);
        UserAddress = view.findViewById(R.id.UserAddressProfileTV);
        LogoutBtn = view.findViewById(R.id.LogOutProfileBtn);
        EditProfileBtn = view.findViewById(R.id.EditProfileBtn);
        AddNewProductBtn = view.findViewById(R.id.AddNewProfileBtn);
        searchBtn = view.findViewById(R.id.profile_search);
        mapBtn = view.findViewById(R.id.profile_map);
        LogoutBtn.setOnClickListener(v -> UserLogOut());
        EditProfileBtn.setOnClickListener(v -> EditUserProfile());
        AddNewProductBtn.setOnClickListener(v -> addNewProduct());
        loggedInUser = Model.instance.getLoggedInUser();
        searchBtn.setOnClickListener(v -> navigateToProductList());
        mapBtn.setOnClickListener(v -> navigateToMap());

        RecyclerView recyclerView = view.findViewById(R.id.userProfile_productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ProductAdapter(getLayoutInflater());
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(linearLayoutManager);

        String userId = this.getArguments().getString("userIdToOpenProfile");

        authenticate(userId);
        Model.instance.getProductsByUserId(loggedInUser.getId(), new Model.GetProductsByUserIdListener() {
            @Override
            public void onComplete(List<Product> products) {
                if(products != null)
                {
                    adapter.setData(products);
                    recyclerView.setAdapter(adapter);
                    userProducts = new ArrayList<Product>(products);
                    adapter.setOnClickListener(new ProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            if ((userId != null && userId.equals(loggedInUser.getId())) || (userId == null && loggedInUser != null))
                            {
                                String productId = userProducts.get(position).getId();
                                NavDirections action = (NavDirections)UserProfileFragmentDirections.actionUserProfileFragment2ToEditProductFragment(productId);
                                Navigation.findNavController(view).navigate(action);
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    private void navigateToProductList()
    {
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment2_to_productsListFragment);
    }

    private void navigateToMap()
    {
        Intent i = new Intent(getActivity(), MapsActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    private void authenticate(String userId){
        if (userId != null)
        {
            if(loggedInUser == null || !loggedInUser.getId().equals(userId))
            {
                Model.instance.getUser(userId, new Model.GetUserListener() {
                    @Override
                    public void onComplete(User user) {
                        initializeFields(user);

                    }
                });
                setVisitorMode();
            }

            if(loggedInUser.getId().equals(userId))
            {
                initializeFields(loggedInUser);
            }
        }

        else
        {
            if(loggedInUser != null)
            {
                initializeFields(loggedInUser);
            }
        }
    }

    private void initializeFields(User user)
    {
        String userName = user.getFirstName() + " " + user.getLastName();
        String userAddress = user.getCity() + ", " + user.getStreet();
        UserName.setText(userName);
        UserEmail.setText(user.getEmail());
        UserPhoneNumber.setText(user.getPhoneNumber());
        UserAddress.setText(userAddress);
    }

    private void setVisitorMode()
    {
        LogoutBtn.setVisibility(View.GONE);
        EditProfileBtn.setVisibility(View.GONE);
        AddNewProductBtn.setVisibility(View.GONE);
    }

    private void UserLogOut() {
        Model.instance.logOut();
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment2_to_loginFragment2);
    }

    private void EditUserProfile() {
        if(loggedInUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_userProfileFragment2_to_editProfileFragment);
        }
        else {
            setVisitorMode();
        }
    }

    private void addNewProduct() {
        Navigation.findNavController(view).navigate(R.id.action_userProfileFragment2_to_addNewProductFragment);
    }

}