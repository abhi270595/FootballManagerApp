package com.example.abhi270595.footballmanager;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhi270595.footballmanager.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class GeneralNotificationFragment extends Fragment {

    private ListView listView;
    private TextView matchBetween;
    private ProgressBar progressBar;
    private ArrayList<String> matchList= new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public GeneralNotificationFragment() {

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GeneralNotificationsAsyncTask().execute(NetworkUtils.buildSecondNotificationUrl());



        //new StandingsAsyncTask().execute(NetworkUtils.buildStandingsURL());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fixture, container, false);

        matchBetween = (TextView) rootView.findViewById(R.id.match_between);
        listView = (ListView) rootView.findViewById(R.id.fixture_Listview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_indicator);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.fixture_list,R.id.match_between,matchList);
        listView.setAdapter(adapter);

        return rootView;
    }

    public class GeneralNotificationsAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            listView.setVisibility(View.INVISIBLE);
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
            listView.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {

                //String[] tour_description_string_array = new String[jsonArr1.length()];

                try {
                    adapter.clear();
                    matchList.clear();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        matchList.add(jsonObject.getString("body"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //TODO logic when there is no json data
            }
        }
    }


}
