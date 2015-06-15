package com.einswatch.einswatchuser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Shao Fei on 15/6/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "einscloud_local";

    private static final String USER_TABLE = "User";
    private static final String REG_VIEWER_TABLE = "RegisteredViewer";
    private static final String LANDLINE_TABLE = "LandlineContact";
    private static final String ACTIVITY_LOG_TABlE = "ActivityLog";

    /* User table keys */
    private static final String USER_ID = "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIRSTNAME= "first_name";
    private static final String USER_LASTNAME = "last_name";
    private static final String USER_EMAIL= "email";
    private static final String USER_NRIC = "nric";
    private static final String USER_PHONE = "phone_no";
    private static final String USER_ACC_THRESHOLD= "acc_threshold";
    private static final String USER_HR_THRESHOLD= "hr_threshold";


    // TODO: Add in constants for other tables


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE" + USER_TABLE + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_USERNAME + " TEXT UNIQUE NOT NULL," +  USER_PASSWORD + " TEXT NOT NULL," + USER_FIRSTNAME
                + " TEXT NOT NULL," + USER_LASTNAME + " TEXT NOT NULL, " + USER_EMAIL + " TEXT," + USER_NRIC
                + " TEXT UNIQUE NOT NULL," + USER_PHONE + " TEXT NOT NULL," + USER_ACC_THRESHOLD + " REAL,"
                + USER_HR_THRESHOLD + " REAL)";
        db.execSQL(CREATE_USER_TABLE);

        // TODO: Create other tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

        onCreate(db);
    }

    // TODO: Complete other operations of CRUD

    public void insertQuery(String tableName, LinkedList<String[]> keyValuePairs) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i < keyValuePairs.size(); i++) {
            String[] keyValuePair = keyValuePairs.get(i);
            String key = keyValuePair[0];
            String value = keyValuePair[1];
            values.put(key, value);
        }

        database.insert(tableName, null, values);
        database.close();
    }

    public Cursor readQuery(String tableName, String[] columns, String[][] selections) {
        SQLiteDatabase database = this.getReadableDatabase();


        String columns_query = "";
        String selections_query = "";

        for(int i = 0; i < columns.length; i++) {
            if(i == 0) {
                columns_query += columns[i];
            } else {
                columns_query += (", " + columns[i]);
            }
        }

        for(int i = 0; i < selections.length; i++) {
            if(i == 0) {
                columns_query += selections[i][0] + "=" + selections[i][1];
            } else {
                columns_query += (" AND " + selections[i] + "=" + selections[i][1]);
            }
        }

        String query = "SELECT " + columns_query + "FROM " + tableName + "WHERE " + selections_query;

        Cursor cursor = database.rawQuery(query, null);

        return cursor;
    }
}
