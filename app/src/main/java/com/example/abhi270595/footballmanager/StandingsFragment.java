package com.example.abhi270595.footballmanager;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhi270595.footballmanager.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class StandingsFragment extends Fragment {

    private ProgressBar progressBar;
    private LinearLayout outer;
    private FrameLayout frameLayout;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;


    public StandingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_standings, container, false);

        View rootView = inflater.inflate(R.layout.fragment_standings, container, false);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_layout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_indicator);
        outer = (LinearLayout) rootView.findViewById(R.id.outerLayout);


        return rootView;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new StandingsAsyncTask().execute(NetworkUtils.buildStandingsURL());
    }

    public class StandingsAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            //frameLayout.setVisibility(View.INVISIBLE);
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
            //frameLayout.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {

                try {
                    JSONArray jsonArr = new JSONArray(s);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        LinearLayout standing = new LinearLayout(getContext());

                        standing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        standing.setOrientation(LinearLayout.HORIZONTAL);

                        outer.addView(standing);

                        tv1 = new TextView(getContext());
                        tv1.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4f));
                        tv1.setTextSize(16);
                        tv1.setPadding(0, 0, 0, 16);
                        tv1.setText(jsonObject.getString("username"));
                        standing.addView(tv1);
                        tv2 = new TextView(getContext());
                        tv2.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv2.setTextSize(16);
                        tv2.setPadding(0, 0, 0, 16);
                        tv2.setText(jsonObject.getString("id") + "1");
                        standing.addView(tv2);
                        tv3 = new TextView(getContext());
                        tv3.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv3.setTextSize(16);
                        tv3.setPadding(0, 0, 0, 16);
                        tv3.setText(jsonObject.getString("id") + "3");
                        standing.addView(tv3);
                        tv4 = new TextView(getContext());
                        tv4.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv4.setTextSize(16);
                        tv4.setPadding(0, 0, 0, 16);
                        tv4.setText(jsonObject.getString("id") + "2");
                        standing.addView(tv4);
                        tv5 = new TextView(getContext());
                        tv5.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv5.setTextSize(16);
                        tv5.setPadding(0, 0, 0, 16);
                        tv5.setText(jsonObject.getString("id"));
                        standing.addView(tv5);
                        tv6 = new TextView(getContext());
                        tv6.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv6.setTextSize(16);
                        tv6.setPadding(0, 0, 0, 16);
                        tv6.setText(jsonObject.getString("id") + "7");
                        standing.addView(tv6);
                        tv7 = new TextView(getContext());
                        tv7.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        tv7.setTextSize(16);
                        tv7.setPadding(0,0,0,16);
                        tv7.setText(jsonObject.getString("id") + "2");
                        standing.addView(tv7);



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
