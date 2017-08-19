package com.project.uwm.mydiabitiestracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.uwm.mydiabitiestracker.EditRecords.UpdatePrescriptionRecords;
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.R;

import java.util.List;

/**
 * Created by Anitha on 7/30/2017.
 */

public class PrescriptionAdaptor extends RecyclerView.Adapter<PrescriptionAdaptor.ViewHolder> {
        private List<PrescriptionReadingObject> mPrescription;
        private Context mContext;

        public PrescriptionAdaptor(Context context, List<PrescriptionReadingObject> prescription){
            mPrescription = prescription;
            mContext = context;
        }
        private Context getContext(){
            return mContext;
        }
        @Override
        public PrescriptionAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View prescriptionView = inflater.inflate(R.layout.item_prescription, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(prescriptionView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PrescriptionAdaptor.ViewHolder viewHolder, final int position) {
            // Get the data model based on position
            final PrescriptionReadingObject pre = mPrescription.get(position);

            // Set item views based on your views and data model
            final TextView dateTextView = viewHolder.dateTextView;
            dateTextView.setText(pre.getDate());
            final TextView timeTextView = viewHolder.timeTextView;
            timeTextView.setText(pre.getTime());
            TextView presTextView = viewHolder.prescriptionTextView;
            presTextView.setText(pre.getDrugName());
            TextView dosageTextView = viewHolder.dosageTextView;
            dosageTextView.setText(Integer.toString(pre.getDosage()));

            viewHolder.prescriptionEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatePrescriptionRecords(timeTextView,dateTextView);
                }
            });
        }
        private void updatePrescriptionRecords(TextView timeView,TextView dateView) {
            String TIME_ENTRY= timeView.getText().toString();
            String DATE_ENTRY = dateView.getText().toString();
            Intent intent = new Intent(getContext(), UpdatePrescriptionRecords.class);
            intent.putExtra("PRESCRIPTION_TIME", TIME_ENTRY);
            intent.putExtra("PRESCRIPTION_DATE", DATE_ENTRY);
            mContext.startActivity(intent);
        }
        @Override
        public int getItemCount() {
            return mPrescription.size();
        }
        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView dateTextView;
            public TextView timeTextView;
            public TextView dosageTextView;
            public TextView prescriptionTextView;
            public ImageButton prescriptionEdit;
            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                dateTextView = (TextView) itemView.findViewById(R.id.rvs_date_pvalue);
                timeTextView = (TextView) itemView.findViewById(R.id.rvs_time_pvalue);
                dosageTextView = (TextView) itemView.findViewById(R.id.rvs_pdosage);
                prescriptionTextView = (TextView) itemView.findViewById(R.id.rvs_prescription);
                prescriptionEdit =(ImageButton) itemView.findViewById(R.id.rveditprescriptionButton);
            }
        }
}
