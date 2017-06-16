package com.example.abhi270595.footballmanager;

import android.content.Intent;
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

public class CreateTournamentActivity extends AppCompatActivity {

    private static final String[] Tournament_Types = new String[] {
            "League", "Knockout", "League + Knockout"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);

        final Button createButton = (Button) findViewById(R.id.create_button);
        final EditText tournamentName = (EditText) findViewById(R.id.tournament_name);
        final EditText tournamentDescription = (EditText) findViewById(R.id.tournament_description);
        final EditText numberOfTeams = (EditText) findViewById(R.id.number_of_teams);
        final AutoCompleteTextView typeOfTournament = (AutoCompleteTextView) findViewById(R.id.type_of_tournament);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked !!" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "[" + tournamentName.getText().toString()
                        + tournamentDescription.getText().toString()
                        + numberOfTeams.getText().toString() + typeOfTournament.getText().toString() + "]",
                        Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Tournament_Types);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.type_of_tournament);
        textView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_tournament, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
