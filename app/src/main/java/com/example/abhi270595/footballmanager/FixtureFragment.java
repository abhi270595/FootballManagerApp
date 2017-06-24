package com.example.abhi270595.footballmanager;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FixtureFragment extends Fragment implements FixturesDataAdapter.FixtureViewClickHandler{

    private RecyclerView mRecyclerViewFixture;
    private FixturesDataAdapter fixtureAdapter;
    private LinearLayoutManager fixtureLayoutManager;
    private ProgressBar progressBar;
    private ArrayList<String> fixturesBetween = new ArrayList<String>();
    private ArrayList<String> time = new ArrayList<String>();


    public FixtureFragment() {
        // Required empty public constructor
    }

    public void onClick(String particularTournament) {
        Toast.makeText(getContext(), particularTournament, Toast.LENGTH_SHORT).show();
        Intent intentForTournamentDetails = new Intent(getActivity(), EditFixture.class);
        startActivity(intentForTournamentDetails);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FixtureAsyncTask().execute(NetworkUtils.buildFixtureURL());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fixture, container, false);


        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_indicator);
        mRecyclerViewFixture = (RecyclerView) rootView.findViewById(R.id.fixture_recycler_view);
        mRecyclerViewFixture.setHasFixedSize(true);
        fixtureLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFixture.setLayoutManager(fixtureLayoutManager);
        fixtureAdapter = new FixturesDataAdapter(this);
        mRecyclerViewFixture.setAdapter(fixtureAdapter);

        return rootView;
    }

    public class FixtureAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
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

            progressBar.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")) {

                //String[] tour_description_string_array = new String[jsonArr1.length()];

                try {
                    fixturesBetween.clear();
                    time.clear();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        fixturesBetween.add(jsonObject.getString("name"));
                        time.add(jsonObject.getString("username"));
                    }

                    //set the data
                    fixtureAdapter.setResultData(s, fixturesBetween, time);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //TODO logic when there is no json data
            }
        }
    }


}
