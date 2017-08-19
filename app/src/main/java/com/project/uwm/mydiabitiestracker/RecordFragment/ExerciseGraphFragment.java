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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Collections;


public class ExerciseGraphFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    BarChart exerciseLineChart;
    DatabaseManager dbManager;
    ArrayList<String> dateStringArray;
    ArrayList<Integer> glucoseLevel;
    ArrayList<ExerciseReadingObject> exerciseObjectArray;
    UserPreference pref;
    String userName;
    ArrayList<Float> dateArray, monthArray, yearArray;
    EditText  editTextYear, editTextMonth,editTextSD,editTextMean,editTextDosage;
    String year, month;
    Float fyear, fmonth;
    TextView textDosage;

    public ExerciseGraphFragment() {
        // Required empty public constructor
    }


    public static ExerciseGraphFragment newInstance(String param1, String param2) {
        ExerciseGraphFragment fragment = new ExerciseGraphFragment();
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

        pref = new UserPreference(this.getContext());
        userName = pref.getUserName();

        View  rootView = inflater.inflate(R.layout.fragment_exercise_graph, container, false);
        exerciseLineChart = (BarChart)  rootView.findViewById(R.id.exerciseLineChart);
        dbManager = new DatabaseManager(getActivity());

        dateStringArray = new ArrayList<String>();
        glucoseLevel = new ArrayList<Integer>();
        yearArray = new ArrayList<Float>();
        monthArray = new ArrayList<Float>();
        dateArray = new ArrayList<Float>();
        exerciseObjectArray = new ArrayList<ExerciseReadingObject>();

        exerciseObjectArray = dbManager.selectAllExerciseDetails(userName);
        Collections.sort(exerciseObjectArray);

        // Standard Deviation

        double average =0;
        for (int i = 0; i < exerciseObjectArray.size(); i++)
        {
            average += exerciseObjectArray.get(i).getDuration();
        }
        average = average/exerciseObjectArray.size();

        double sd = 0;
        for (int i = 0; i < exerciseObjectArray.size(); i++)
        {
            double value =  exerciseObjectArray.get(i).getDuration() - average;
            double sqrt =( value* value) ;
            sd += sqrt;
        }
        sd = sd / exerciseObjectArray.size();
        double standardDeviation = Math.sqrt(sd);

        editTextSD.setText(String.valueOf(standardDeviation));
        editTextMean.setText(String.valueOf(average));
        editTextMean.setEnabled(false);
        editTextSD.setEnabled(false);

        for(int i = 0; i < exerciseObjectArray.size(); i++){
            glucoseLevel.add(exerciseObjectArray.get(i).getDuration());
            dateStringArray.add(exerciseObjectArray.get(i).getDate());
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
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0;i<dateArray.size();i++){
            entries.add(new BarEntry(dateArray.get(i),glucoseLevel.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Exercise Graph");
        //dataSet.setColor(Color.RED);


        BarData lineData = new BarData(dataSet);

        exerciseLineChart.setData(lineData);
        exerciseLineChart.invalidate();
        exerciseLineChart.zoomIn();
        exerciseLineChart.zoomOut();

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