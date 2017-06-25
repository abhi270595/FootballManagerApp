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
 * Created by chunga on 25/6/17.
 */
public class NotificationDataAdapter extends
        RecyclerView.Adapter<NotificationDataAdapter.ViewHolder>{

    private String jsonResult;
    private ArrayList<String> notification_array;

    private CardViewClickHandler mClickHandler;

    public NotificationDataAdapter(CardViewClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }

    public interface CardViewClickHandler {
        void onClick(String particularTournament);
    }

    public void setResultData(String result, ArrayList<String> name) {
        jsonResult = result;
        notification_array = name;

        notifyDataSetChanged();
    }

    public void removeData(int adapterPosition) {
        notification_array.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, notification_array.size());
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();

        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.notification_row, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.name.setText(notification_array.get(position));

    }

    @Override
    public int getItemCount() {
        if (notification_array == null) return 0;
        return notification_array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView description;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.recycler_notification);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(
                            v.getContext(), name.getText() + "" + description.getText(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String particularTournament = notification_array.get(adapterPosition);
            mClickHandler.onClick(particularTournament);
        }



    }
}
