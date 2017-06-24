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
public class RequestsDataAdapter extends RecyclerView.Adapter<RequestsDataAdapter.ViewHolder>{


    private String jsonResult;
    private ArrayList<String> teamName;

    public void setResultData(String result, ArrayList<String> teamName1) {
        jsonResult = result;
        teamName = teamName1;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();

        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.requests_row, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.teamNameTv.setText(teamName.get(position));

    }


    @Override
    public int getItemCount() {
        if (teamName == null) return 0;
        return teamName.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {

        public TextView teamNameTv;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            teamNameTv = (TextView) itemLayoutView.findViewById(R.id.recycler_team_name);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(
                            v.getContext(), teamNameTv.getText(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            //itemLayoutView.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String particularTournament = tour_name_string_array.get(adapterPosition);
            mClickHandler.onClick(particularTournament);
        }*/



    }


}
