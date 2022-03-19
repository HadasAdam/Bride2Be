package com.example.bride2be;

import android.util.Log;

import com.example.bride2be.models.Model;
import com.example.bride2be.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeneralUtils {
    public static String md5(final String givenPassword) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(givenPassword.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static User findUserByEmail(List<User> userList, final String givenEmail) {
        final User[] userWithGivenEmail = new User[1];
        userWithGivenEmail[0] = null;
        Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void onComplete(List<User> users) {
                for(User user: userList) {
                    if (user.getEmail().equals(givenEmail)) {
                        Log.d("TAG", "found a user with this mail: " + givenEmail);
                        userWithGivenEmail[0] = user;
                        break;
                    }
                }
            }
        });
        return userWithGivenEmail[0];
    }

    public static boolean isEmailValid(String enteredEmail) {
        String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(enteredEmail);
        return !enteredEmail.isEmpty() && matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        // checking if password length is between 6 and 15 and if password don't contains spaces
        return ((password.length() >= 6) && (password.length() <= 15)) && !password.contains(" ");
    }

    public static boolean isFirstNameValid(String firstName) {
        return firstName.matches( "[A-Z][a-z]{1,14}" );
    }

    public static boolean isLastNameValid(String lastName) {
        return lastName.matches( "[A-Z][a-z]{1,14}" );
    }

    public static boolean isPhoneValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]{10}");
    }

    public static boolean isTitle(String productTitle) {
        return productTitle.length() >= 4 && productTitle.length() <= 12;
    }

    public static boolean isDescription(String productDescription) {
        return productDescription.matches("[A-Z][a-z]{1,14}") && productDescription.length() >= 4 && productDescription.length() <= 50;
    }

    public static boolean isPrice(String productPrice) {
        try {
            Double.parseDouble(productPrice);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }



}
