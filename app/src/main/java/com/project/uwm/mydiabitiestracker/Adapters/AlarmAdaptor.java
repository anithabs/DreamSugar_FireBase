/*
package com.project.uwm.mydiabitiestracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.EditRecords.UpdateExerciseRecords;
import com.project.uwm.mydiabitiestracker.Objects.AlarmObject;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.List;

*/
/**
 * Created by Anitha on 8/5/2017.
 *//*


public class AlarmAdaptor extends RecyclerView.Adapter<com.project.uwm.mydiabitiestracker.Adapters.ExerciseAdapter.ViewHolder> {

        private List<AlarmAdaptor> mAlarm;
        private Context mContext;

        public AlarmAdaptor(Context context, List<AlarmAdaptor> alarms){
            mAlarm = alarms;
            mContext = context;
        }
        private Context getContext(){
            return mContext;
        }
        @Override
        public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View alarmView = inflater.inflate(R.layout.item_exercise, parent, false);

            // Return a new holder instance
           ViewHolder viewHolder = new ViewHolder(alarmView);
           // return viewHolder;
            return null;
        }

        @Override
        public void onBindViewHolder(com.project.uwm.mydiabitiestracker.Adapters.AlarmAdaptor.ViewHolder viewHolder, final int position) {
            // Get the data model based on position
            final AlarmObject ero = mAlarm.get(position);

            // Set item views based on your views and data model
            final TextView dateTextView = viewHolder.edateTextView;
            dateTextView.setText(ero.getDate());
            final TextView timeTextView = viewHolder.etimeTextView;
            timeTextView.setText(ero.getTime());
            TextView eTypeTextView = viewHolder.etypeTextView;
            eTypeTextView.setText(ero.getExerciseType());
            TextView eDurationTextView = viewHolder.edurationTextView;
            eDurationTextView.setText(Integer.toString(ero.getDuration()));

            viewHolder.exerciseEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateExerciseRecords(timeTextView,dateTextView);
                }
            });
        }
        private void updateExerciseRecords(TextView timeView,TextView dateView) {
            String TIME_ENTRY= timeView.getText().toString();
            String DATE_ENTRY = dateView.getText().toString();
            Intent intent = new Intent(getContext(), UpdateExerciseRecords.class);
            intent.putExtra("EXERCISE_TIME", TIME_ENTRY);
            intent.putExtra("EXERCISE_DATE", DATE_ENTRY);
            mContext.startActivity(intent);
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView edateTextView;
            public TextView etimeTextView;
            public TextView etypeTextView;
            public TextView edurationTextView;
            public ImageButton exerciseEdit;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                edateTextView = (TextView) itemView.findViewById(R.id.rvs_date_evalue);
                etimeTextView = (TextView) itemView.findViewById(R.id.rvs_time_evalue);
                etypeTextView = (TextView) itemView.findViewById(R.id.rvs_exercise_type);
                edurationTextView = (TextView) itemView.findViewById(R.id.rvs_excerise_duration);
                exerciseEdit =(ImageButton) itemView.findViewById(R.id.rvedit_exercise_button);
            }
        }
    }
}
*/
