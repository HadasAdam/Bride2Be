package com.example.bride2be;

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

    public static User findUserByEmail(List<User> userList, String givenEmail) {
        for(User user: userList)
            if(user.getEmail().equals(givenEmail)) {
                return user;
            }
        return null;
    }

    public static boolean isEmailValid(String enteredEmail) {
        String EMAIL_REGEX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(enteredEmail);
        return !enteredEmail.isEmpty() && matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        // checking if password length is between 6 and 15 and if password don't contains spaces
        return ((password.length() >= 6) && (password.length() <= 15)) && !password.contains(" ");
    }

    public static boolean isFirstNameValid(String firstName) {
        return firstName.matches( "[A-Z][a-z]*" );
    }

    public static boolean isLastNameValid(String lastName) {
        return lastName.matches( "[A-Z]+([ '-][a-zA-Z]+)*" );
    }

    public static boolean isPhoneValid(String phoneNumber) {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return (m.matches());
    }

}
