package com.project.uwm.mydiabitiestracker.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Anitha on 7/25/2017.
 */

public class UserPreference {

    private static String userNameField;
    private static String signout;
    private static String passwordField;
    private static String exerciseField;
    private static String durationField;
    private static String typeoffoodField;
    private static String amountOfFoodField;
    private static String glucoseLevelField;
    private static String readingTakenField;
    private static String drugNameField;
    private static String dosageField;
    private static String rtestedBGLField;
    private static String rprescriptionField;
    private static String rdietField;
    private static String rexerciseField;
    private static final String SIGN_OUT ="signOut";
    private static final String USER_KEY = "userKey";
    private static final String PASSWORD_KEY ="passwordKey";
    private static final String EXERCISE_TYPE_KEY ="exerciseType";
    private static final String DURATION_KEY = "duration";
    private static final String TYPEOFFOOD_KEY = "typeOfFood";
    private static final String AMOUNTOFFOOD_KEY = "amountOfFood";
    private static final String DRUGNAME_KEY = "drugName";
    private static final String DOSAGE_KEY = "dosage";
    private static final String RTESTEDBGVALUE_KEY = "testedBGValue";
    private static final String REXERCISE_KEY = "exercise";
    private static final String RPRESCRIPTION_KEY = "prescription";
    private static final String RDIET_KEY = "diet";

    public UserPreference(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        setUserName(pref.getString(USER_KEY,"name"));
        setPassword(pref.getString(PASSWORD_KEY,"password"));
        setAmountOfFoodField(pref.getString(AMOUNTOFFOOD_KEY,"23"));
        setTypeOfFoodField(pref.getString(TYPEOFFOOD_KEY,"tof"));
        setDosageField(pref.getString(DOSAGE_KEY,"dose"));
        setDurationField(pref.getString(DURATION_KEY,"60"));
        setExerciseField(pref.getString(EXERCISE_TYPE_KEY,"exer"));
        setDrugNameField(pref.getString(DRUGNAME_KEY,"drug"));
        setRdietField(pref.getString(RDIET_KEY,"rdiet"));
        setRexerciseField(pref.getString(REXERCISE_KEY,"rexer"));
        setRprescriptionField(pref.getString(RPRESCRIPTION_KEY,"pres"));
        setRtestedBGLField(pref.getString(RTESTEDBGVALUE_KEY,"260"));
        setSignout(pref.getString(SIGN_OUT,"sign"));
    }

    public void setPreference(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(USER_KEY, userNameField);
        editor.putString(PASSWORD_KEY,passwordField);
        editor.putString(EXERCISE_TYPE_KEY,exerciseField);
        editor.putString(DURATION_KEY,durationField);
        editor.putString(TYPEOFFOOD_KEY,typeoffoodField);
        editor.putString(AMOUNTOFFOOD_KEY,amountOfFoodField);
        editor.putString(DRUGNAME_KEY,drugNameField);
        editor.putString(DOSAGE_KEY,dosageField);
        editor.putString(RTESTEDBGVALUE_KEY,rtestedBGLField);
        editor.putString(REXERCISE_KEY,rexerciseField);
        editor.putString(RPRESCRIPTION_KEY,rprescriptionField);
        editor.putString(RDIET_KEY,rdietField);
        editor.putString(SIGN_OUT,signout);
        editor.commit();
    }

    public static void setPassword(String password) {
        userNameField= password;
    }
    public static void setUserName(String userName) {
        passwordField= userName;
    }

    public static void setSignout(String signout) {
        UserPreference.signout = signout;
    }
    public static String getSignout() {
        return signout;
    }

    public static String getPassword() {
        return passwordField;
    }
    public static String getUserName() {
        return userNameField;
    }

    public static void setAmountOfFoodField(String amountOfFoodField) {
        UserPreference.amountOfFoodField = amountOfFoodField;
    }
    public static String getAmountOfFoodField() {
        return amountOfFoodField;
    }

    public static void setTypeOfFoodField(String typeoffood) {
        typeoffoodField = typeoffood;
    }
    public static String getTypeOfFoodField() {
        return typeoffoodField;
    }

    public static String getDosageField() {
        return dosageField;
    }
    public static void setDosageField(String dosageField) {
        UserPreference.dosageField = dosageField;
    }

    public static String getDrugNameField() {
        return drugNameField;
    }
    public static void setDrugNameField(String drugNameField) {
        UserPreference.drugNameField = drugNameField;
    }

    public static String getDurationField() {
        return durationField;
    }
    public static void setDurationField(String durationField) {
        UserPreference.durationField = durationField;
    }

    public static String getExerciseField() {
        return exerciseField;
    }
    public static void setExerciseField(String exerciseField) {
        UserPreference.exerciseField = exerciseField;
    }

    public static String getGlucoseLevelField() {
        return glucoseLevelField;
    }
    public static void setGlucoseLevelField(String glucoseLevelField) {
        UserPreference.glucoseLevelField = glucoseLevelField;
    }

    public static void setReadingTakenField(String readingTakenField) {
        UserPreference.readingTakenField = readingTakenField;
    }
    public static String getReadingTakenField() {
        return readingTakenField;}

    public static String getRprescriptionField() {
        return rprescriptionField;
    }
    public static void setRprescriptionField(String rprescriptionField) {
        UserPreference.rprescriptionField = rprescriptionField;
    }

    public static String getRtestedBGLField() {
        return rtestedBGLField;
    }
    public static void setRtestedBGLField(String rtestedBGLField){
        UserPreference.rtestedBGLField = rtestedBGLField;
    }

    public static String getRexerciseField() {
        return rexerciseField;
    }
    public static void setRexerciseField(String rexerciseField) {
        UserPreference.rexerciseField = rexerciseField;
    }

    public static String getRdietField() {
        return rdietField;
    }
    public static void setRdietField(String rdietField) {
        UserPreference.rdietField = rdietField;
    }
}

