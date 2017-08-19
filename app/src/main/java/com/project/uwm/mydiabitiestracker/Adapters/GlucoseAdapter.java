package com.project.uwm.mydiabitiestracker.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.EditRecords.UpdateGlucoseRecords;
import com.project.uwm.mydiabitiestracker.Objects.GlucoseReadingObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.List;

/**
 * Created by Anitha on 7/26/2017.
 */
public class GlucoseAdapter extends RecyclerView.Adapter<GlucoseAdapter.ViewHolder> {
    private List<GlucoseReadingObject> mGlucose;
    private Context mContext;

    public GlucoseAdapter(Context context, List<GlucoseReadingObject> glucose){
        mGlucose = glucose;
        mContext = context;
    }
    private Context getContext(){
        return mContext;
    }
    @Override
    public GlucoseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View glucoseView = inflater.inflate(R.layout.item_glucose, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(glucoseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GlucoseAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        GlucoseReadingObject glucose = mGlucose.get(position);

        // Set item views based on your views and data model
        final TextView gdateTextView = viewHolder.gdateTextView;
        gdateTextView.setText(glucose.getGdate());
        final TextView gtimeTextView = viewHolder.gtimeTextView;
        gtimeTextView.setText(glucose.getGtime());
        TextView glevel = viewHolder.glevel;
        glevel.setText(Integer.toString(glucose.getGlucose_level()));
        TextView greadingtaken = viewHolder.greadingtaken;
        greadingtaken.setText(glucose.getReading_taken());
        viewHolder.glucoseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGlucoseRecords(gtimeTextView,gdateTextView);
            }
        });
    }
    private void updateGlucoseRecords(TextView timeView,TextView dateView) {
        String TIME_ENTRY= timeView.getText().toString();
        String DATE_ENTRY = dateView.getText().toString();
        Intent intent = new Intent(getContext(), UpdateGlucoseRecords.class);
        intent.putExtra("GLUCOSE_TIME", TIME_ENTRY);
        intent.putExtra("GLUCOSE_DATE", DATE_ENTRY);
        mContext.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return mGlucose.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView gdateTextView;
        public TextView gtimeTextView;
        public TextView glevel;
        public TextView greadingtaken;
        public ImageButton glucoseEdit;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            gdateTextView = (TextView) itemView.findViewById(R.id.rvs_date_gvalue);
            gtimeTextView = (TextView) itemView.findViewById(R.id.rvs_time_gvalue);
            glevel = (TextView) itemView.findViewById(R.id.rvs_glevel);
            greadingtaken = (TextView) itemView.findViewById(R.id.rvs_greadingTaken);
            glucoseEdit = (ImageButton) itemView.findViewById(R.id.rveditGlucoseButton);
        }
    }
}
