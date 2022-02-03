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
import android.widget.Spinner;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText phoneNumberET;
    Spinner citySpinner;
    EditText passwordET;
    Button submitBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailAddressET = view.findViewById(R.id.signUpFrg_emailET);
        phoneNumberET = view.findViewById(R.id.signUpFrg_phoneNumberET);
        citySpinner = view.findViewById(R.id.signUpFrg_cityET);
        firstNameET = view.findViewById(R.id.signUpFrg_firstNameET);
        lastNameET = view.findViewById(R.id.signUpFrg_lastNameET);
        passwordET = view.findViewById(R.id.signUpFrg_passwordET);
        submitBtn = view.findViewById(R.id.signUpFrg_submitBTN);
        submitBtn.setOnClickListener(v -> onClickSubmitButton());
        submitBtn.setEnabled(true);
        return view;
    }

    public void onClickSubmitButton()
    {
        submitBtn.setEnabled(false);
        int id = Model.instance.getAllUsers().size();
        User user = new User(firstNameET.toString(), lastNameET.toString(),
                emailAddressET.toString(), phoneNumberET.toString(), GeneralUtils.md5(passwordET.toString()),
                "Israel", citySpinner.getSelectedItem().toString(), "none");
        user.setId(""+id);
        if(checkNewUserInputs(user))
        {
            Model.instance.addUser(user, new Model.AddUserListener(){
                @Override
                public void onComplete() {
                }
            });
            Log.d("TAG", "User with email: " + user.getEmail() + " was added to database.");
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
            fragmentTransaction.commit();
        }
        else
        {
            Log.d("TAG", "Unable to add user with email: " + user.getEmail() + " to database.");
        }
    }

    private boolean checkNewUserInputs(User user)
    {
        if (GeneralUtils.findUserByEmail(Model.instance.getAllUsers(), user.getEmail()) != null)
            return false;
        if (GeneralUtils.isEmailValid(user.getEmail()))
            return false;
        if (GeneralUtils.isFirstNameValid(user.getFirstName()))
            return false;
        if (GeneralUtils.isLastNameValid(user.getLastName()))
            return false;
        if(GeneralUtils.isPhoneValid(user.getPhoneNumber()))
            return false;
        if(GeneralUtils.isPasswordValid(passwordET.toString()))
            return false;
        return true;
    }
}