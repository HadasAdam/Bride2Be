package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;
import com.google.android.gms.maps.SupportMapFragment;

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
    User loggedInUser;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        UserName = view.findViewById(R.id.UserNameProfileTV);
        UserEmail = view.findViewById(R.id.UserEmailProfileTV);
        UserPhoneNumber = view.findViewById(R.id.UserPhoneNumProfileTV);
        UserAddress = view.findViewById(R.id.UserAddressProfileTV);
        LogoutBtn = view.findViewById(R.id.LogOutProfileBtn);
        EditProfileBtn = view.findViewById(R.id.EditProfileBtn);
        AddNewProductBtn = view.findViewById(R.id.AddNewProduct_btn);
        LogoutBtn.setOnClickListener(v -> UserLogOut());
        EditProfileBtn.setOnClickListener(v -> EditUserProfile());
        AddNewProductBtn.setOnClickListener(v -> addNewProduct());
        loggedInUser = Model.instance.getLoggedInUser();
        if(loggedInUser == null)
        {
            setVisitorMode();
        }

        Button logOutBtn = view.findViewById(R.id.LogOutProfileBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_loginFragment);
            }

        });

        Button editProBtn = view.findViewById(R.id.EditProfileBtn);
        editProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_editProfileFragment);
            }

        });

        Button newProBtn = view.findViewById(R.id.AddNewProduct_btn);
        newProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navBarFragment_to_addNewProductFragment);
            }

        });

        return view;
    }

    private void setVisitorMode()
    {
        LogoutBtn.setVisibility(View.GONE);
        EditProfileBtn.setVisibility(View.GONE);
        AddNewProductBtn.setVisibility(View.GONE);
    }

    private void UserLogOut() {
        Model.instance.logOut();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new LoginFragment());
        fragmentTransaction.commit();
    }

    private void openMapFilters()
    {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, mapFragment);
        fragmentTransaction.commit();
    }

    private void EditUserProfile() {
        if(loggedInUser != null)
        {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new EditProfileFragment());
            fragmentTransaction.commit();
        }
        else
        {
            setVisitorMode();
        }
    }

    private void addNewProduct() { // add new product by the user
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new AddNewProductFragment());
        fragmentTransaction.commit();
    }

}