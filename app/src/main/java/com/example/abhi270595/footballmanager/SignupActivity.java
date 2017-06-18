package com.example.abhi270595.footballmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhi270595.footballmanager.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Abhishek Waghela on 2/6/2017.
 */

public class SignupActivity extends AppCompatActivity {
    // Sign up details
    private String email;
    private String password;
    private String name;
    private ProgressDialog progressDialog;

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
                //TODO : change the service
                new SignupAsyncTask().execute(NetworkUtils.buildAuthenticationURL());
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


    public class SignupAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mEmailSignUpButton.setEnabled(false);
            progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating your account...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL networkUrl = params[0];
            String queryResults = null;

            try {
                //TODO : hook it up with a post service not a get service
                queryResults = NetworkUtils.getResponseFromHttpUrl(networkUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queryResults;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            mEmailSignUpButton.setEnabled(true);
            if (s != null && !s.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    email = mEmailView.getText().toString();
                    password = mPasswordView.getText().toString();

                    if (!email.equals(null) && email.equals(jsonObject.getString("username"))) {
                        onSignUpSuccess();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account creation Failed :( ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //TODO logic when there is no json data
            }
        }
    }

    public void onSignUpSuccess() {
        mEmailSignUpButton.setEnabled(true);
        SharedPreferences loginSettings = getApplicationContext().getSharedPreferences("Authentication", 0);
        SharedPreferences.Editor editor = loginSettings.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.commit();
        Intent myIntent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
}
