package com.example.abhi270595.footballmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardViewDataAdapter extends
        RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {


    private String jsonResult;
    private ArrayList<String> tour_name_string_array;
    private ArrayList<String> tour_description_string_array;

    private CardViewClickHandler mClickHandler;

    public CardViewDataAdapter(CardViewClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }

    public interface CardViewClickHandler {
        void onClick(String particularTournament);
    }

    public void setResultData(String result, ArrayList<String> name, ArrayList<String> description) {
        jsonResult = result;
        tour_name_string_array = name;
        tour_description_string_array = description;

        notifyDataSetChanged();
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();

        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.cardview_row, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.name.setText(tour_name_string_array.get(position));

        viewHolder.description.setText(tour_description_string_array.get(position));

        //viewHolder.singlestudent=stList.get(position);

    }

    @Override
    public int getItemCount() {
        if (tour_name_string_array == null) return 0;
        return tour_name_string_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public TextView name;
        public TextView description;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.recycler_tournament_name);
            description = (TextView) itemLayoutView.findViewById(R.id.recycler_tournament_description);
            itemLayoutView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(
                            v.getContext(), name.getText() + "" + description.getText() ,
                            Toast.LENGTH_SHORT).show();
                }
            });
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String particularTournament = tour_name_string_array.get(adapterPosition);
            mClickHandler.onClick(particularTournament);
        }



    }

}
