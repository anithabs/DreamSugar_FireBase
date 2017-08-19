package com.project.uwm.mydiabitiestracker.RecordFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.uwm.mydiabitiestracker.Adapters.ExerciseAdapter;
import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class ExerciseRecordsFragment extends Fragment /*implements View.OnClickListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener*/ {
    private BottomSheetDialog bottomSheetDialog;
    ArrayList<ExerciseReadingObject> exerciseList = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListTemp = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListFromDate = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListToDate = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListFromTime = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListToTime = new ArrayList<>();
    ArrayList<ExerciseReadingObject> exerListText = new ArrayList<>();

    DatabaseManager dbManager;
    private RecyclerView rvExercise;
    private RecyclerView.Adapter eAdaptor;
    private RecyclerView.LayoutManager eLayoutManager;
    private OnFragmentInteractionListener mListener;
    String userName;
    UserPreference pref;
    private static final int        DIALOG_DATE_PICKER  = 100;
    private int                     datePickerInput;
    public  TextView textFromGL,textToGL;
    public EditText editTextFromDate,editTextToDate,editTextFromTime,editTextToTime,editTextFromGL,editTextToGL;;
    private int day;
    private int month;
    private int year, hour,minute;
    TextView editTextSearch;


    public ExerciseRecordsFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.selectRecords);
        spinner.setSelection(2);
        editTextFromDate =(EditText) getActivity().findViewById(R.id.editTextFromDate);
        editTextToDate =(EditText) getActivity().findViewById(R.id.editTextToDate);
        editTextFromTime =(EditText) getActivity().findViewById(R.id.editTextFromTime);
        editTextToTime =(EditText)getActivity().findViewById(R.id.editTextToTime);
        editTextSearch =(EditText)getActivity().findViewById(R.id.editTextSearchKeyWord);
        textFromGL = (TextView) getActivity().findViewById(R.id.TextFromGlucoseLevel);
        textToGL =(TextView) getActivity().findViewById(R.id.TextToGlucoseLevel);

        editTextFromGL = (EditText) getActivity().findViewById(R.id.editTextFromGlucoseLevel);
        editTextToGL =(EditText) getActivity().findViewById(R.id.editTextToGlucoseLevel);
        textFromGL = (TextView) getActivity().findViewById(R.id.TextFromGlucoseLevel);
        textToGL =(TextView) getActivity().findViewById(R.id.TextToGlucoseLevel);


        editTextFromGL.setVisibility(View.INVISIBLE);
        editTextToGL.setVisibility(View.INVISIBLE);
        textFromGL.setVisibility(View.INVISIBLE);
        textToGL.setVisibility(View.INVISIBLE);

        View rootView = inflater.inflate(R.layout.fragment_exercise_records, container, false);
        rvExercise = (RecyclerView) rootView.findViewById(R.id.rvExercise);
        rvExercise.setHasFixedSize(true);


        eLayoutManager = new LinearLayoutManager(getActivity());
        Context context =getActivity();
        dbManager = new DatabaseManager(context);
        rvExercise.setLayoutManager(eLayoutManager);
        userName = pref.getUserName();

        final DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
        final TimePickerDialog.OnTimeSetListener from_timeListener,to_timeListener;
        from_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayFromTime();
                exerciseList = FromTime(hour,minute);
                eAdaptor = new ExerciseAdapter(getActivity(), exerciseList);
                rvExercise.setAdapter(eAdaptor);
                editTextToTime.setText("");
            }
        };
        to_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayToTime();
                updateDisplayFromTime();
                exerciseList = ToTime(hour,minute);
                eAdaptor = new ExerciseAdapter(getActivity(), exerciseList);
                rvExercise.setAdapter(eAdaptor);
            }
        };
        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth + 1;
                day = monthday;
                updateFromDisplay();
                exerciseList = FromDate(year,  month,  day);
                eAdaptor = new ExerciseAdapter(getActivity(), exerciseList);
                rvExercise.setAdapter(eAdaptor);
                editTextToTime.setText("");
                editTextFromTime.setText("");
                editTextToDate.setText("");
            }
        };
        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth+1;
                day = monthday;
                updateToDisplay();
                exerciseList = ToDate(year,  month,  day);
                eAdaptor = new ExerciseAdapter(getActivity(), exerciseList);
                rvExercise.setAdapter(eAdaptor);
                editTextToTime.setText("");
                editTextFromTime.setText("");
            }
        };

        editTextFromDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),from_dateListener,calender.get(calender.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        editTextToDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),to_dateListener,calender.get(calender.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        editTextToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(getActivity(),to_timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
        editTextFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                TimePickerDialog dialog = new TimePickerDialog(getActivity(),from_timeListener,calender.get(calender.HOUR),calender.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
        rvExercise.setLayoutManager(eLayoutManager);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String  firsthalf =null;
                String secondhalf =null;
                exerciseList.clear();
                exerListTemp = dbManager.selectAllExerciseDetails(userName);
                if(s.toString().contains(" ")) {
                    String[] stringArray = s.toString().split(" ");
                    if(stringArray.length > 2){
                        firsthalf = stringArray[0];
                        secondhalf = stringArray[2];
                    }
                }
                if(s.toString().contains(" and ") && secondhalf !=null  ){
                    for(int i = 0 ; i < exerListTemp.size() ; i++)
                        if(exerListTemp.get(i).getExerciseType().contains(firsthalf) && exerListTemp.get(i).getExerciseType().contains(secondhalf)) {
                            exerListText.add(exerListTemp.get(i));
                        }
                } else if(s.toString().contains(" or ") && secondhalf !=null  ){
                    for(int i = 0 ; i < exerListTemp.size() ; i++)
                        if(exerListTemp.get(i).getExerciseType().contains(firsthalf) || exerListTemp.get(i).getExerciseType().contains(secondhalf)) {
                            exerListText.add(exerListTemp.get(i));
                        }
                }else {
                    for(int i = 0 ; i < exerListTemp.size() ; i++)
                        if(exerListTemp.get(i).getExerciseType().contains(s.toString()) )
                            exerListText.add(exerListTemp.get(i));
                }
                exerciseList = exerListText;
                eAdaptor = new ExerciseAdapter(getActivity(), exerciseList);
                rvExercise.setAdapter(eAdaptor);

                editTextFromDate.setText("");
                editTextToDate.setText("");
                editTextFromTime.setText("");
                editTextToTime.setText("");

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        return rootView;
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

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void updateFromDisplay(){
        if(month <10 && day <10 ) {
            editTextFromDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            editTextFromDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            editTextFromDate.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            editTextFromDate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateToDisplay(){
        if(month <10 && day <10 ) {
            editTextToDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-0").append(day));
        }else if(month <10){
            editTextToDate.setText(new StringBuilder().append(year).append("-0").append(month).append("-").append(day));
        }else if(day <10){
            editTextToDate.setText(new StringBuilder().append(year).append("-").append(month).append("-0").append(day));
        }else
            editTextToDate.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    private void updateDisplayToTime(){
        if(hour <10 && minute <10){
            editTextToTime.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            editTextToTime.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            editTextToTime.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else {
            editTextToTime.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    private void updateDisplayFromTime(){
        if(hour <10 && minute <10){
            editTextFromTime.setText(new StringBuilder().append("0").append(hour).append(":0").append(minute));
        }else if(hour <10){
            editTextFromTime.setText(new StringBuilder().append("0").append(hour).append(":").append(minute));
        }else if(minute <10) {
            editTextFromTime.setText(new StringBuilder().append(hour).append(":0").append(minute));
        }else {
            editTextFromTime.setText(new StringBuilder().append(hour).append(":").append(minute));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public ArrayList<ExerciseReadingObject> FromDate(int year, int month, int date){
        String y, m, d;
        exerListFromDate.clear();
        ArrayList<ExerciseReadingObject> FromDate = new ArrayList<>();
        if(exerListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromDate = exerListText;
        }else
            FromDate = dbManager.selectAllExerciseDetails(userName);
        for( int i = 0 ; i < FromDate.size() ; i++){
            String sdate = FromDate.get(i).getDate();
            String[] dateArray = sdate.split("-");
            y = dateArray[0];
            m = dateArray[1];
            d = dateArray[2];
            if(Integer.parseInt(y) > year) {
                exerListFromDate.add(FromDate.get(i));
            }else if(Integer.parseInt(y) >= year && Integer.parseInt(m) > month ){
                exerListFromDate.add(FromDate.get(i));
            }else if (Integer.parseInt(y) >= year && Integer.parseInt(m) >= month && Integer.parseInt(d) >= date){
                exerListFromDate.add(FromDate.get(i));
            }
        }
        return  exerListFromDate;
    }
    public ArrayList<ExerciseReadingObject> ToDate(int year, int month, int date){
        String y, m, d;
        exerListToDate.clear();
        ArrayList<ExerciseReadingObject> ToDate = new ArrayList<>();
        if(exerListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToDate = exerListFromDate;
        }else if(exerListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToDate = exerListText;
        }else
            ToDate = dbManager.selectAllExerciseDetails(userName);

        for( int i = 0 ; i < ToDate.size() ; i++){
            String sdate = ToDate.get(i).getDate();
            String[] dateArray = sdate.split("-");
            y = dateArray[0];
            m = dateArray[1];
            d = dateArray[2];
            if(Integer.parseInt(y) < year) {
                exerListToDate.add(ToDate.get(i));
            }else if(Integer.parseInt(y) <= year && Integer.parseInt(m) < month ){
                exerListToDate.add(ToDate.get(i));
            }else if (Integer.parseInt(y) <= year && Integer.parseInt(m) <= month && Integer.parseInt(d) <= date){
                exerListToDate.add(ToDate.get(i));
            }
        }
        return  exerListToDate;
    }

    public ArrayList<ExerciseReadingObject> FromTime(int hour, int min){
        String h, m;
        exerListFromTime.clear();
        ArrayList<ExerciseReadingObject> FromTime = new ArrayList<>();
        if(exerListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            FromTime =exerListToDate;
        }else if(exerListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            FromTime = exerListFromDate;
        }else if(exerListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromTime = exerListText;
        }else
            FromTime = dbManager.selectAllExerciseDetails(userName);

        for( int i = 0 ; i < FromTime.size() ; i++){
            String sdate = FromTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) > hour) {
                exerListFromTime.add(FromTime.get(i));
            }else if(Integer.parseInt(h) >= hour && Integer.parseInt(m) >= min ){
                exerListFromTime.add(FromTime.get(i));
            }
        }
        return  exerListFromTime;
    }

    public ArrayList<ExerciseReadingObject> ToTime(int hour, int min){
        String h, m;
        exerListToTime.clear();
        ArrayList<ExerciseReadingObject> ToTime = new ArrayList<>();
        if(exerListFromTime.size() !=0 || editTextFromTime.getText().toString()!= null){
            ToTime =exerListFromTime;
        } else if(exerListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            ToTime =exerListToDate;
        }else if(exerListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToTime = exerListFromDate;
        }else if(exerListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToTime = exerListText;
        }else
            ToTime = dbManager.selectAllExerciseDetails(userName);

        for( int i = 0 ; i < ToTime.size() ; i++){
            String sdate = ToTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) < hour) {
                exerListToTime.add(ToTime.get(i));
            }else if(Integer.parseInt(h) <= hour && Integer.parseInt(m) < min ){
                exerListToTime.add(ToTime.get(i));
            }
        }
        return  exerListToTime;
    }

}
