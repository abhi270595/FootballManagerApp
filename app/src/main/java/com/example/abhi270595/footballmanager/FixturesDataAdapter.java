package com.example.abhi270595.footballmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by chunga on 24/6/17.
 */
public class FixturesDataAdapter extends RecyclerView.Adapter<FixturesDataAdapter.ViewHolder>{


    private String jsonResult;
    private ArrayList<String> fixtureBetweenTeams;
    private ArrayList<String> time;
    private ArrayList<String> date;
    private ArrayList<String> location;

    private FixtureViewClickHandler mClickHandler;

    public FixturesDataAdapter(FixtureViewClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }

    public interface FixtureViewClickHandler {
        void onClick(String particularTournament);
    }


    public void setResultData(String result, ArrayList<String> fixture, ArrayList<String> time1) {
        jsonResult = result;
        fixtureBetweenTeams = fixture;
        time = time1;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();

        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.fixtures_row, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.fixtureTv.setText(fixtureBetweenTeams.get(position));

        viewHolder.timeTv.setText(time.get(position));

        //viewHolder.singlestudent=stList.get(position);

    }

    @Override
    public int getItemCount() {
        if (fixtureBetweenTeams == null) return 0;
        return fixtureBetweenTeams.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView fixtureTv;
        public TextView timeTv;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            fixtureTv = (TextView) itemLayoutView.findViewById(R.id.recycler_fixture_between);
            timeTv = (TextView) itemLayoutView.findViewById(R.id.recycler_time);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(
                            v.getContext(), fixtureTv.getText() + "" + timeTv.getText(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String fix = fixtureBetweenTeams.get(adapterPosition);
            mClickHandler.onClick(fix);
        }



    }


}
