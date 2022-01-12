package com.example.bride2be.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.bride2be.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    EditText firstNameEt;
    EditText lastNameEt;
    EditText emailEt;
    EditText phoneNumberEt;
    EditText passwordEt;
    EditText countryET;
    Button signUpBtn;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up,container, false);
        firstNameEt = view.findViewById(R.id.FirstNameET);
        lastNameEt = view.findViewById(R.id.LastNameET);
        emailEt = view.findViewById(R.id.EmailET);
        phoneNumberEt = view.findViewById(R.id.PhoneNumberET);
        passwordEt = view.findViewById(R.id.PasswordET);
        signUpBtn = view.findViewById(R.id.SignUpButton);
        // countryET = view.findViewById(R.id.CountryET);
        signUpBtn.setOnClickListener(v -> makeUser());
        return view;
    }

    private void makeUser() {
        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String email = emailEt.getText().toString();
        String phoneNumber = phoneNumberEt.getText().toString();
        String password = passwordEt.getText().toString();
//        String country = countryET.getText().toString();
       // Todo - implement id with full name + 1 (check database for existed users)

        User user = new User(1232441414L, firstName, lastName, email, phoneNumber, password, null, null, null);

    }
}