package com.example.abhi270595.footballmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;
    private CardViewDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    //private List<Student> studentList;

    // on scroll

    /*private static int current_page = 1;

    private int ival = 1;
    private int loadLimit = 10;*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    new NetworkAsyncTask().execute(NetworkUtils.buildUrl());
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_archive:
                    mTextMessage.setText("");
                    new NetworkAsyncTask().execute(NetworkUtils.buildUrl());
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("");
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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //studentList = new ArrayList<Student>();

        //loadData(current_page);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter();

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

       /* mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(
                mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do somthing...

                loadMoreData(current_page);

            }

        });*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, CreateTournamentActivity.class);
                startActivity(intent);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_indicator);

        showRecyclerView();
        new NetworkAsyncTask().execute(NetworkUtils.buildUrl());

    }

    // By default, we add 10 objects for first time.
    /*private void loadData(int current_page) {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request

        for (int i = ival; i <= loadLimit; i++) {
            Student st = new Student("Student " + i, "androidstudent" + i
                    + "@gmail.com", false);

            studentList.add(st);
            ival++;

        }

    }*/
    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item
    /*private void loadMoreData(int current_page) {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request

        loadLimit = ival + 10;

        for (int i = ival; i <= loadLimit; i++) {
            Student st = new Student("Student " + i, "androidstudent" + i
                    + "@gmail.com", false);

            studentList.add(st);
            ival++;
        }

        mAdapter.notifyDataSetChanged();

    }*/

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
            if (s != null && !s.equals("")) {

                int length;
                JSONArray jsonArr1 = null;
                try {
                    jsonArr1 = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String[] tour_name_string_array = new String[jsonArr1.length()];
                String[] tour_description_string_array = new String[jsonArr1.length()];
                System.out.println(s);

                try {
                    JSONArray jsonArr = new JSONArray(s);
                    System.out.println(jsonArr.length());
                    for(int i = 0; i<jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        tour_name_string_array[i] = jsonObject.getString("title");
                        System.out.println(tour_name_string_array);
                        tour_description_string_array[i] = jsonObject.getString("body");
                        System.out.println(tour_description_string_array);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mAdapter.setResultData(s, tour_name_string_array, tour_description_string_array);

                // json formatting
                /*try {
                    showRecyclerView();
                    JSONArray jsonArray = new JSONArray(s);
                    *//*for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                    }*//*
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    //mTextMessage.setText(jsonObject.getString("title"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                //mTextMessage.setText(s);
            } else {
                //TODO logic when there is no json data
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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