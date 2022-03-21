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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.firestore.DocumentSnapshot;

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
    View view;


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
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        UserName = view.findViewById(R.id.UserNameProfileTV);
        UserEmail = view.findViewById(R.id.UserEmailProfileTV);
        UserPhoneNumber = view.findViewById(R.id.UserPhoneNumProfileTV);
        UserAddress = view.findViewById(R.id.UserAddressProfileTV);
        LogoutBtn = view.findViewById(R.id.LogOutProfileBtn);
        EditProfileBtn = view.findViewById(R.id.EditProfileBtn);
        AddNewProductBtn = view.findViewById(R.id.AddNewProfileBtn);
        LogoutBtn.setOnClickListener(v -> UserLogOut());
        EditProfileBtn.setOnClickListener(v -> EditUserProfile());
        AddNewProductBtn.setOnClickListener(v -> addNewProduct());
        loggedInUser = Model.instance.getLoggedInUser();

        String userId = this.getArguments().getString("userIdToOpenProfile");
        authenticate(userId);

        return view;
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
        UserName.setText(userName);
        UserEmail.setText(user.getEmail());
        UserPhoneNumber.setText(user.getPhoneNumber());
        UserAddress.setText(user.getCity());
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