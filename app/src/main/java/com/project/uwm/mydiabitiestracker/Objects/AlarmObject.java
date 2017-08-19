package com.project.uwm.mydiabitiestracker.Objects;

/**
 * Created by Anitha on 8/5/2017.
 */

public class AlarmObject {
    private int KEY_ROWID;
    private String KEY_TITLE;;
    private String KEY_BODY;
    private String KEY_DATE_TIME;

    AlarmObject(  int id,String title,String body, String datetime){
        setKEY_BODY(body);
        setKEY_DATE_TIME(datetime);
        setKEY_ROWID(id);
        setKEY_TITLE(title);
    }

    public void setKEY_BODY(String KEY_BODY) {
        this.KEY_BODY = KEY_BODY;
    }

    public void setKEY_ROWID(int KEY_ROWID) {
        this.KEY_ROWID = KEY_ROWID;
    }

    public void setKEY_DATE_TIME(String KEY_DATE_TIME) {
        this.KEY_DATE_TIME = KEY_DATE_TIME;
    }

    public void setKEY_TITLE(String KEY_TITLE) {
        this.KEY_TITLE = KEY_TITLE;
    }

    public int getKEY_ROWID() {
        return KEY_ROWID;
    }

    public String getKEY_BODY() {
        return KEY_BODY;
    }

    public String getKEY_DATE_TIME() {
        return KEY_DATE_TIME;
    }

    public String getKEY_TITLE() {
        return KEY_TITLE;
    }
}
