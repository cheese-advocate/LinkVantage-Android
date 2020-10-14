package com.example.compulinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.InputValidatorHelper;
import com.example.compulinkapp.R;

import java.util.concurrent.ExecutionException;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final Button returnToLogin = (Button) findViewById(R.id.backToLogin);

        returnToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                returnToLogin.startAnimation(anim);
                Intent backToLogin = new Intent(v.getContext(), LoginActivity.class);
                startActivity(backToLogin);
            }
        });

        /**
         * Reset submission button variable
         */
        final Button resetPasswordSubmit = (Button) findViewById(R.id.reset_password_submit);
        /**
         * Variables used to validate the email entered when resetting password
         */
        final EditText email = findViewById(R.id.emailInp);
        final InputValidatorHelper helper = new InputValidatorHelper();

        resetPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                resetPasswordSubmit.startAnimation(anim);
                if(helper.isEmail(email).equals("false"))//invalid email
                {
                    Toast.makeText(v.getContext(), "Invalid email entered\nEmail should look like the following:\nexample@example.com", Toast.LENGTH_LONG).show();
                }
                else//valid email
                {
                    try {
                        emailOTP(); //e-mail OTP to entered email.
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ForgotPasswordActivity.this, "If an account for that email exists, an OTP will be sent to you shortly.", Toast.LENGTH_SHORT).show();


                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Enter OTP");

                    final EditText otpInp = new EditText(v.getContext());
                    builder.setView(otpInp);

                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Purposely left empty as this method gets overridden by another method
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel(); //Closes the dialog
                        }
                    });

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    /**
                     *Used to validate the OTP and will only close the dialog box if the OTP is valid
                     * Validation can be done in the method below
                     */
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String otp;
                            otp = otpInp.getText().toString().trim();

                            if(otp.equals(""))
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "OTP is empty", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                try {
                                    Log.d("OTP Response:" ,"Entered OTP:" + otp + " , server returned is OTP valid:" + resetPassword() );
                                //    Log.d("OTP Response:" ,"Entered OTP:" + otp + " , server returned OTP :" + fetchOTP() );
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //App Activity Transitioning - START
                                dialog.dismiss(); //Closes the dialog
                                Intent newPassword = new Intent(getApplicationContext(), NewPasswordActivity.class);
                                startActivity(newPassword); //Starts the new activity
                                //App Activity Transitioning - .END
                            }
                        }
                    });
                }

            }
        });
    }

//Send OTP to Email - START
    private String emailOTP() throws ExecutionException, InterruptedException, ExecutionException {

        //Initialising variables from user_input_fields - Start
        EditText email = findViewById(R.id.emailInp);
        //Initialising variables from user_input_fields - Start

        //Constructing Post Variable - Start
        String postVar = "HANDLE_EMAIL_OTP";
        postVar = postVar   + "-"     + "email="       +  email    .getText()  .toString();
        //Constructing Post Variable - End

        Conect emailOTP = new Conect();

        String response = (String) emailOTP.execute(postVar).get();
        Log.d("Testing Response:" ,"e-Mail OTP Response:" + response );


        return response;
    };
//Send OTP to Email - START
//Retrieve OTP from server - START
private String fetchOTP() throws ExecutionException, InterruptedException, ExecutionException {

    //Initialising variables from user_input_fields - Start
    EditText email = findViewById(R.id.emailInp);
    //Initialising variables from user_input_fields - Start

    //Constructing Post Variable - Start
    String postVar = "HANDLE_RETRIEVE_OTP";
    postVar = postVar   + "-"     + "email="       +  email    .getText()  .toString();
    //Constructing Post Variable - End

    Conect retrieveOTP = new Conect();

    String returnOTP = (String) retrieveOTP.execute(postVar).get();
    Log.d("Testing Response:" ,"Fetch OTP Response:" + returnOTP );

    return returnOTP;
};
//Retrieve OTP from server - END
//Reset User Password - START
private String resetPassword() throws ExecutionException, InterruptedException, ExecutionException {

    //Initialising variables from user_input_fields - Start
    EditText email = findViewById(R.id.emailInp);
    //Initialising variables from user_input_fields - Start

    //Constructing Post Variable - Start
    String postVar = "HANDLE_RESET_PASSWORD";
    postVar = postVar   + "-"     + "email="       +  email    .getText()  .toString();
    postVar = postVar   + "-"     + "userOTP="     + "031192";    // OTP will go "07P07P" <- here;
    postVar = postVar   + "-"     + "newPassword=" + "p@sswordd";    // password will go "p@sswordd" <- here;
    //Constructing Post Variable - End

    Conect testingConnection = new Conect();

    String testing = (String) testingConnection.execute(postVar).get();
    Log.d("Testing Response:" ,"Reset Password Response" + testing );

    return testing;
};
//Reset User Password - END

}
//FORGOT PASSWORD ACTIVITY - END
