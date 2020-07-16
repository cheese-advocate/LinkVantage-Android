package com.example.compulinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compulinkapp.classes.InputValidatorHelper;
import com.example.compulinkapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        /**
         * General useful information to the user, adds to user friendliness
         */
        Toast.makeText(getApplicationContext(), "To reset your password an OTP will be sent to you either via\nEmail or\nTo your account on the website", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(ForgotPasswordActivity.this, "An Email containing an OTP has been sent to you", Toast.LENGTH_SHORT).show();
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
                                dialog.dismiss(); //Closes the dialog
                                Intent newPassword = new Intent(getApplicationContext(), NewPasswordActivity.class);
                                startActivity(newPassword); //Starts the new activity
                            }
                        }
                    });
                }

            }
        });
    }
}
