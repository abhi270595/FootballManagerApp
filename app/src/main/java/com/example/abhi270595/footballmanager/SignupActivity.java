package com.example.abhi270595.footballmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Abhishek Waghela on 2/6/2017.
 */

public class SignupActivity extends AppCompatActivity {
    // Sign up details
    private String email;
    private String password;
    private String name;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private Button mEmailSignUpButton;
    private TextView mSignUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        // Set up the signup form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.signup_email);
        mPasswordView = (EditText) findViewById(R.id.signup_password);
        mNameView = (EditText) findViewById(R.id.input_name);

        mEmailSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        mSignUpLink = (TextView) findViewById(R.id.link_signup);
        mSignUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent myIntent = new Intent(SignupActivity.this, LoginActivity.class);
                SignupActivity.this.startActivity(myIntent);
            }
        });
    }

    public void attemptSignup() {

        mEmailSignUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating your Account...");
        progressDialog.show();

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        name = mNameView.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onSignUpSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignUpSuccess() {
        mEmailSignUpButton.setEnabled(true);
        finish();
    }
}
