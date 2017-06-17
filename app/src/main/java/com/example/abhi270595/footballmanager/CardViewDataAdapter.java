package com.example.abhi270595.footballmanager;

import java.util.List;

import android.content.Context;
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

    //private List<Student> stList;
    private String jsonResult;
    private String[] tour_name_string_array;
    private String[] tour_description_string_array;

    public CardViewDataAdapter() {
        //this.stList = students;

    }

    public void setResultData(String result, String[] name, String[] description) {
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

        viewHolder.name.setText(tour_name_string_array[position]);

        viewHolder.description.setText(tour_description_string_array[position]);

        //viewHolder.singlestudent=stList.get(position);

    }

    @Override
    public int getItemCount() {
        if (tour_name_string_array == null) return 0;
        return tour_name_string_array.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;

        //public Student singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.recycler_tournament_name);
            description = (TextView) itemLayoutView.findViewById(R.id.recycler_tournament_description);
            /*itemLayoutView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(
                            v.getContext(),
                            "Data : \n" + singlestudent.getName() + " \n"
                                    + singlestudent.getEmailId(),
                            Toast.LENGTH_SHORT).show();

                }
            });*/

        }

    }

}
