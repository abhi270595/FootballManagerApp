package com.example.abhi270595.footballmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar mProgressBar;
    private ArrayList<String> tour_name_string_array = new ArrayList<String>();
    private ArrayList<String> tour_description_string_array = new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private CardViewDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    new NetworkAsyncTask().execute(NetworkUtils.buildUrl());
                    return true;
                case R.id.navigation_archive:
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    new NetworkAsyncTask().execute(NetworkUtils.buildUrl());

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public class NetworkAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
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

                //String[] tour_description_string_array = new String[jsonArr1.length()];

                try {
                    JSONArray jsonArr = new JSONArray(s);
                    System.out.println(jsonArr.length());
                    for(int i = 0; i<jsonArr.length(); i++) {
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
        //final String[][] filteredModelList = filter(, newText);

        //mAdapter.setResultData();
        return true;
    }

    /*private String[][] filter( models, String query) {
        query = query.toLowerCase();final List<CountryModel> filteredModelList = new ArrayList<>();
        for (CountryModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }*/

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
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "settings menu clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}