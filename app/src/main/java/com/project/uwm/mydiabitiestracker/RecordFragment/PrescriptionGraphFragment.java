package com.project.uwm.mydiabitiestracker.RecordFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class PrescriptionGraphFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    LineChart prescriptionLineChart;
    DatabaseManager dbManager;
    ArrayList<String> dateStringArray ;
    ArrayList<Integer>  prescriptionmg;
    ArrayList<PrescriptionReadingObject>  prescriptionObjectArray;
    UserPreference pref;
    String userName;
    ArrayList<Float> dateArray, monthArray, yearArray;
    EditText  editTextYear, editTextMonth,editTextSD,editTextMean,editTextDosage;
    String year, month;
    Float fyear, fmonth;
    Set<String> prescriptionSet;
    TextView textDosage;

    public PrescriptionGraphFragment() {
        // Required empty public constructor
    }


    public static PrescriptionGraphFragment newInstance(String param1, String param2) {
        PrescriptionGraphFragment fragment = new PrescriptionGraphFragment();
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


        editTextYear = (EditText) getActivity().findViewById(R.id.editTextSelectYear);
        editTextMonth = (EditText) getActivity().findViewById(R.id.editTextSelectMonth);
        editTextSD = (EditText) getActivity().findViewById(R.id.editTextSD);
        editTextMean = (EditText)getActivity().findViewById(R.id.editTextMean);

        editTextDosage = (EditText) getActivity().findViewById(R.id.et_dosageName);
        textDosage = (TextView) getActivity().findViewById(R.id.tv_dosageName);
        editTextDosage.setVisibility(View.VISIBLE);
        textDosage.setVisibility(View.VISIBLE);


        pref = new UserPreference(this.getContext());
        userName = pref.getUserName();
        View  rootView = inflater.inflate(R.layout.fragment_prescription_graph, container, false);
        prescriptionLineChart = (LineChart)  rootView.findViewById(R.id.prescriptionLineChart);
        dbManager = new DatabaseManager(getActivity());

        dateStringArray = new ArrayList<String>();
        prescriptionmg = new ArrayList<Integer>();
        yearArray = new ArrayList<Float>();
        monthArray = new ArrayList<Float>();
        dateArray = new ArrayList<Float>();
        prescriptionSet = new HashSet<>();


        editTextDosage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                dateStringArray.clear();
                prescriptionmg.clear();
                yearArray.clear();
                monthArray.clear();
                dateArray.clear();
                prescriptionSet.clear();
                prescriptionObjectArray = dbManager.selectAllPrescriptionDetails(userName);
                Collections.sort( prescriptionObjectArray);

                for(int i = 0; i < prescriptionObjectArray.size();i++){
                    prescriptionSet.add(prescriptionObjectArray.get(i).getDrugName());
                }

                String[] prescritionname = prescriptionSet.toArray(new String[prescriptionSet.size()]);
                // Standard Deviation
                double average = 0;
                int count =0;
                for (int i = 0; i <  prescriptionObjectArray.size() ; i++) {
                    if((prescriptionObjectArray.get(i).getDrugName()).equals(s.toString())) {
                        average += prescriptionObjectArray.get(i).getDosage();
                        count++;
                    }
                }
                average = average/ count;

                double sd = 0;
                for (int i = 0; i <  prescriptionObjectArray.size(); i++) {
                    if((prescriptionObjectArray.get(i).getDrugName()).equals(s.toString())) {
                        double value = prescriptionObjectArray.get(i).getDosage() - average;
                        double sqrt = (value * value);
                        sd += sqrt;
                    }
                }

                sd = sd /  count;
                double standardDeviation = Math.sqrt(sd);

                editTextSD.setText(String.valueOf(standardDeviation));
                editTextMean.setText(String.valueOf(average));

                editTextMean.setEnabled(false);
                editTextSD.setEnabled(false);

                for(int i = 0; i < prescriptionObjectArray.size(); i++){
                    prescriptionmg.add(prescriptionObjectArray.get(i).getDosage());
                    dateStringArray.add(prescriptionObjectArray.get(i).getDate());
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
                int size = prescriptionSet.size();
                ArrayList<ArrayList<Entry>> entries = new ArrayList<ArrayList<Entry>>(size);
                // ArrayList<Entry> entry= new ArrayList<>();

                for (int j = 0; j< prescritionname.length;j++) {
                    String name = prescritionname[j];
                    ArrayList<Entry> entry= new ArrayList<>();
                    for (int i = 0; i < dateArray.size(); i++) {
                        String ArrayName = prescriptionObjectArray.get(i).getDrugName();
                        if(ArrayName.equals(name))
                            entry.add(new Entry(dateArray.get(i), prescriptionmg.get(i)));
                    }
                    entries.add(j,entry);
                }


   /*     LineDataSet dataset1 = new LineDataSet(entries.get(0),prescritionname[0] );*/
/*
       LineDataSet dataSet2 = new LineDataSet(entries.get(1), prescritionname[1]);*/
 /*       dataSet2.setColor(Color.RED);
        dataSet2.setDrawFilled(true);*/


        /*LineDataSet dataSet3 = new LineDataSet(entries.get(2), prescritionname[2]);*/
                LineDataSet dataSet =null;


                for (int i= 0; i<prescritionname.length;i++){
                    if((prescritionname[i]).equals(s.toString())){
                        dataSet = new LineDataSet(entries.get(i), prescritionname[i]) ;
                        //dataSet.setColor(Color.RED);
                        dataSet.setDrawFilled(true);
                    }
                }
                LineData lineData = new LineData();
                if( dataSet == null){
                    //do nothing
                }else {
                    lineData = new LineData(dataSet);
                }
                dbManager.close();
                prescriptionLineChart.setData(lineData);
                prescriptionLineChart.invalidate();

            }
        });

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
