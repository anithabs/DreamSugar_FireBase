package com.project.uwm.mydiabitiestracker.Objects;

import android.support.annotation.NonNull;

public class PrescriptionReadingObject implements Comparable<PrescriptionReadingObject> {
    private int prescription_id;
    private String username;
    private String drug_name;
    private int dosage;
    private String date;
    private String time;

    public PrescriptionReadingObject(int id, String username, String drugname, int dosage, String date, String time){
        setPrescriptionId(id);
        setDosage(dosage);
        setDrugName(drugname);
        setDate(date);
        setTime(time);
        setUsername(username);
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getPrescription_id() {return prescription_id;}

    public void setPrescriptionId(int prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getDrugName() {
        return drug_name;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public void setDrugName(String drug_type) {
        this.drug_name = drug_type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    @Override
    public int compareTo(@NonNull PrescriptionReadingObject prescriptionReadingObject) {
        String[] part = prescriptionReadingObject.date.split("-");
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
