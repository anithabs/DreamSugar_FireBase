package com.project.uwm.mydiabitiestracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.EditRecords.UpdateFoodRecords;
import com.project.uwm.mydiabitiestracker.R;
import com.project.uwm.mydiabitiestracker.Objects.FoodConsumedObject;
import java.util.List;

/**
 * Created by Anitha on 7/26/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<FoodConsumedObject> mFoods;
    private Context mContext;

    public FoodAdapter(Context context, List<FoodConsumedObject> foods){
        mFoods = foods;
        mContext = context;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View foodView = inflater.inflate(R.layout.item_food, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final FoodConsumedObject food = mFoods.get(position);

        // Set item views based on your views and data model
        final TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText(food.getDate());
        final TextView timeTextView = viewHolder.timeTextView;
        timeTextView.setText(food.getTime());
        TextView tofTextView = viewHolder.tofTextView;
        tofTextView.setText(food.getTypeOfFood());
        TextView aofTextView = viewHolder.aofTextView;
        aofTextView.setText(Integer.toString(food.getAmountOfFood()));
        viewHolder.foodEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFoodRecords(timeTextView,dateTextView);
            }
        });
    }
    private void updateFoodRecords(TextView timeView,TextView dateView) {
        String TIME_ENTRY= timeView.getText().toString();
        String DATE_ENTRY = dateView.getText().toString();
        Intent intent = new Intent(getContext(), UpdateFoodRecords.class);
        intent.putExtra("FOOD_TIME", TIME_ENTRY);
        intent.putExtra("FOOD_DATE", DATE_ENTRY);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView tofTextView;
        public TextView aofTextView;
        public ImageButton foodEdit;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.rvs_date_fvalue);
            timeTextView = (TextView) itemView.findViewById(R.id.rvs_time_fvalue);
            tofTextView = (TextView) itemView.findViewById(R.id.rvs_tof_fvalue);
            aofTextView = (TextView) itemView.findViewById(R.id.rvs_aof_fvalue);
            foodEdit =(ImageButton) itemView.findViewById(R.id.rveditFoodButton);
        }
    }
}