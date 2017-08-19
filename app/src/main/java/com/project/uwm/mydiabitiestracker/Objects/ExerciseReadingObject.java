package com.project.uwm.mydiabitiestracker.Objects;

import android.support.annotation.NonNull;

public class  ExerciseReadingObject implements Comparable<ExerciseReadingObject>{
    private int exercise_id;
    private String user_name;
    private String exercise_type;
    private int duration;
    private String date;
    private String time;

    public ExerciseReadingObject(int id, String username,String exercise_type, int duration, String date, String time){
        setExerciseId(id);
        setUser_name(username);
        setExerciseType(exercise_type);
        setDuration(duration);
        setDate(date);
        setTime(time);
    }
    // Getters:

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getExerciseType() {
        return exercise_type;
    }

    public int getDuration() { return duration; }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getExercise_id(){
        return exercise_id;
    }

    // Setters:
    public void setExerciseType(String exercise_type) {this.exercise_type = exercise_type; }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setExerciseId(int exercise_id) { this.exercise_id = exercise_id; }

    @Override
    public int compareTo(@NonNull ExerciseReadingObject exerciseReadingObject) {
        String[] part = exerciseReadingObject.date.split("-");
        String[] part1 = date.split("-");
        int date = Integer.parseInt(part[0]);
        int month = Integer.parseInt(part[1]);
        int year = Integer.parseInt(part[2]);

        int date1 = Integer.parseInt(part1[0]);
        int month1 = Integer.parseInt(part1[1]);
        int year1 = Integer.parseInt(part1[2]);

        if(year > year1)
            return  -1;
        else if(month > month1)
            return  -1;
        else if(date >date1)
            return -1;
        else
            return 0;
    }
}



