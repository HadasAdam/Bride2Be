package com.example.bride2be;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    static final String TAG = "LoginFragment";
    EditText emailET;
    EditText passwordET;
    Button submitButton;
    Button signUpButton;
    User userWantsToLogIn;
    View view;

    public LoginFragment() { }
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        view = inflater.inflate(R.layout.fragment_login, container,false);
        signUpButton = view.findViewById(R.id.login_frg_signup_button);
        submitButton = view.findViewById(R.id.login_frg_submit_button);
        emailET = view.findViewById(R.id.login_frg_email_et);
        passwordET = view.findViewById(R.id.login_frg_password_et);
        signUpButton.setOnClickListener(v -> onClickSignUpButton());
        submitButton.setOnClickListener(v -> onClickSubmitButton());
        userWantsToLogIn = null;
        Model.instance.logOut();
        return view;
    }

    public void onClickSubmitButton() {
        String emailAddress = emailET.getText().toString();
        String password = passwordET.getText().toString();
        checkLoginCredentials(emailAddress, password);
    }

    private void sendParametersAndNavigateToUserProfile(User user)
    {
        NavDirections action = (NavDirections)LoginFragmentDirections.actionLoginFragment2ToUserProfileFragment2(user.getId());
        Navigation.findNavController(view).navigate(action);
    }

    public void onClickSignUpButton() {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_signUpFragment2);
    }

    private void checkLoginCredentials(String emailAddress, String givenPassword) {
        Model.instance.getAllUsers(users -> {
            boolean found = false;
            for (User user : users) {
                Log.d(TAG, "User : " + user.getEmail());
                if (user != null && user.getEmail().equals(emailAddress) && user.getPasswordHash().equals(GeneralUtils.md5(givenPassword))) {
                    found = true;
                    userWantsToLogIn = user;
                    break;
                }
            }
            if(found){
                Log.d(TAG, "User with email: " + emailAddress + " was found in database.");
                Model.instance.setLoggedInUser(userWantsToLogIn);
                sendParametersAndNavigateToUserProfile(userWantsToLogIn);
            }
            else {
                Log.d(TAG, "User with email: " + emailAddress +
                        " was not found in database or put a wrong password.");

                Context context = view.getContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Wrong Email or Password. Please try to login again");
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
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });
    }
}