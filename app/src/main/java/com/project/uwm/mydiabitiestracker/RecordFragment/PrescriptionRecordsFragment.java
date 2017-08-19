package com.project.uwm.mydiabitiestracker.RecordFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.uwm.mydiabitiestracker.Adapters.PrescriptionAdaptor;
import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class PrescriptionRecordsFragment extends Fragment /*implements View.OnClickListener,DatePickerDialog.OnDateSetListener*/ {
    private GlucoseRecordsFragment.OnFragmentInteractionListener gmListener;
    ArrayList<PrescriptionReadingObject> presList = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListTemp = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListFromDate = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListToDate = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListFromTime = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListToTime = new ArrayList<>();
    ArrayList<PrescriptionReadingObject> presListText = new ArrayList<>();

    DatabaseManager dbManager;
    private RecyclerView rvPres;
    private RecyclerView.Adapter pAdaptor;
    private RecyclerView.LayoutManager pLayoutManager;
    private Switch checkSwitch;
    private CheckBox weekChkBox;
    private CheckBox dayChkBox;
    String userName;
    UserPreference pref;

    public  TextView textFromGL,textToGL;
    public EditText editTextFromDate,editTextToDate,editTextFromTime,editTextToTime,editTextFromGL,editTextToGL;
    private int day;
    private int month;
    private int year, hour,minute;
    TextView editTextSearch;

    private OnFragmentInteractionListener mListener;

    public PrescriptionRecordsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.selectRecords);
        spinner.setSelection(3);
        editTextFromDate =(EditText) getActivity().findViewById(R.id.editTextFromDate);
        editTextToDate =(EditText) getActivity().findViewById(R.id.editTextToDate);
        editTextFromTime =(EditText) getActivity().findViewById(R.id.editTextFromTime);
        editTextToTime =(EditText)getActivity().findViewById(R.id.editTextToTime);
        editTextSearch =(EditText)getActivity().findViewById(R.id.editTextSearchKeyWord);
        View rootView= inflater.inflate(R.layout.fragment_prescription_records, container, false);

        editTextFromGL = (EditText) getActivity().findViewById(R.id.editTextFromGlucoseLevel);
        editTextToGL =(EditText) getActivity().findViewById(R.id.editTextToGlucoseLevel);
        textFromGL = (TextView) getActivity().findViewById(R.id.TextFromGlucoseLevel);
        textToGL =(TextView) getActivity().findViewById(R.id.TextToGlucoseLevel);

        editTextFromGL.setVisibility(View.INVISIBLE);
        editTextToGL.setVisibility(View.INVISIBLE);
        textFromGL.setVisibility(View.INVISIBLE);
        textToGL.setVisibility(View.INVISIBLE);

        rvPres = (RecyclerView) rootView.findViewById(R.id.rvprescription);
        rvPres.setHasFixedSize(true);
        editTextSearch =(EditText)getActivity().findViewById(R.id.editTextSearchKeyWord);
     /*   weekChkBox =(CheckBox) getActivity().findViewById(R.id.cbWeek);
        dayChkBox =(CheckBox) getActivity().findViewById(R.id.cbDay);*/
        userName = pref.getUserName();

        pLayoutManager = new LinearLayoutManager(getActivity());
        Context context =getActivity();
        dbManager = new DatabaseManager(context);
        rvPres.setLayoutManager(pLayoutManager);

        final DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
        final TimePickerDialog.OnTimeSetListener from_timeListener,to_timeListener;
        from_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayFromTime();
                presList = FromTime(hour,minute);
                pAdaptor = new PrescriptionAdaptor(getActivity(), presList);
                rvPres.setAdapter(pAdaptor);
                editTextToTime.setText("");
            }
        };
        to_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayToTime();
                presList = ToTime(hour,minute);
                pAdaptor = new PrescriptionAdaptor(getActivity(), presList);
                rvPres.setAdapter(pAdaptor);
            }
        };
        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth+1;
                day = monthday;
                updateFromDisplay();
                presList = FromDate(year,  month,  day);
                pAdaptor = new PrescriptionAdaptor(getActivity(), presList);
                rvPres.setAdapter(pAdaptor);
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
                presList = ToDate(year,  month,  day);
                pAdaptor = new PrescriptionAdaptor(getActivity(), presList);
                rvPres.setAdapter(pAdaptor);
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
        rvPres.setLayoutManager(pLayoutManager);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                String  firsthalf =null;
                String secondhalf =null;
                presList.clear();
                presListTemp = dbManager.selectAllPrescriptionDetails(userName);
                if(s.toString().contains(" ")) {
                    String[] stringArray = s.toString().split(" ");
                    if(stringArray.length > 2){
                        firsthalf = stringArray[0];
                        secondhalf = stringArray[2];
                    }
                }
                if(s.toString().contains(" and ") && secondhalf !=null  ){
                    for(int i = 0 ; i < presListTemp.size() ; i++)
                        if(presListTemp.get(i).getDrugName().contains(firsthalf) && presListTemp.get(i).getDrugName().contains(secondhalf)) {
                            presListText.add(presListTemp.get(i));
                        }
                } else if(s.toString().contains(" or ") && secondhalf !=null  ){
                    for(int i = 0 ; i < presListTemp.size() ; i++)
                        if(presListTemp.get(i).getDrugName().contains(firsthalf) || presListTemp.get(i).getDrugName().contains(secondhalf)) {
                            presListText.add(presListTemp.get(i));
                        }
                }else {
                    for(int i = 0 ; i < presListTemp.size() ; i++)
                        if(presListTemp.get(i).getDrugName().contains(s.toString()) )
                            presListText.add(presListTemp.get(i));
                }
                presList = presListText;
                pAdaptor = new PrescriptionAdaptor(getActivity(), presList);
                rvPres.setAdapter(pAdaptor);

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
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public ArrayList<PrescriptionReadingObject> FromDate(int year, int month, int date){
        String y, m, d;
        presListFromDate.clear();
        ArrayList<PrescriptionReadingObject> FromDate = new ArrayList<>();
        if(presListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromDate = presListText;
        }else
            FromDate = dbManager.selectAllPrescriptionDetails(userName);
        for( int i = 0 ; i < FromDate.size() ; i++){
            String sdate = FromDate.get(i).getDate();
            String[] dateArray = sdate.split("-");
            y = dateArray[0];
            m = dateArray[1];
            d = dateArray[2];
            if(Integer.parseInt(y) > year) {
                presListFromDate.add(FromDate.get(i));
            }else if(Integer.parseInt(y) >= year && Integer.parseInt(m) > month ){
                presListFromDate.add(FromDate.get(i));
            }else if (Integer.parseInt(y) >= year && Integer.parseInt(m) >= month && Integer.parseInt(d) >= date){
                presListFromDate.add(FromDate.get(i));
            }
        }
        return  presListFromDate;
    }
    public ArrayList<PrescriptionReadingObject> ToDate(int year, int month, int date){
        String y, m, d;
        presListToDate.clear();
        ArrayList<PrescriptionReadingObject> ToDate = new ArrayList<>();
        if(presListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToDate = presListFromDate;
        }else if(presListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToDate = presListText;
        }else
            ToDate = dbManager.selectAllPrescriptionDetails(userName);

        for( int i = 0 ; i < ToDate.size() ; i++){
            String sdate = ToDate.get(i).getDate();
            String[] dateArray = sdate.split("-");
            y = dateArray[0];
            m = dateArray[1];
            d = dateArray[2];
            if(Integer.parseInt(y) < year) {
                presListToDate.add(ToDate.get(i));
            }else if(Integer.parseInt(y) <= year && Integer.parseInt(m) < month ){
                presListToDate.add(ToDate.get(i));
            }else if (Integer.parseInt(y) <= year && Integer.parseInt(m) <= month && Integer.parseInt(d) <= date){
                presListToDate.add(ToDate.get(i));
            }
        }
        return  presListToDate;
    }

    public ArrayList<PrescriptionReadingObject> FromTime(int hour, int min){
        String h, m;
        presListFromTime.clear();
        ArrayList<PrescriptionReadingObject> FromTime = new ArrayList<>();
        if(presListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            FromTime =presListToDate;
        }else if(presListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            FromTime = presListFromDate;
        }else if(presListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromTime = presListText;
        }else
            FromTime = dbManager.selectAllPrescriptionDetails(userName);

        for( int i = 0 ; i < FromTime.size() ; i++){
            String sdate = FromTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) > hour) {
                presListFromTime.add(FromTime.get(i));
            }else if(Integer.parseInt(h) >= hour && Integer.parseInt(m) >= min ){
                presListFromTime.add(FromTime.get(i));
            }
        }
        return  presListFromTime;
    }

    public ArrayList<PrescriptionReadingObject> ToTime(int hour, int min){
        String h, m;
        presListToTime.clear();
        ArrayList<PrescriptionReadingObject> ToTime = new ArrayList<>();
        if(presListFromTime.size() !=0 || editTextFromTime.getText().toString()!= null){
            ToTime =presListFromTime;
        } else if(presListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            ToTime =presListToDate;
        }else if(presListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToTime = presListFromDate;
        }else if(presListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToTime = presListText;
        }else
            ToTime = dbManager.selectAllPrescriptionDetails(userName);

        for( int i = 0 ; i < ToTime.size() ; i++){
            String sdate = ToTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) < hour) {
                presListToTime.add(ToTime.get(i));
            }else if(Integer.parseInt(h) <= hour && Integer.parseInt(m) < min ){
                presListToTime.add(ToTime.get(i));
            }
        }
        return  presListToTime;
    }

}
