package com.example.bride2be;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    TextView UserFirstName;
    EditText UserEmail;
    EditText UserPhoneNumber;
    EditText UserCity; // TODO: SHOULD BE A SPINNER, NOT AN EDITTEXT
    EditText UserStreet;
    Button CancelEditProfile;
    Button SaveEditProfile;
    User userToEdit = Model.instance.getLoggedInUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        UserFirstName = view.findViewById(R.id.UserNameEditProfileTV);
        UserEmail = view.findViewById(R.id.EmailEditProfileET); // TODO: FIRST NAME AND LAST NAME SEPARATELY!
        UserPhoneNumber = view.findViewById(R.id.PhoneNumEditProfileET);
        UserCity = view.findViewById(R.id.CityEditProfileET);
        UserStreet = view.findViewById(R.id.StreetEditProfileET);
        CancelEditProfile = view.findViewById(R.id.CancelEditProfileBtn);
        SaveEditProfile = view.findViewById(R.id.SaveEditProfileBtn);
        CancelEditProfile.setOnClickListener(v -> AbortProfileEdit());
        SaveEditProfile.setOnClickListener(v -> SaveChangesInProfile());
        if(userToEdit == null)
        {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new LoginFragment());
            fragmentTransaction.commit();
        }

        UserFirstName.setText(userToEdit.getFirstName());
        // TODO: LAST NAME ???
        UserEmail.setText(userToEdit.getEmail());
        UserPhoneNumber.setText(userToEdit.getPhoneNumber());
        // TODO: USER CITY ???
        UserStreet.setText(userToEdit.getStreet());

        return view;
    }

    private void AbortProfileEdit() { // get back to user profile
        //clear changes

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();
    }

    private void SaveChangesInProfile() {

        userToEdit.setFirstName(UserFirstName.getText().toString());
        // TODO: userToEdit.setLastName();
        userToEdit.setEmail(UserEmail.getText().toString());
        userToEdit.setPhoneNumber(UserPhoneNumber.getText().toString());
        // TODO: userToEdit.setCity();
        userToEdit.setStreet(UserStreet.getText().toString());

        Model.instance.updateUser(userToEdit, new Model.UpdateUserListener() {
            @Override
            public void onComplete() {
                Log.d("TAG","User with id: " + userToEdit.getId() + " was updated.");
            }
        });

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
        fragmentTransaction.commit();
    }

}