package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        //Toast message to be shown when this activity starts
        Toast.makeText(getApplicationContext(), "Only one contact can be created during registration.\nAdditional Contacts can be added after registration", Toast.LENGTH_LONG).show();

        Button companyOptBut = (Button) findViewById(R.id.companyOptionBut);

        companyOptBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchOpt = new Intent(v.getContext(), RegisterCompanyActivity.class);
                startActivity(switchOpt);
            }
        });

 // - Assigning Inputs - Start
        // - Contact Information - Start
        final TextView username =   (TextView) findViewById(R.id.usernameRegInp);
        final TextView password =   (TextView) findViewById(R.id.passwordRegInp);
        final TextView firstname =  (TextView) findViewById(R.id.firstNameInp);
        final TextView lastname =   (TextView) findViewById(R.id.lastNameInp);
        final TextView email =      (TextView) findViewById(R.id.emailRegInp);
        final TextView phone =      (TextView) findViewById(R.id.phoneRegInp);
        // - Contact Information - End
        // - Address Information - Start
        final TextView number =     (TextView) findViewById(R.id.streetNumRegInp);
        final TextView name =       (TextView) findViewById(R.id.adressRegInp); //Note 1 'd' - Needs revising
        final TextView city =       (TextView) findViewById(R.id.citySuburbRegInp);
        final TextView postal =     (TextView) findViewById(R.id.postalCodeRegInp);
        final TextView addInfo =    (TextView) findViewById(R.id.additionalInfoRegInp);
        // - Address Information - End
 // - Assigning Inputs - End

        //Register Button Code - Start
        Button regBut =             (Button) findViewById(R.id.registerBut);

        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputValidatorHelper helper = new InputValidatorHelper();

                //Variables for Toast - Start
               // String validity = "true"; // We will assume the TextViews as valid and check against criteria.
                String validity = "true"; //true actually
                Context context = getApplicationContext();
                CharSequence toastPrint = "The following validation criteria are not met:";
                int durationToast = Toast.LENGTH_LONG;
                //Variables for Toast - End

                //Testing Validation - REMOVE



                //Testing Validation - REMOVE


 //Validation - Start
                //Username Validation - Start
                if (helper.isEmpty(username).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"Username - Should not be empty." );
                    validity = helper.falsify();
                } else {
                    if(helper.lengthCheckMin(username, 4).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"Username - Should be at least 4 characters long." );
                        validity = helper.falsify();
                    } else {
                        if(helper.lengthCheckMax(username, 18).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"Username - Should not exceed 18 characters in length." );
                            validity = helper.falsify();
                        } else {
                            if(helper.containsInt(username).equals("true")){
                                toastPrint = helper.appendToast(toastPrint ,"Username - Should not contain any integers." );
                                validity = helper.falsify();
                            }
                        }
                    }
                }
                //Username Validation - End
                //Password Validation - Start
                if (helper.isEmpty(password).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"Password - Should not be empty." );
                    validity = helper.falsify();
                } else {
                    if (helper.lengthCheckMin(password, 8).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"Password - Should be at least 8 characters." );
                        validity = helper.falsify();
                    } else{
                        if (helper.lengthCheckMax(password , 15).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"Password - Should not exceed 15 characters in length." );
                            validity = helper.falsify();
                        } else{
                            if(helper.containsInt(password).equals("false")){
                                toastPrint = helper.appendToast(toastPrint ,"Password - Should contain an integer" );
                                validity = helper.falsify();
                            } else {
                                if (helper.containsUpperCase(password).equals("false")){
                                    toastPrint = helper.appendToast(toastPrint ,"Password - Should contain an upper-case character" );
                                    validity = helper.falsify();
                                }
                            }
                        }
                    }
                }
                //Password Validation - End
                //FirstName Validation - Start
                if (helper.isEmpty(firstname).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"First Name - Should not be empty." );
                    validity = helper.falsify();
                } else {
                    if (helper.lengthCheckMax(firstname , 18).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"First Name - Should not exceed 18 characters." );
                        validity = helper.falsify();
                    } else{
                        if(helper.containsInt(firstname).equals("true")){
                            toastPrint = helper.appendToast(toastPrint ,"First Name - Should not contain an integer." );
                            validity = helper.falsify();
                        }
                    }
                }
                //FirstName Validation - End
                //LastName Validation - Start
                if (helper.isEmpty(lastname).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"Last Name - Should not be empty." );
                    validity = helper.falsify();
                } else{
                    if (helper.lengthCheckMax(lastname , 18).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"Last Name - Should not exceed 18 characters." );
                        validity = helper.falsify();
                    } else {
                        if(helper.containsInt(lastname).equals("true")){
                            toastPrint = helper.appendToast(toastPrint ,"Last Name - Should not contain an integer." );
                            validity = helper.falsify();
                        }
                    }
                }
                //LastName Validation - End
                //eMail Validation - Start
                if (helper.isEmpty(email).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"e-Mail - Should not be empty." );
                    validity = helper.falsify();
                } else {
                    if (helper.isEmail(email).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"e-Mail - Should match standard e-mail patterns." );
                        validity = helper.falsify();
                    }
                }
                //eMail Validation - End
                //phoneNumber Validation - Start
                if (helper.isEmpty(phone).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"phoneNumber - Should not be empty." );
                    validity = helper.falsify();
                } else {
                    if (helper.isNumeric(phone).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"phoneNumber - Should be numeric.");
                        validity = helper.falsify();
                    } else {
                        if (helper.lengthCheckMin(phone,10).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"phoneNumber - Should be 10 digits long.");
                            validity = helper.falsify();
                        }else {
                            if (helper.lengthCheckMax(phone, 12).equals("false")){
                                toastPrint = helper.appendToast(toastPrint ,"phoneNumber - Length should smaller than 13 digits.");
                                validity = helper.falsify();
                            }
                        }
                    }
                }
                //phoneNumber Validation - End
          //Address Validation - Start
                //House number validation - Start
                if (helper.isEmpty(number).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"House Number - Should not be null." );
                    validity = helper.falsify();
                } else {
                    if (helper.isNumeric(number).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"House Number - Should be numeric." );
                        validity = helper.falsify();
                    } else {

                    }  if (helper.lengthCheckMax(number , 6).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"House Number - Should not be longer than 6 characters long." );
                        validity = helper.falsify();
                    }

                }

                //House number validation - End
                //Street name validation -  Start
                if (helper.isEmpty(name).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"Street Name - Should not be null." );
                    validity = helper.falsify();
                } else {
                    if(helper.lengthCheckMin(name , 2).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"Street Name - Should be at least 2 characters long." );
                        validity = helper.falsify();
                    }else{
                        if (helper.lengthCheckMax(name , 18).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"Street Name - Should not be longer than 18 characters." );
                            validity = helper.falsify();
                        } else {
                            if(helper.containsInt(name).equals("true")){
                                toastPrint = helper.appendToast(toastPrint ,"Street Name - Should not contain integers." );
                                validity = helper.falsify();
                            }
                        }
                    }
                }
                //Street name validation -  End
                //City validation -  Start
                if (helper.isEmpty(city).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"City - Should not be null." );
                    validity = helper.falsify();
                } else {
                    if(helper.lengthCheckMin(city , 2).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"City - Should be at least 2 characters long." );
                        validity = helper.falsify();
                    }else{
                        if (helper.lengthCheckMax(city , 18).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"City - Should not be longer than 18 characters." );
                            validity = helper.falsify();
                        } else {
                            if (helper.containsInt(city).equals("true")){
                                toastPrint = helper.appendToast(toastPrint ,"City - Should not contain an integer." );
                                validity = helper.falsify();
                            }
                        }
                    }
                }
                //City validation -  End
                //Postal Code validation -  Start
                if (helper.isEmpty(postal).equals("true")){
                    toastPrint = helper.appendToast(toastPrint ,"Postal Code - Should not be null." );
                    validity = helper.falsify();
                } else {
                    if(helper.lengthCheckMin(postal , 4).equals("false")){
                        toastPrint = helper.appendToast(toastPrint ,"Postal Code - Should not be less than 4 characters long." );
                        validity = helper.falsify();
                    }else{
                        if (helper.lengthCheckMax(postal , 5).equals("false")){
                            toastPrint = helper.appendToast(toastPrint ,"Postal Code - Should not be longer than 5 characters." );
                            validity = helper.falsify();
                        } else {
                            if (helper.isNumeric(postal).equals("false")){
                                toastPrint = helper.appendToast(toastPrint ,"Postal Code - Should consist of integers." );
                                validity = helper.falsify();
                            }
                        }
                    }
                }
                //Postal Code validation -  End
          //Address Validation - Start
 //Validation - End

