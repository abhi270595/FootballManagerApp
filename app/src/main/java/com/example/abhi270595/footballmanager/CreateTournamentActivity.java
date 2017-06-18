package com.example.abhi270595.footballmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhi270595.footballmanager.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class CreateTournamentActivity extends AppCompatActivity {

    private static final String[] Tournament_Types = new String[] {
            "League", "Knockout", "League + Knockout"
    };
    private ProgressDialog progressDialog;
    private Button createButton;
    private EditText tournamentName;
    private EditText tournamentDescription;
    private EditText numberOfTeams;
    private AutoCompleteTextView typeOfTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);

        createButton = (Button) findViewById(R.id.create_button);
        tournamentName = (EditText) findViewById(R.id.tournament_name);
        tournamentDescription = (EditText) findViewById(R.id.tournament_description);
        numberOfTeams = (EditText) findViewById(R.id.number_of_teams);
        typeOfTournament = (AutoCompleteTextView) findViewById(R.id.type_of_tournament);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(), "Clicked !!" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "[" + tournamentName.getText().toString()
                        + tournamentDescription.getText().toString()
                        + numberOfTeams.getText().toString() + typeOfTournament.getText().toString() + "]",
                        Toast.LENGTH_LONG).show();*/
                new TournamentCreatorAsyncTask().execute(NetworkUtils.buildUrl());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Tournament_Types);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.type_of_tournament);
        textView.setAdapter(adapter);

    }

    public class TournamentCreatorAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            createButton.setEnabled(false);
            progressDialog = new ProgressDialog(CreateTournamentActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating a tournament for you...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL networkUrl = params[0];
            String queryResults = null;

            try {
                //TODO: instead of getting implementing posting to the service
                queryResults = NetworkUtils.getResponseFromHttpUrl(networkUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queryResults;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            createButton.setEnabled(true);
            if (s != null && !s.equals("")) {

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if (!tournamentName.getText().toString().equals("") && jsonObject.getString("title").equals(tournamentName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Created the tournament successfully...", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tournament Creation Failed...Please try again..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //TODO logic when there is no json data
                //finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
