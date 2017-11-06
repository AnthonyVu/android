package ca.bcit.ass3.vu_wang;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by A00127241 on 2017-10-19.
 */

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
            }
        } catch (SQLException sqle) {
        }
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

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
}
