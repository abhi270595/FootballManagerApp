package com.example.abhi270595.footballmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhi270595.footballmanager.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ProgressBar mProgressBar;
    private ArrayList<String> tour_name_string_array = new ArrayList<String>();
    private ArrayList<String> tour_description_string_array = new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private CardViewDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mState = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mState = "";
                    invalidateOptionsMenu();
                    new NetworkAsyncTask().execute(NetworkUtils.buildUrl());
                    return true;
                case R.id.navigation_archive:
                    mState = "";
                    invalidateOptionsMenu();
                    new ArchiveAsyncTask().execute(NetworkUtils.buildArchiveURL());
                    return true;
                case R.id.navigation_notifications:
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    mState = "HIDE_MENU";
                    invalidateOptionsMenu();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loginSettings = getApplicationContext().getSharedPreferences("Authentication", 0);
        /*SharedPreferences.Editor editor = loginSettings.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.commit();*/

        if (!loginSettings.getBoolean("isLoggedIn", true)) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Football Manager");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardViewDataAdapter();

        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTournamentActivity.class);
                startActivity(intent);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_indicator);

        showRecyclerView();
        new NetworkAsyncTask().execute(NetworkUtils.buildUrl());

    }

    private void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    public class ArchiveAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
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

            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {
                tour_name_string_array.clear();
                tour_description_string_array.clear();
                try {
                    JSONArray jsonArr = new JSONArray(s);
                    System.out.println(jsonArr.length());
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        tour_name_string_array.add(jsonObject.getString("name"));
                        tour_description_string_array.add(jsonObject.getString("body"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.setResultData(s, tour_name_string_array, tour_description_string_array);

            } else {
                //TODO logic when there is no json data
            }
        }
    }


    public class NetworkAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
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

            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {

                //String[] tour_description_string_array = new String[jsonArr1.length()];
                tour_name_string_array.clear();
                tour_description_string_array.clear();
                try {
                    JSONArray jsonArr = new JSONArray(s);
                    System.out.println(jsonArr.length());
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        tour_name_string_array.add(jsonObject.getString("title"));
                        tour_description_string_array.add(jsonObject.getString("body"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.setResultData(s, tour_name_string_array, tour_description_string_array);

            } else {
                //TODO logic when there is no json data
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);

        if (mState.equals("HIDE_MENU")) {
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.requestFocus();
            }
        });

        /*MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(mCountryModel);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true; // Return true to expand action view
                    }
                });*/
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return true;
    }

    private void filter(String query) {
        query = query.toLowerCase();
        final ArrayList<String> filteredName = new ArrayList<>();
        final ArrayList<String> filteredDescription = new ArrayList<>();

        for (int j = 0; j < tour_name_string_array.size(); j++) {
            final String text = tour_name_string_array.get(j).toLowerCase();
            if (text.contains(query)) {
                filteredName.add(tour_name_string_array.get(j));
                filteredDescription.add(tour_description_string_array.get(j));
            }
        }
        mAdapter.setResultData(null, filteredName, filteredDescription);
        //filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        switch (menuItemSelected) {
            case R.id.action_search: //new NetworkAsyncTask().execute(NetworkUtils.buildUrl());
                Toast.makeText(getApplicationContext(), "search menu clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "logout menu clicked", Toast.LENGTH_LONG).show();

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences loginSettings = getApplicationContext().getSharedPreferences("Authentication", 0);
                                SharedPreferences.Editor editor = loginSettings.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.commit();
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                            }
                        }).setNegativeButton("No", null).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}