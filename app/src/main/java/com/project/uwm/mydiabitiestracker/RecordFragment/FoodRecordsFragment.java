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

import com.project.uwm.mydiabitiestracker.Adapters.FoodAdapter;
import com.project.uwm.mydiabitiestracker.DatabaseManager;
import com.project.uwm.mydiabitiestracker.Objects.FoodConsumedObject;
import com.project.uwm.mydiabitiestracker.Objects.UserPreference;
import com.project.uwm.mydiabitiestracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class FoodRecordsFragment extends Fragment  {
    private BottomSheetDialog bottomSheetDialog;
    private OnFragmentInteractionListener mListener;

    ArrayList<FoodConsumedObject> foodList = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListTemp = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListFromDate = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListToDate = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListFromTime = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListToTime = new ArrayList<>();
    ArrayList<FoodConsumedObject> foodListText = new ArrayList<>();

    DatabaseManager dbManager;
    private RecyclerView rvFood;
    private RecyclerView.Adapter fAdaptor;
    private RecyclerView.LayoutManager fLayoutManager;
    String userName;
    UserPreference pref;
    public  TextView textFromGL,textToGL;
    public EditText editTextFromDate,editTextToDate,editTextFromTime,editTextToTime,editTextFromGL,editTextToGL;
    private int day;
    private int month;
    private int year, hour,minute;
    TextView editTextSearch;


    public FoodRecordsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.selectRecords);
        spinner.setSelection(0);
        editTextFromGL = (EditText) getActivity().findViewById(R.id.editTextFromGlucoseLevel);
        editTextToGL =(EditText) getActivity().findViewById(R.id.editTextToGlucoseLevel);
        textFromGL = (TextView) getActivity().findViewById(R.id.TextFromGlucoseLevel);
        textToGL =(TextView) getActivity().findViewById(R.id.TextToGlucoseLevel);


        editTextFromGL.setVisibility(View.INVISIBLE);
        editTextToGL.setVisibility(View.INVISIBLE);
        textFromGL.setVisibility(View.INVISIBLE);
        textToGL.setVisibility(View.INVISIBLE);
        editTextFromDate =(EditText) getActivity().findViewById(R.id.editTextFromDate);
        editTextToDate =(EditText) getActivity().findViewById(R.id.editTextToDate);
        editTextFromTime =(EditText) getActivity().findViewById(R.id.editTextFromTime);
        editTextToTime =(EditText)getActivity().findViewById(R.id.editTextToTime);
        editTextSearch =(EditText)getActivity().findViewById(R.id.editTextSearchKeyWord);
        View rootView = inflater.inflate(R.layout.fragment_food_records, container, false);
        rvFood = (RecyclerView) rootView.findViewById(R.id.rvFoods);
        rvFood.setHasFixedSize(true);



        fLayoutManager = new LinearLayoutManager(getActivity());
        Context context =getActivity();
        dbManager = new DatabaseManager(context);
        userName = pref.getUserName();
        final DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
        final TimePickerDialog.OnTimeSetListener from_timeListener,to_timeListener;
        from_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayFromTime();
                foodList = FromTime(hour,minute);
                fAdaptor = new FoodAdapter(getActivity(), foodList);
                rvFood.setAdapter(fAdaptor);
                editTextToTime.setText("");
            }
        };
        to_timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                hour = hr;
                minute = min ;
                updateDisplayToTime();
                foodList = ToTime(hour,minute);
                fAdaptor = new FoodAdapter(getActivity(), foodList);
                rvFood.setAdapter(fAdaptor);

            }
        };
        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yr, int mnth, int monthday) {
                year =yr;
                month = mnth+1;
                day = monthday;
                updateFromDisplay();
                foodList = FromDate(year,  month,  day);
                fAdaptor = new FoodAdapter(getActivity(), foodList);
                rvFood.setAdapter(fAdaptor);
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
                foodList = ToDate(year,  month,  day);
                fAdaptor = new FoodAdapter(getActivity(), foodList);
                rvFood.setAdapter(fAdaptor);
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
        rvFood.setLayoutManager(fLayoutManager);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String  firsthalf =null;
                String secondhalf =null;
                foodList.clear();
                foodListTemp = dbManager.selectAllFoodDetails(userName);
               if(s.toString().contains(" ")) {
                   String[] stringArray = s.toString().split(" ");
                   if(stringArray.length > 2){
                       firsthalf = stringArray[0];
                       secondhalf = stringArray[2];
                   }
                }
                if(s.toString().contains(" and ") && secondhalf !=null  ){
                    for(int i = 0 ; i < foodListTemp.size() ; i++)
                        if(foodListTemp.get(i).getTypeOfFood().contains(firsthalf) && foodListTemp.get(i).getTypeOfFood().contains(secondhalf)) {
                            foodListText.add(foodListTemp.get(i));
                        }
                } else if(s.toString().contains(" or ") && secondhalf !=null  ){
                    for(int i = 0 ; i < foodListTemp.size() ; i++)
                        if(foodListTemp.get(i).getTypeOfFood().contains(firsthalf) || foodListTemp.get(i).getTypeOfFood().contains(secondhalf)) {
                            foodListText.add(foodListTemp.get(i));
                        }
                }else {
                    for(int i = 0 ; i < foodListTemp.size() ; i++)
                        if(foodListTemp.get(i).getTypeOfFood().contains(s.toString()) )
                            foodListText.add(foodListTemp.get(i));
                }
                foodList = foodListText;
                fAdaptor = new FoodAdapter(getActivity(), foodList);
                rvFood.setAdapter(fAdaptor);

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

    public ArrayList<FoodConsumedObject> FromDate(int year, int month, int date){
        String y, m, d;
        foodListFromDate.clear();
        ArrayList<FoodConsumedObject> FromDate = new ArrayList<>();
        if(foodListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromDate = foodListText;
        }else
            FromDate = dbManager.selectAllFoodDetails(userName);
         for( int i = 0 ; i < FromDate.size() ; i++){
             String sdate = FromDate.get(i).getDate();
             String[] dateArray = sdate.split("-");
             y = dateArray[0];
             m = dateArray[1];
             d = dateArray[2];
             if(Integer.parseInt(y) > year) {
                 foodListFromDate.add(FromDate.get(i));
             }else if(Integer.parseInt(y) >= year && Integer.parseInt(m) > month ){
                 foodListFromDate.add(FromDate.get(i));
             }else if (Integer.parseInt(y) >= year && Integer.parseInt(m) >= month && Integer.parseInt(d) >= date){
                 foodListFromDate.add(FromDate.get(i));
             }
         }
        return  foodListFromDate;
    }
    public ArrayList<FoodConsumedObject> ToDate(int year, int month, int date){
        String y, m, d;
        foodListToDate.clear();
        ArrayList<FoodConsumedObject> ToDate = new ArrayList<>();
        if(foodListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToDate = foodListFromDate;
        }else if(foodListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToDate = foodListText;
        }else
            ToDate = dbManager.selectAllFoodDetails(userName);

        for( int i = 0 ; i < ToDate.size() ; i++){
            String sdate = ToDate.get(i).getDate();
            String[] dateArray = sdate.split("-");
            y = dateArray[0];
            m = dateArray[1];
            d = dateArray[2];
            if(Integer.parseInt(y) < year) {
                foodListToDate.add(ToDate.get(i));
            }else if(Integer.parseInt(y) <= year && Integer.parseInt(m) < month ){
                foodListToDate.add(ToDate.get(i));
            }else if (Integer.parseInt(y) <= year && Integer.parseInt(m) <= month && Integer.parseInt(d) <= date){
                foodListToDate.add(ToDate.get(i));
            }
        }
        return  foodListToDate;
    }

    public ArrayList<FoodConsumedObject> FromTime(int hour, int min){
        String h, m;
        foodListFromTime.clear();
        ArrayList<FoodConsumedObject> FromTime = new ArrayList<>();
        if(foodListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            FromTime =foodListToDate;
        }else if(foodListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            FromTime = foodListFromDate;
        }else if(foodListText.size() !=0 || editTextSearch.getText().toString()!= null){
            FromTime = foodListText;
        }else
            FromTime = dbManager.selectAllFoodDetails(userName);

        for( int i = 0 ; i < FromTime.size() ; i++){
            String sdate = FromTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) > hour) {
                foodListFromTime.add(FromTime.get(i));
            }else if(Integer.parseInt(h) >= hour && Integer.parseInt(m) >= min ){
                foodListFromTime.add(FromTime.get(i));
            }
        }
        return  foodListFromTime;
    }

    public ArrayList<FoodConsumedObject> ToTime(int hour, int min){
        String h, m;
        foodListToTime.clear();
        ArrayList<FoodConsumedObject> ToTime = new ArrayList<>();
        if(foodListFromTime.size() !=0 || editTextFromTime.getText().toString()!= null){
            ToTime =foodListFromTime;
        } else if(foodListToDate.size() !=0 || editTextToDate.getText().toString()!= null){
            ToTime =foodListToDate;
        }else if(foodListFromDate.size() !=0 ||  editTextFromDate.getText().toString()!= null) {
            ToTime = foodListFromDate;
        }else if(foodListText.size() !=0 || editTextSearch.getText().toString()!= null){
            ToTime = foodListText;
        }else
            ToTime = dbManager.selectAllFoodDetails(userName);

        for( int i = 0 ; i < ToTime.size() ; i++){
            String sdate = ToTime.get(i).getTime();
            String[] dateArray = sdate.split(":");
            h = dateArray[0];
            m = dateArray[1];
            if(Integer.parseInt(h) < hour) {
                foodListToTime.add(ToTime.get(i));
            }else if(Integer.parseInt(h) <= hour && Integer.parseInt(m) < min ){
                foodListToTime.add(ToTime.get(i));
            }
        }
        return  foodListToTime;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

}
