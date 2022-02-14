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

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    EditText emailET;
    EditText passwordET;
    Button submitButton;
    Button signUpButton;
    User userWantsToLogIn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container,false);
        signUpButton = view.findViewById(R.id.login_frg_signup_button);
        submitButton = view.findViewById(R.id.nav_edit_pro);
        emailET = view.findViewById(R.id.login_frg_email_et);
        passwordET = view.findViewById(R.id.login_frg_password_et);
        signUpButton.setOnClickListener(v -> onSignUpButton());
        submitButton.setOnClickListener(v -> onClickSubmitButton());
        userWantsToLogIn = null;
        Model.instance.logOut();
        return view;
    }

    public void onClickSubmitButton()
    {
        String emailAddress = emailET.getText().toString();
        String password = passwordET.getText().toString();
        if (checkLoginCredentials(emailAddress, password))
        {
            Model.instance.setLoggedInUser(userWantsToLogIn);
            Log.d("TAG", "User with email: " + emailAddress + " was found in database.");
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
            fragmentTransaction.commit();
        }
        else
        {
            Log.d("TAG", "User with email: " + emailAddress +
                    " was not found in database or put a wrong password.");
        }
    }

    public void onSignUpButton()
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainactivity_fragment_container, new SignUpFragment());
        fragmentTransaction.commit();
    }

    private boolean checkLoginCredentials(String emailAddress, String givenPassword)
    {
        userWantsToLogIn = GeneralUtils.findUserByEmail(Model.instance.getAllUsers(), emailAddress);
        return (userWantsToLogIn != null && userWantsToLogIn.getPasswordHash().equals(GeneralUtils.md5(givenPassword)));
    }
}