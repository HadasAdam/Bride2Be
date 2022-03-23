package com.example.bride2be;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    static final String TAG = "SignUpFragment";
    View view;
    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText phoneNumberET;
    Spinner citySpinner;
    EditText passwordET;
    EditText streetET;
    Button submitBtn;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailAddressET = view.findViewById(R.id.signUpFrg_emailET);
        phoneNumberET = view.findViewById(R.id.signUpFrg_phoneNumberET);
        citySpinner = view.findViewById(R.id.signUpFrg_cityET);
        firstNameET = view.findViewById(R.id.signUpFrg_firstNameET);
        lastNameET = view.findViewById(R.id.signUpFrg_lastNameET);
        passwordET = view.findViewById(R.id.signUpFrg_passwordET);
        submitBtn = view.findViewById(R.id.signUpFrg_submitBTN);
        streetET = view.findViewById(R.id.signUpFrg_street);
        submitBtn.setOnClickListener(v -> onClickSubmitButton());
        submitBtn.setEnabled(true);
        Model.instance.logOut();
        initializeCitySpinner();
        return view;
    }

    public void onClickSubmitButton() {
        submitBtn.setEnabled(false);
        User newUser = new User(firstNameET.getText().toString(), lastNameET.getText().toString(),
                emailAddressET.getText().toString(), phoneNumberET.getText().toString(), GeneralUtils.md5(passwordET.getText().toString()),
                "Israel", citySpinner.getSelectedItem().toString(), streetET.getText().toString());

        if(checkNewUserInputs(newUser)) {
            checkIfEmailIsAlreadyTaken(newUser);
        }
        else {
            submitBtn.setEnabled(true);
            Log.d("TAG", "Unable to add user with email: " + newUser.getEmail() + " to database.");
        }
    }

    private void checkIfEmailIsAlreadyTaken(User newUser) {
        Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void onComplete(List<User> users) {
                boolean found = false;
                for (User user : users) {
                    if (user != null && user.getEmail().equals(newUser.getEmail())) {
                        found = true;
                    }
                }
                if(found){
                    AlertDialog.Builder alertDialogBuilder = getAlertDialogBuilder();
                    Log.d(TAG, "A user with this email already exists.");
                    alertDialogBuilder.setTitle("A user with this email already exists");
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
                else {
                    Model.instance.addUser(newUser, () -> {});
                    Log.d(TAG, "User with email: " + newUser.getEmail() + " was added to database.");
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment2_to_loginFragment2);
                }
            }
        });
    }

    private AlertDialog.Builder getAlertDialogBuilder()
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

    private boolean checkNewUserInputs(User user) {

        AlertDialog.Builder alertDialogBuilder = getAlertDialogBuilder();

        if (!GeneralUtils.isEmailValid(user.getEmail())){
            Log.d("TAG", "Email address is not valid.");
            alertDialogBuilder.setTitle("Email address is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if (!GeneralUtils.isFirstNameValid(user.getFirstName())){
            Log.d("TAG", "First name is not valid.");
            alertDialogBuilder.setTitle("First name is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if (!GeneralUtils.isLastNameValid(user.getLastName())){
            Log.d("TAG", "Last name is not valid.");
            alertDialogBuilder.setTitle("Last name is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if(!GeneralUtils.isPhoneValid(user.getPhoneNumber())){
            Log.d("TAG", "Phone number is not valid.");
            alertDialogBuilder.setTitle("Phone number is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if(!GeneralUtils.isPasswordValid(user.getPhoneNumber())){
            Log.d("TAG", "Password is not valid.");
            alertDialogBuilder.setTitle("Password is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }

        if(citySpinner.getSelectedItem() == null) {
            Log.d("TAG", "City was not chosen.");
            alertDialogBuilder.setTitle("City was not chosen");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }

        return true;
    }

    private void initializeCitySpinner()
    {
        String[] cities = new String[Model.instance.getCities().size()];

        for(int i = 0; i < Model.instance.getCities().size(); i++)
        {
            cities[i] = Model.instance.getCities().get(i).getName();
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, cities);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(dataAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}