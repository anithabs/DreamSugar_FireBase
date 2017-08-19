package com.project.uwm.mydiabitiestracker.RecordFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.GlucoseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Collections;

public class GlucoseGraphFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    LineChart glouseLineChart;
    DatabaseManager dbManager;
    ArrayList<String> dateStringArray;
    ArrayList<Integer> glucoseLevel;
    ArrayList<GlucoseReadingObject> glucoseObjectArray;
    UserPreference pref;
    String userName;
    ArrayList<Float> dateArray, monthArray, yearArray;
    EditText  editTextYear, editTextMonth,editTextSD,editTextMean,editTextDosage;
    String year, month;
    Float fyear, fmonth;
    public TextView textDosage;


    public GlucoseGraphFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static GlucoseGraphFragment newInstance(String param1, String param2) {
        GlucoseGraphFragment fragment = new GlucoseGraphFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        editTextYear =(EditText) getActivity().findViewById(R.id.editTextSelectYear);
        editTextMonth =(EditText) getActivity().findViewById(R.id.editTextSelectMonth);
        editTextSD =(EditText) getActivity().findViewById(R.id.editTextSD);
       editTextMean =(EditText)getActivity().findViewById(R.id.editTextMean);

        editTextDosage =(EditText) getActivity().findViewById(R.id.et_dosageName);
        textDosage = (TextView) getActivity().findViewById(R.id.tv_dosageName);

        editTextDosage.setVisibility(View.INVISIBLE);
        textDosage.setVisibility(View.INVISIBLE);



       /* year = editTextYear.toString();
        month = editTextMonth.toString();

        fmonth = Float.parseFloat(year);
        fmonth = Float.parseFloat(month);*/


        pref = new UserPreference(this.getContext());
        userName = pref.getUserName();
        View  rootView = inflater.inflate(R.layout.fragment_glucose_graph, container, false);
        glouseLineChart = (LineChart)  rootView.findViewById(R.id.glucoseLineChart);
        dbManager = new DatabaseManager(getActivity());
        dateStringArray = new ArrayList<String>();
        glucoseLevel = new ArrayList<Integer>();
        yearArray = new ArrayList<Float>();
        monthArray = new ArrayList<Float>();
        dateArray = new ArrayList<Float>();

        glucoseObjectArray = dbManager.selectAllGlucoseDetails(userName);
        Collections.sort(glucoseObjectArray);

        // Standard Deviation

        double average =0;
        for (int i = 0; i < glucoseObjectArray.size(); i++)
        {
            average += glucoseObjectArray.get(i).getGlucose_level();
        }
        average = average/glucoseObjectArray.size();

        double sd = 0;
        for (int i = 0; i < glucoseObjectArray.size(); i++)
        {
            double value =  glucoseObjectArray.get(i).getGlucose_level() - average;
            double sqrt =( value* value) ;
            sd += sqrt;

        }
        sd = sd / glucoseObjectArray.size();
        double standardDeviation = Math.sqrt(sd);

        editTextSD.setText(String.valueOf(standardDeviation));
        editTextMean.setText(String.valueOf(average));

        editTextMean.setEnabled(false);
        editTextSD.setEnabled(false);

        for(int i = 0; i < glucoseObjectArray.size(); i++){
            glucoseLevel.add(glucoseObjectArray.get(i).getGlucose_level());
            dateStringArray.add(glucoseObjectArray.get(i).getGdate());
        }
        for (int i = 0; i< dateStringArray.size();i++){
            String[] part = dateStringArray.get(i).split("-");
            Float year =Float.parseFloat(part[0]);
            Float m =Float.parseFloat(part[1]);
            Float d =Float.parseFloat(part[2]);
            yearArray.add(year);
            monthArray.add(m);
            dateArray.add(d);
        }
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0;i<dateArray.size();i++){
            entries.add(new Entry(dateArray.get(i),glucoseLevel.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Glucose Graph");
        //dataSet.setColor(Color.RED);
        dataSet.setDrawFilled(true);


        LineData lineData = new LineData(dataSet);

        glouseLineChart.setData(lineData);
        glouseLineChart.invalidate();
        return  rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
