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

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyPlanet.sqlite";
    private static final int DB_VERSION = 2;
    private Context context;

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
                db.execSQL(CreateContributionTable());
            }
        } catch (SQLException sqle) {
            String msg = "[DatabaseHelper / updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    private String CreateEventMasterTable() {
        String sql = "";
        sql += "CREATE TABLE EVENT_MASTER (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Date TEXT, ";
        sql += "Time TEXT)";

        return sql;
    }

    private String CreateEventDetailTable() {
        String sql = "";
        sql += "CREATE TABLE EVENT_DETAIL (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "itemName TEXT, ";
        sql += "Unit TEXT, ";
        sql += "Quantity TEXT,";
        sql += "eventId INTEGER)";

        return sql;
    }

    private String CreateContributionTable() {
        String sql = "";
        sql += "CREATE TABLE CONTRIBUTION (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Quantity TEXT, ";
        sql += "Date TEXT,";
        sql += "detailId INTEGER)";

        return sql;
    }

    public ArrayList<Cursor> getData(String query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }
}