//Validity True - Start


                //Constructing Post Variable - Start
                String postVar = "RegisterClient:";

                postVar = postVar   + "\n"          + "username="    + username.getText().toString();
                postVar = postVar + "\n"     + "password=" +  password.getText().toString();
                postVar = postVar + "\n"     + "firstName="   + firstname.getText().toString();
                postVar = postVar + "\n"     + "lastName="    + lastname.getText().toString();
                postVar = postVar + "\n"     + "email="       + email.getText().toString();
                postVar = postVar + "\n"     + "phone="       + phone.getText().toString();

                postVar = postVar + "\n"     + "number="      + number.getText().toString();
                postVar = postVar + "\n"     + "name="        + name.getText().toString();
                postVar = postVar + "\n"     + "city="        + phone.getText().toString();
                postVar = postVar + "\n"     + "postal="      + postal.getText().toString();
                postVar = postVar + "\n"     + "addInfo="     + addInfo.getText().toString();
                //Constructing Post Variable - End

                if(validity.equals("true")){ //

                    toastPrint = "Successfully Validated - Registering Account.";

                //Post Process - Start
                    Conect conect = new Conect();
                    conect.execute(postVar);
                //Post Process - End
                }

                Toast toast = Toast.makeText(context, toastPrint, durationToast);
                toast.show();

//Validity True - End

            }
        });//Register Button Code - End

    } //onCreate End

} //End - Register Account Class