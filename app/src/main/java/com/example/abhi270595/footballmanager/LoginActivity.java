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
 * Created by Abhishek Waghela on 2/5/2017.
 */

public class LoginActivity extends AppCompatActivity {
    // Login details
    private String email;
    private String password;
    private ProgressDialog progressDialog;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextView mSignUpLink;

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AuthenticationAsyncTask().execute(NetworkUtils.buildAuthenticationURL());
            }
        });

        mSignUpLink = (TextView) findViewById(R.id.link_signup);
        mSignUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }


    public class AuthenticationAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mEmailSignInButton.setEnabled(false);
            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Letting you in..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL networkUrl = params[0];
            String queryResults = null;

            try {
                queryResults = NetworkUtils.getResponseFromHttpUrl(networkUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queryResults;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            mEmailSignInButton.setEnabled(true);
            if (s != null && !s.equals("")) {

                //String[] tour_description_string_array = new String[jsonArr1.length()];

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    email = mEmailView.getText().toString();
                    password = mPasswordView.getText().toString();

                    if (!email.equals(null) && email.equals(jsonObject.getString("username"))) {
                        onLoginSuccess();
                    } else {
                        Toast.makeText(getApplicationContext(), " Login Failed :( ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //TODO logic when there is no json data
            }
        }
    }

    public void onLoginSuccess() {
        mEmailSignInButton.setEnabled(true);
        SharedPreferences loginSettings = getApplicationContext().getSharedPreferences("Authentication", 0);
        SharedPreferences.Editor editor = loginSettings.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.commit();
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
}
