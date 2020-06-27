package com.example.compulinkapp;

import android.text.TextUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InputValidatorHelper {

    // - InputValidatorHelper - Start

    public String isEmail(TextView tv) {
        String message = "null";
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String email = tv.getText().toString();

        if (email.matches(regex) == true){
            message = "true"; //true
        } else {
            message = "false"; //false
        }

        return message;
    }

    public String isNumeric(TextView tv) { //This method tests if the entry String are all digits - Working (should change to boolean)

        String message = "Null";
        String textView = tv.getText().toString();

        try {
            Double.parseDouble(textView);
            message = "true"; // true - is Numeric
        } catch(NumberFormatException e){
            message = "false"; // false - is not numeric
        }
        return message;
    }

    public String isEmpty(TextView tv) {  //This method tests if the field is empty - Working (should change to boolean)

        String message = "Null";
        if (tv.getText().toString().isEmpty()) {
            message = "true"; //True
        } else {
            message = "false"; //False
        }
        return message;
    }

    public String lengthCheckMax(TextView tv , int maxSize) {//This method tests the length of the data against the max size it is allowed. - Working (should change to boolean)

        String message = "Null";
        int maximumSize = maxSize;
        String textView = tv.getText().toString();
        int length = textView.length();

        if (length > maximumSize){
            message = "false"; //False - The string is bigger than it is allowed to be
        } else {
            message = "true"; //True - The string size fits the criteria
        }
        return message;
    }
    public String lengthCheckMin(TextView tv , int minSize) {//This method tests the length of the data against the max size it is allowed. - Working (should change to boolean)

        String message = "Null";
        int minimumSize = minSize;
        String textView = tv.getText().toString();
        int length = textView.length();

        if (length < minimumSize){
            message = "false"; //False - The string is bigger than it is allowed to be
        } else {
            message = "true"; //True - The string size fits the criteria
        }
        return message;
    }
    public String falsify() {//This method ensures the validation string is false.
        return "false";
    }

    public CharSequence appendToast(CharSequence toastPrint , String extraToast) {//This method ensures the validation string is false.
        CharSequence tp = toastPrint + "\n" + extraToast;
        return tp;
    }
    public String containsInt(TextView textView) {//This method ensures the validation string contains at least one int.
        String message = "null";
        String tv = textView.getText().toString();
        int tvLength = tv.length();
        int loop = 1;
        char temp;
        message = "false";

        while (loop == 1){
            for (int i = 0; i < tvLength; i++) {
                temp = tv.charAt(i);
                if (temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == '5'  //There must be an easier way to do this...
                    || temp == '6' || temp == '7' || temp == '8' || temp == '9' || temp == '0')
                {
                    message = "true";
                    loop --;
                }
            }
            loop --;
        }
        return message;
    }

    public String containsUpperCase(TextView textView) {//This method ensures the validation string contains at least one upper-case character.
        String message = "null";
        String tv = textView.getText().toString();
        int tvLength = tv.length();
        int loop = 1;
        char temp;

        message = "false"; // false - Does not contain an upper-case character.

        while (loop == 1){

            for (int i = 0; i < tvLength; i++) {
                temp = tv.charAt(i);
                if (temp != '1' && temp != '2' && temp != '3' && temp != '4' && temp != '5'  //There must be an easier way to do this...
                        && temp != '6' && temp != '7' && temp != '8' && temp != '9' && temp != '0')
                {
                    if (temp == tv.toUpperCase().charAt(i)){
                        message = "true"; //There is an upper-case character.
                        loop --;
                    }
                }
            }
            loop --;

        }

        return message;
    }

    public boolean validPassword(String pw)
    {
        /**
         * Password should be the following:
         * At least 8 characters
         * One uppercase
         * One lowercase
         * One number
         * One special character
         *
         * If this is too complex or does not match the database it can be easily simplified
         */
        String pwRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if(pw.trim().matches(pwRegex))
        {
            return true;
        }
        else return false;
    }
    // - InputValidatorHelper - End

}


