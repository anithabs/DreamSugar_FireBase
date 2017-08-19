package com.project.uwm.mydiabitiestracker;

import android.content.Context;
import android.database.Cursor;

import com.project.uwm.mydiabitiestracker.Objects.AlarmObject;
import com.project.uwm.mydiabitiestracker.Objects.ExerciseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.FoodConsumedObject;
import com.project.uwm.mydiabitiestracker.Objects.GlucoseReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.PrescriptionReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.RegimenReadingObject;
import com.project.uwm.mydiabitiestracker.Objects.UserObject;
import com.project.uwm.mydiabitiestracker.Objects.WordObject;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String PASSPHRASE="test123";
    private static final String DATABASE_NAME = "DiabetesDB1";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_NAME = "username";

    //User table creation
    private static final String USER_TABLE = "UserTable";
    private static final String USERID = "uid";
    private static final String USERNAME = "userName";
    private static final String PASSWORD = "password";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String EMAIL = "email";
    private static final String usqlCreate = "CREATE TABLE IF NOT EXISTS " + USER_TABLE +
            "(" + USERNAME + " TEXT PRIMARY KEY, " + PASSWORD + " VARCHAR, " + FIRSTNAME +
            " VARCHAR, " + LASTNAME + " VARCHAR, " + EMAIL + " VARCHAR)";

    // Word table creation
    private static final String WORD_TABLE = "WordTable";
    private static final String WID = "wid";
    private static final String WORD = "word";
    private static final String SOURCE = "source";
    private static final String wsqlCreate = "CREATE TABLE IF NOT EXISTS "
            + WORD_TABLE + " ("
            + WID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WORD + " TEXT, "
            + SOURCE + " TEXT)";


    //Regimen table creation:
    private static final String REGIMEN_TABLE = "RegimenTable";
    private static final String RID = "rid";
    private static final String TESTED_BGL="testedBGL";
    private static final String rMEDS="meds";
    private static final String rDIET="diet";
    private static final String rDATE="date";
    private static final String rTIME="time";
    private static final String EXERCISE="exerciseTEXT";


    private static final String rsqlCreate = "CREATE TABLE IF NOT EXISTS "
            + REGIMEN_TABLE + " ("
            + RID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +USER_NAME + " TEXT,"
            + TESTED_BGL + " TEXT,"
            + EXERCISE + " TEXT,"
            + rMEDS + " TEXT,"
            + rDIET + " TEXT,"
            + rDATE + " REAL,"
            + rTIME + " REAL,"
            + "FOREIGN KEY("+USER_NAME+ ") REFERENCES "+ USER_TABLE +"("+USERNAME+"))";
    // Diet table creation
    private static final String DIET_TABLE = "DietTable";
    private static final String FID = "fid";
    private static final String TYPEOFFOOD = "TypeOfFood";
    private static final String AMOUNTOFFOOD = "AmuntOfFood";
    private static final String FDATE = "fDate";
    private static final String FTIME = "fTime";
    private static final String dsqlCreate = "CREATE TABLE IF NOT EXISTS " + DIET_TABLE + " ("
            + FID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT,"
            + TYPEOFFOOD + " TEXT, " + AMOUNTOFFOOD + " INTEGER,"
            + FDATE + " REAL," + FTIME + " REAL,"
            + "FOREIGN KEY("+USER_NAME+ ") REFERENCES "+ USER_TABLE +"("+USERNAME+"))";

    //Glucose table creation
    private static final String GLUCOSE_TABLE = "GlucoseTable";
    private static final String GID = "gid";
    private static final String GLUCOSE_LEVEL = "GlucoseLevel";
    private static final String GLUCOSE_READING_TAKEN = "GlucoseReadingTaken";
    private static final String GDATE = "gDate";
    private static final String GTIME = "gTime";
    private static final String gsqlCreate = "CREATE TABLE IF NOT EXISTS " + GLUCOSE_TABLE +
            " (" + GID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT,"
            + GLUCOSE_LEVEL + " INTEGER,"+ GLUCOSE_READING_TAKEN + " TEXT, "  + GDATE + " REAL," + GTIME + " REAL,"
            + "FOREIGN KEY("+USER_NAME+ ") REFERENCES "+ USER_TABLE +"("+USERNAME+"))";

    //Prescription table creation
    private static final String PRESCRIPTON_TABLE = "PrescriptionTable";
    private static final String PID = "pid";
    private static final String DRUG_NAME = "DrugName";
    private static final String DOSE = "Dose";
    private static final String TIME_TAKEN = "TimeTaken";
    private static final String DATE_TAKEN = "DateTaken";
    private static final String psqlCreate = "CREATE TABLE IF NOT EXISTS "
            + PRESCRIPTON_TABLE + "("
            + PID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_NAME + " TEXT,"
            + DRUG_NAME + " TEXT,"
            + DOSE + " INTEGER,"
            + DATE_TAKEN + " REAL,"
            + TIME_TAKEN + " REAL,"
            + "FOREIGN KEY("+USER_NAME+ ") REFERENCES "+ USER_TABLE +"("+USERNAME+"))";

    // Exercise table creation:
    private static final String EXERCISE_TABLE = "ExerciseTable";
    private static final String EID = "eid";
    private static final String EXERCISE_TYPE = "exercise_type";
    private static final String E_DURATION = "duration";
    private static final String EXERCISE_TIME = "time";
    private static final String EXERCISE_DATE = "date";
    private static final String esqlCreate = "CREATE TABLE IF NOT EXISTS "
            + EXERCISE_TABLE + "("
            + EID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_NAME + " TEXT,"
            + EXERCISE_TYPE + " TEXT,"
            + E_DURATION + " INTEGER,"
            + EXERCISE_DATE + " REAL,"
            + EXERCISE_TIME + " REAL,"
            + "FOREIGN KEY("+USER_NAME+ ") REFERENCES "+ USER_TABLE +"("+USERNAME+"))";

    private static final String ALARMTABLE = "AlarmTable";
    private static final String AID ="id";
    private static final String ALARMTIME ="alarmTime";
    private static final String ONETIME ="oneTime";
    private static final String ACTIVE ="active";
    private static final String LABEL ="label";
    private static final String METRIC ="metric";

    private static final String DATABASE_TABLE = "reminders";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_DATE_TIME = "reminder_date_time";
    public static final String KEY_ROWID = "id";

    private static final String asqlCreate =
            "create table " + DATABASE_TABLE + " ("
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TITLE + " text not null, "
                    + KEY_BODY + " text not null, "
                    + KEY_DATE_TIME + " text not null);";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase.loadLibs(context);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usqlCreate);
        db.execSQL(dsqlCreate);
        db.execSQL(gsqlCreate);
        db.execSQL(psqlCreate);
        db.execSQL(esqlCreate);
        db.execSQL(rsqlCreate);
        db.execSQL(asqlCreate);
        db.execSQL(wsqlCreate);
        db.execSQL(asqlCreate);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist:
        db.execSQL("drop table if exists" + USER_TABLE);
        db.execSQL("drop table if exists " + DIET_TABLE);
        db.execSQL("drop table if exists" + GLUCOSE_TABLE);
        db.execSQL("drop table if exists" + PRESCRIPTON_TABLE);
        db.execSQL("drop table if exists" + EXERCISE_TABLE);
        db.execSQL("drop table if exists" + REGIMEN_TABLE);
        db.execSQL("drop table if exists" + WORD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // Re-create tables
        onCreate(db);
    }

    // All Insert
    public void insertRegime(RegimenReadingObject re) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + REGIMEN_TABLE + " (" + RID + ","+USER_NAME+ "," + TESTED_BGL  +  ","+EXERCISE+"," + rMEDS  + "," + rDIET  + ","+ rDATE +","+ rTIME +") values(";
        sqlInsert += "null,'"+re.getUsername() + "','" + re.getTested()  + "','"+re.getExercise()+"','" + re.getMeds() + "','" + re.getDiet() + "',julianday('" + re.getDate() + "'),julianday('" + re.getTime() + "'))";
        db.execSQL(sqlInsert);
        db.close();

    }
    public void insertFood(FoodConsumedObject food) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String fSqlInsert = "insert into " + DIET_TABLE;
        fSqlInsert += " (" + FID + ","+USER_NAME + ", " + TYPEOFFOOD + ", " + AMOUNTOFFOOD + ", "  + FDATE + ", " + FTIME + ")";
        fSqlInsert += " values(null,'" +food.getUsername()+"','"+ food.getTypeOfFood() + "'," + food.getAmountOfFood()  + ",julianday('" + food.getDate() + "'),julianday('" + food.getTime() + ":00'))";
        db.execSQL(fSqlInsert);
        db.close();
    }
    public void insertGlucose(GlucoseReadingObject glucose) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String gSqlInsert = "insert into " + GLUCOSE_TABLE;
        gSqlInsert += " (" + GID + ","+USER_NAME+ ", " + GLUCOSE_LEVEL + ", " + GLUCOSE_READING_TAKEN + ", " + GDATE + ", " + GTIME + ")";
        gSqlInsert += " values(null,'" + glucose.getUsername()+"',"+glucose.getGlucose_level() + ",'" + glucose.getReading_taken() + "'," + "julianday('" + glucose.getGdate() + "'),julianday('" + glucose.getGtime() + ":00'))";
        db.execSQL(gSqlInsert);
        db.close();
    }
    public void insertPrescription(PrescriptionReadingObject pre) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + PRESCRIPTON_TABLE;
        sqlInsert += " (" + PID + ","+USER_NAME +   ", " + DRUG_NAME + ", " + DOSE + ", " + DATE_TAKEN + ", " + TIME_TAKEN + ")";
        sqlInsert += " values(null,'"+pre.getUsername()+"','" + pre.getDrugName() + "'," +pre.getDosage() +  ",julianday('" + pre.getDate() + "'),julianday('" + pre.getTime() + ":00'))";
        db.execSQL(sqlInsert);
        db.close();
    }
    public void insertExercise(ExerciseReadingObject exercise) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + EXERCISE_TABLE;
        sqlInsert += " (" + EID +  ","+USER_NAME+ ", " + EXERCISE_TYPE + ", " + E_DURATION + ", " + EXERCISE_DATE + ", " + EXERCISE_TIME + ")";
        sqlInsert += " values(null,'" +exercise.getUser_name()+"','"+ exercise.getExerciseType() + "'," +exercise.getDuration() +  ",julianday('" + exercise.getDate() + "'),julianday('" + exercise.getTime() + ":00'))";
        db.execSQL(sqlInsert);
        db.close();
    }
    public void insertUser(UserObject uo) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + USER_TABLE + " (" + USERNAME + "," + PASSWORD + "," + FIRSTNAME + "," + LASTNAME + "," + EMAIL + ") values(";
        sqlInsert += "'" + uo.getUserName() + "','" + uo.getPassword() + "','" + uo.getFirstName() + "','" + uo.getLastName() + "','" + uo.getEmail() + "')";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertAlarm(AlarmObject ao){
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + DATABASE_TABLE + " ( " +KEY_ROWID +","+KEY_TITLE+","+KEY_BODY +","+KEY_DATE_TIME +")";
        sqlInsert +=" values ( null, '" +ao.getKEY_TITLE() +"', '"+ao.getKEY_BODY()+"','" +ao.getKEY_DATE_TIME()+ "')";
        db.execSQL(sqlInsert);
        db.close();
    }
    //-------------------------------------------------------------------------------
    // The following four methods are related to Word objects for AutocompleteTextBox
    //
    public void insertWord(WordObject wo) {


        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlInsert = "insert into " + WORD_TABLE + " (" + WID + "," + WORD + "," + SOURCE + ") values(";
        sqlInsert += "null,'" + wo.getWord() + "','" + wo.getSource() + "')";
        db.execSQL(sqlInsert);
        db.close();
    }
    public ArrayList<String> selectWordsBySource(String source) {
        SQLiteDatabase db = this.getReadableDatabase(PASSPHRASE);
        ArrayList<String> resultList = new ArrayList<String>();
        String query = "select * from " + WORD_TABLE + " where " + SOURCE + "='" + source + "'";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            resultList.add(c.getString(c.getColumnIndex("word")));
        }
        c.close();
        db.close();
        Collections.sort(resultList);
        return resultList;
    }
    public int numOccurrencesByObject(WordObject entry) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String query = "Select * from " + WORD_TABLE + " where " +
                WORD + "='" + entry.getWord() + "' and " +
                SOURCE + "='" + entry.getSource() + "'";
        Cursor cursor = db.rawQuery(query, null);
        int value = cursor.getCount();
        cursor.close();
        db.close();
        return value;
    }
    public boolean ifSourceColumnHasId(String id) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        Boolean returnValue = false;
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORD_TABLE + " WHERE source = '" + id + "'", null);
        if (cursor.getCount() > 0) { // This will get the number of rows
            returnValue = true;
        }
        return returnValue;
    }
    //  End of the methods for AutocompleteTextBox
    //-------------------------------------------------------------------------------
    //
    //
    //user authentication queries
    //
    public int selectUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String query = "Select * from " + USER_TABLE + " where " + USERNAME + "='" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        int value = cursor.getCount();
        cursor.close();
        return value;
    }
    public int selectEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String query = "Select * from " + USER_TABLE + " where " + EMAIL + "='" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        int value = cursor.getCount();
        cursor.close();
        return value;
    }
    public int verifyLogin(String userName, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        db.execSQL(usqlCreate);
        String query = "Select * from " + USER_TABLE + " where " + USERNAME + "='" + userName + "' and " + PASSWORD + "='" + passWord + "' ";
        Cursor cursor = db.rawQuery(query, null);
        int value = cursor.getCount();
        cursor.close();
        return value;
    }


    public RegimenReadingObject selectRegimen(String userName) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int rvalue = 0;
        int id =0;
        String date = " ";
        String time = " ";
        String maxdateSqlDate =
                "select MAX(" + RID + ") from " + REGIMEN_TABLE ;
        Cursor cursor3 = db.rawQuery(maxdateSqlDate, null);
        if (cursor3.moveToNext()){
            id = cursor3.getInt(0);
         }
        String rSqlSelect = "select *  from " + REGIMEN_TABLE + " where "+RID+"= "+id;
        Cursor cursor = db.rawQuery(rSqlSelect, null);
        RegimenReadingObject rro = null;

        if (cursor.moveToNext()) {
            rro = new RegimenReadingObject(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7));
            rvalue = rro.getRegimen_ID();
            String rSqlDate =
                    "select date(" + rDATE + "),time(" + rTIME + ") " + "from " + REGIMEN_TABLE + " where " + RID + "=" + rvalue;
            Cursor cursor1 = db.rawQuery(rSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            rro.setDate(date);
            rro.setTime(time);
        }
        db.close();
        return rro;
    }

    // Delete By Date and Time
    public void deleteAlarm(long rowId){
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlDelete = "Delete from " + DATABASE_TABLE + " where " + KEY_ROWID + " = " + rowId  ;
        db.execSQL(sqlDelete);
        db.close();
    }
    public void deleteFoodByDateTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlDelete = "Delete from " + DIET_TABLE + " where TIME(" + FTIME + ") = TIME('" + gettime + "') and DATE(" + FDATE + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        db.execSQL(sqlDelete);
        db.close();
    }
    public void deleteExerciseByDateTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlDelete = "Delete from " + EXERCISE_TABLE + " where TIME(" + EXERCISE_TIME + ") = TIME('" + gettime + "') and DATE(" + EXERCISE_DATE + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        db.execSQL(sqlDelete);
        db.close();
    }
    public void deleteGlucoseByDateTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlDelete = "Delete from " + GLUCOSE_TABLE + " where TIME(" + GTIME + ") = TIME('" + gettime + "') and DATE(" + GDATE + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        db.execSQL(sqlDelete);
        db.close();
    }
    public void deletePrescriptionByDateTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlDelete = "Delete from " + PRESCRIPTON_TABLE + " where TIME(" + TIME_TAKEN + ") = TIME('" + gettime + "') and DATE(" + DATE_TAKEN + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        db.execSQL(sqlDelete);
        db.close();
    }

    // Update By Object
    public void updateFoodByObject(FoodConsumedObject fco) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlUpdate = "update " + DIET_TABLE + " set " + AMOUNTOFFOOD + "='" + fco.getAmountOfFood() +
                "' , " + TYPEOFFOOD + "='" + fco.getTypeOfFood() + "'"+
                " where "+USER_NAME +"= '"+ fco.getUsername()+"' and  TIME(" + FTIME + ") = TIME('" + fco.getTime() + "') and DATE(" + FDATE + ")=DATE('" + fco.getDate() + "')";
        db.execSQL(sqlUpdate);
        db.close();
    }
    public void updateExerciseByObject(ExerciseReadingObject ero) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlUpdate = "update " + EXERCISE_TABLE + " set " + EXERCISE_TYPE + "='" + ero.getExerciseType() +
                "' , " + E_DURATION + "='" + ero.getDuration() + "' where  "+USER_NAME +"= '"+ ero.getUser_name()+"' and  TIME(" + EXERCISE_TIME + ") = TIME('" + ero.getTime() + "') and DATE(" + EXERCISE_DATE + ")=DATE('" + ero.getDate() + "')";
        db.execSQL(sqlUpdate);
        db.close();
    }
    public void updateGlucoseByObject(GlucoseReadingObject gro) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlUpdate = "update " + GLUCOSE_TABLE + " set " + GLUCOSE_LEVEL + "='" + gro.getGlucose_level() +
                "' , " + GLUCOSE_READING_TAKEN + "='" + gro.getReading_taken() + "' where  "+USER_NAME +"= '"+ gro.getUsername()+"' and  TIME(" + GTIME + ") = TIME('" + gro.getGtime() + "') and DATE(" + GDATE + ")=DATE('" + gro.getGdate() + "')";
        db.execSQL(sqlUpdate);
        db.close();
    }
    public void updatePrescription(PrescriptionReadingObject pro) {
        String drug = pro.getDrugName();
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String sqlUpdate = "update " + PRESCRIPTON_TABLE + " set " + DRUG_NAME + "='" + pro.getDrugName() +
                "' , " + DOSE + "='" + pro.getDosage() + "' where  "+USER_NAME +"= '"+ pro.getUsername()+"' and  TIME(" + TIME_TAKEN + ") = TIME('" + pro.getTime() + "') and DATE(" + DATE_TAKEN + ")=DATE('" + pro.getDate() + "')";
        db.execSQL(sqlUpdate);
        db.close();
    }

    //select record by date and time
    public FoodConsumedObject selectFoodByTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int fvalue = 0;
        String date = " ";
        String time = " ";
        String fSqlSelect = "select *  from " + DIET_TABLE + " where TIME(" + FTIME + ") = TIME('" + gettime + "') and DATE(" + FDATE + ")=DATE('" + getdate +"') and " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(fSqlSelect, null);
        FoodConsumedObject foodObject = null;

        if (cursor.moveToNext()) {
            foodObject = new FoodConsumedObject(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5));
            fvalue = foodObject.getFood_id();
            String fSqlDate = "select date(" + FDATE + "),time(" + FTIME + ") from " + DIET_TABLE + " where " + FID + "=" + fvalue;
            Cursor cursor1 = db.rawQuery(fSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            foodObject.setDate(date);
            foodObject.setTime(time);
        }
        db.close();
        return foodObject;
    }
    public ExerciseReadingObject selectExerciseByTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int evalue = 0;
        String date = " ";
        String time = " ";
        String eSqlSelect = "select *  from " + EXERCISE_TABLE + " where TIME(" + EXERCISE_TIME + ") = TIME('" + gettime + "') and DATE(" + EXERCISE_DATE + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(eSqlSelect, null);
        ExerciseReadingObject ero = null;

        if (cursor.moveToNext()) {
            ero = new ExerciseReadingObject(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)), cursor.getString(4),
                    cursor.getString(5));
            evalue = ero.getExercise_id();
            String fSqlDate = "select date(" + EXERCISE_DATE + "),time(" + EXERCISE_TIME + ") from " + EXERCISE_TABLE + " where " + EID + "=" + evalue;
            Cursor cursor1 = db.rawQuery(fSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            ero.setDate(date);
            ero.setTime(time);
        }
        db.close();
        return ero;
    }
    public GlucoseReadingObject selectGlucoseByTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        String date = " ";
        String time = " ";
        String gSqlSelect = "select *  from " + GLUCOSE_TABLE + " where TIME(" + GTIME + ") = TIME('" + gettime + "') and DATE(" + GDATE + ")=DATE('" + getdate + "') and " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(gSqlSelect, null);
        GlucoseReadingObject gro = null;

        if (cursor.moveToNext()) {
            gro = new GlucoseReadingObject(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4),
                    (cursor.getString(5)));
            int gvalue = gro.getGlucose_id();
            String fSqlDate = "select date(" + GDATE + "),time(" + GTIME + ") from " + GLUCOSE_TABLE + " where " + GID + "=" + gvalue;
            Cursor cursor1 = db.rawQuery(fSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            gro.setGdate(date);
            gro.setGtime(time);
        }
        db.close();
        return gro;
    }
    public PrescriptionReadingObject selectPrescriptionByDateTime(String gettime, String getdate,String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int pvalue = 0;
        String date = " ";
        String time = " ";
        String pSqlSelect = "select *  from " + PRESCRIPTON_TABLE + " where TIME(" + TIME_TAKEN + ") = TIME('" + gettime + "') and DATE(" + DATE_TAKEN + ")=DATE('" + getdate +"') and " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(pSqlSelect, null);
        PrescriptionReadingObject pro = null;

        if (cursor.moveToNext()) {
            pro =new PrescriptionReadingObject(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),Integer.parseInt(cursor.getString(3)), cursor.getString(4),
                    (cursor.getString(5)));
            pvalue = pro.getPrescription_id();
            String pSqlDate = "select date(" + DATE_TAKEN + "),time(" + TIME_TAKEN + ") from " + PRESCRIPTON_TABLE + " where " + PID + "=" + pvalue;
            Cursor cursor1 = db.rawQuery(pSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            pro.setDate(date);
            pro.setTime(time);
        }
        db.close();
        return pro;
    }
    // select All records

    // need to implement
    public ArrayList<AlarmObject> selectAllAlarm(){

        return null;
    }
    public ArrayList<FoodConsumedObject> selectAllFoodDetails(String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int fvalue = 0;
        String date = " ";
        String time = " ";
        String fSqlSelect = "select *  from " + DIET_TABLE + " where " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(fSqlSelect, null);
        ArrayList<FoodConsumedObject> ArrayFood = new ArrayList<FoodConsumedObject>();

        while (cursor.moveToNext()) {
            FoodConsumedObject fco = new FoodConsumedObject(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5));
            fvalue = fco.getFood_id();
            String fSqlDate = "select date(" + FDATE + "),time(" + FTIME + ") from " + DIET_TABLE + " where " + FID + "=" + fvalue;
            Cursor cursor1 = db.rawQuery(fSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            fco.setDate(date);
            fco.setTime(time);
            ArrayFood.add(fco);
        }
        db.close();
        return ArrayFood;
    }

    public ArrayList<GlucoseReadingObject> selectAllGlucoseDetails(String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int gvalue = 0;
        String date = " ";
        String time = " ";
        String gSqlSelect = "select *  from " + GLUCOSE_TABLE + " where " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(gSqlSelect, null);
        ArrayList<GlucoseReadingObject> ArrayGlucose = new ArrayList<GlucoseReadingObject>();

        while (cursor.moveToNext()) {
            GlucoseReadingObject gco = new GlucoseReadingObject(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4),
                    (cursor.getString(5)));
            gvalue = gco.getGlucose_id();
            String fSqlDate = "select date(" + GDATE + "),time(" + GTIME + ") from " + GLUCOSE_TABLE + " where " + GID + "=" + gvalue;
            Cursor cursor1 = db.rawQuery(fSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            gco.setGdate(date);
            gco.setGtime(time);
            ArrayGlucose.add(gco);
        }
        db.close();
        return ArrayGlucose;
    }
    public ArrayList<ExerciseReadingObject> selectAllExerciseDetails(String username) {
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int evalue = 0;
        String date = " ";
        String time = " ";
        String eSqlSelect = "select *  from " + EXERCISE_TABLE + " where " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(eSqlSelect, null);
        ArrayList<ExerciseReadingObject> ArrayExercise = new ArrayList<ExerciseReadingObject>();

        while (cursor.moveToNext()) {
            ExerciseReadingObject gco = new ExerciseReadingObject(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4),
                    (cursor.getString(5)));
            evalue = gco.getExercise_id();
            String eSqlDate = "select date(" + EXERCISE_DATE + "),time(" + EXERCISE_TIME + ") from " + EXERCISE_TABLE + " where " + EID + "=" + evalue;
            Cursor cursor1 = db.rawQuery(eSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            gco.setDate(date);
            gco.setTime(time);
            ArrayExercise.add(gco);
        }
        db.close();
        return ArrayExercise;
    }
    public ArrayList<PrescriptionReadingObject> selectAllPrescriptionDetails(String username){
        SQLiteDatabase db = this.getWritableDatabase(PASSPHRASE);
        int pvalue = 0;
        String date = " ";
        String time = " ";
        String pSqlSelect = "select *  from " + PRESCRIPTON_TABLE + " where " + USER_NAME + "= '" + username + "'";
        Cursor cursor = db.rawQuery(pSqlSelect, null);
        ArrayList<PrescriptionReadingObject> ArrayPres = new ArrayList<PrescriptionReadingObject>();

        while (cursor.moveToNext()) {
            PrescriptionReadingObject pco = new PrescriptionReadingObject(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),Integer.parseInt(cursor.getString(3)), cursor.getString(4),
                    (cursor.getString(5)));
            pvalue = pco.getPrescription_id();
            String pSqlDate = "select date(" + DATE_TAKEN + "),time(" + TIME_TAKEN + ") from " + PRESCRIPTON_TABLE + " where " + PID + "=" + pvalue;
            Cursor cursor1 = db.rawQuery(pSqlDate, null);
            if (cursor1.moveToNext())
                date = cursor1.getString(0);
            time = cursor1.getString(1);
            pco.setDate(date);
            pco.setTime(time);
            ArrayPres.add(pco);
        }
        db.close();
        return ArrayPres;
    }

}