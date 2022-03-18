package com.example.bride2be;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bride2be.models.City;
import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    View view;
    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText phoneNumberET;
    Spinner citySpinner;
    EditText passwordET;
    Button submitBtn;
    ArrayList<String> problemsInUserInfo = new ArrayList<>();

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
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailAddressET = view.findViewById(R.id.signUpFrg_emailET);
        phoneNumberET = view.findViewById(R.id.signUpFrg_phoneNumberET);
        citySpinner = view.findViewById(R.id.signUpFrg_cityET);
        firstNameET = view.findViewById(R.id.signUpFrg_firstNameET);
        lastNameET = view.findViewById(R.id.signUpFrg_lastNameET);
        passwordET = view.findViewById(R.id.signUpFrg_passwordET);
        submitBtn = view.findViewById(R.id.signUpFrg_submitBTN);
        submitBtn.setOnClickListener(v -> onClickSubmitButton());
        submitBtn.setEnabled(true);
        Model.instance.logOut();
        initializeCitySpinner();
        return view;
    }

    public void onClickSubmitButton()
    {
        submitBtn.setEnabled(false);
        int id = Model.instance.getAllUsers().size();
        User user = new User(firstNameET.getText().toString(), lastNameET.getText().toString(),
                emailAddressET.getText().toString(), phoneNumberET.getText().toString(), GeneralUtils.md5(passwordET.getText().toString()),
                "Israel", citySpinner.getSelectedItem().toString(), "none");
        user.setId(""+id);
        if(checkNewUserInputs(user))
        {
            Model.instance.addUser(user, () -> Model.instance.setLoggedInUser(user));
            Log.d("TAG", "User with email: " + user.getEmail() + " was added to database.");
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainactivity_fragment_container, new UserProfileFragment());
            fragmentTransaction.commit();
        }
        else
        {
            submitBtn.setEnabled(true);
            Log.d("TAG", "Unable to add user with email: " + user.getEmail() + " to database.");
        }
    }

    private boolean checkNewUserInputs(User user) {
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


        if (GeneralUtils.findUserByEmail(Model.instance.getAllUsers(), user.getEmail()) != null){
            Log.d("TAG", "A user with this email already exists.");
            alertDialogBuilder.setTitle("A user with this email already exists");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
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

        if(citySpinner.getSelectedItem() == null)
        {
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