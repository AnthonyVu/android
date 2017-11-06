package ca.bcit.ass3.vu_wang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "assign3.sqlite";
    private static final int DB_VERSION = 2;
    private Context context;

    public static final String MASTER ="EVENT_MASTER";
    public static final String ID = "_id";
    public static final String EVENTNAME = "Name";
    public static final String EVENTDATE = "Date";
    public static final String EVENTTIME = "Time";

    public static final String DETAIL = "EVENT_DETAIL";
    public static final String ITEMNAME = "itemName";
    public static final String ITEMUNIT = "Unit";
    public static final String ITEMQUANTITY = "Quantity";
    public static final String EVENTID = "eventId";

    public DatabaseHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(CreateEventMasterTable());
                db.execSQL(CreateEventDetailTable());
                seedData(db);
            }
        } catch (SQLException sqle) {
        }
    }

    private void seedData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(EVENTNAME, "Pot Luck Christmas Party");
        values.put(EVENTDATE, "December 25, 2017");
        values.put(EVENTTIME, "6:00 PM");
        db.insert(MASTER, null, values);

        values.clear();
        values.put(ITEMNAME, "Paper plates");
        values.put(ITEMUNIT, "Pieces");
        values.put(ITEMQUANTITY, "20");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Paper cups");
        values.put(ITEMUNIT, "Pieces");
        values.put(ITEMQUANTITY, "30");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Napkins");
        values.put(ITEMUNIT, "Pieces");
        values.put(ITEMQUANTITY, "100");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Beer");
        values.put(ITEMUNIT, "6 packs");
        values.put(ITEMQUANTITY, "5");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Pop");
        values.put(ITEMUNIT, "2 Liter Bottles");
        values.put(ITEMQUANTITY, "3");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Pizza");
        values.put(ITEMUNIT, "Large");
        values.put(ITEMQUANTITY, "3");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);

        values.clear();
        values.put(ITEMNAME, "Peanuts");
        values.put(ITEMUNIT, "Grams");
        values.put(ITEMQUANTITY, "200");
        values.put(EVENTID, "1");
        db.insert(DETAIL, null, values);
    }

    private String CreateEventMasterTable() {
        String sql = "";
        sql += "CREATE TABLE " + MASTER + "(";
        sql +=  ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql +=  EVENTNAME + " TEXT, ";
        sql +=  EVENTDATE + " TEXT, ";
        sql +=  EVENTTIME + " TEXT)";

        return sql;
    }

    private String CreateEventDetailTable() {
        String sql = "";
        sql += "CREATE TABLE " + DETAIL + "(";
        sql +=  ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql +=  ITEMNAME + " TEXT, ";
        sql +=  ITEMUNIT + " TEXT, ";
        sql +=  ITEMQUANTITY + " TEXT,";
        sql +=  EVENTID + " INTEGER)";

        return sql;
    }

    private void seedData() {

    }
}
