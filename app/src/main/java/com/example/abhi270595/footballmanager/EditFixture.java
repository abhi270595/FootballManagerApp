package com.example.abhi270595.footballmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditFixture extends AppCompatActivity {

    private EditText fixtureTime;
    private EditText fixtureLocation;
    private EditText fixtureDate;
    private TextView fixtureName;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fixture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Fixture..");


        fixtureLocation = (EditText) findViewById(R.id.fixture_location);
        fixtureTime = (EditText) findViewById(R.id.fixture_time);
        fixtureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditFixture.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fixtureTime.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                /*mTimePicker.setTitle("Select Time");*/
                mTimePicker.show();
            }
        });

        fixtureDate = (EditText) findViewById(R.id.fixture_date);
        fixtureDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditFixture.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        fixtureDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        fixtureName = (TextView) findViewById(R.id.fixture_name);
        update = (Button) findViewById(R.id.fixture_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update the fixture in the fixture fragment
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_fixture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
