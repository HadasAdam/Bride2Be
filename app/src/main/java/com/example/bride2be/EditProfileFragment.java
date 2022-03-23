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
import android.widget.Spinner;
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
    TextView UserLastName;
    EditText UserEmail;
    EditText UserPhoneNumber;
    Spinner citySpinner;
    EditText UserStreet;
    Button CancelEditProfile;
    Button SaveEditProfile;
    User userToEdit = Model.instance.getLoggedInUser();
    View view;

    public EditProfileFragment() { }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        UserFirstName = view.findViewById(R.id.FirstNameEditProfileET);
        UserLastName = view.findViewById(R.id.LastNameEditProfileET);
        UserEmail = view.findViewById(R.id.EmailEditProfileET);
        UserPhoneNumber = view.findViewById(R.id.PhoneNumEditProfileET);
        citySpinner = view.findViewById(R.id.editProfile_citySpinner);
        UserStreet = view.findViewById(R.id.StreetEditProfileET);
        CancelEditProfile = view.findViewById(R.id.CancelEditProfileBtn);
        SaveEditProfile = view.findViewById(R.id.SaveEditProfileBtn);
        CancelEditProfile.setOnClickListener(v -> AbortProfileEdit());
        SaveEditProfile.setOnClickListener(v -> SaveChangesInProfile());
        if(userToEdit == null) {
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_loginFragment2);
        }

        UserFirstName.setText(userToEdit.getFirstName());
        UserLastName.setText(userToEdit.getLastName());
        UserEmail.setText(userToEdit.getEmail());
        UserPhoneNumber.setText(userToEdit.getPhoneNumber());
        UserStreet.setText(userToEdit.getStreet());
        initializeCitySpinner();

        return view;
    }

    private void AbortProfileEdit() {
        Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_userProfileFragment2);
    }

    private void SaveChangesInProfile() {
        userToEdit.setFirstName(UserFirstName.getText().toString());
        userToEdit.setLastName(UserLastName.getText().toString());
        userToEdit.setEmail(UserEmail.getText().toString());
        userToEdit.setPhoneNumber(UserPhoneNumber.getText().toString());
        userToEdit.setCity(citySpinner.getSelectedItem().toString());
        userToEdit.setStreet(UserStreet.getText().toString());

        if (checkEditUserInputs(userToEdit)){
            Model.instance.updateUser(userToEdit, () -> Log.d("TAG","User with id: " + userToEdit.getId() + " was updated."));
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_userProfileFragment2);
        }
    }

    private void initializeCitySpinner() {
        String[] cities = new String[Model.instance.getCities().size()];

        for(int i = 0; i < Model.instance.getCities().size(); i++) {
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

        for (int i = 0; i < dataAdapter.getCount(); i++) {
            if (citySpinner.getItemAtPosition(i).equals(userToEdit.getCity())) {
                citySpinner.setSelection(i);
                break;
            }
        }
    }

    private AlertDialog.Builder getAlertDialogBuilder() {
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

    private boolean checkEditUserInputs(User user) {
        AlertDialog.Builder alertDialogBuilder = getAlertDialogBuilder();

        if (!GeneralUtils.isEmailValid(user.getEmail())) {
            Log.d("TAG", "Email address is not valid.");
            alertDialogBuilder.setTitle("Email address is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if (!GeneralUtils.isFirstNameValid(user.getFirstName())) {
            Log.d("TAG", "First name is not valid.");
            alertDialogBuilder.setTitle("First name is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if (!GeneralUtils.isLastNameValid(user.getLastName())) {
            Log.d("TAG", "Last name is not valid.");
            alertDialogBuilder.setTitle("Last name is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }
        if (!GeneralUtils.isPhoneValid(user.getPhoneNumber())) {
            Log.d("TAG", "Phone number is not valid.");
            alertDialogBuilder.setTitle("Phone number is not valid");
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            return false;
        }

        return true;
    }

}