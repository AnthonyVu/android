package ca.bcit.ass3.vu_wang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView list_events;
    private Button viewDatabaseButton;
    private SQLiteOpenHelper helper;
    private String name;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(this);
        list_events = (ListView) findViewById(R.id.list_events);
        list_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ItemList.class);
                db = helper.getReadableDatabase();
                cursor = db.query(DatabaseHelper.MASTER,
                        new String[] {DatabaseHelper.ID, DatabaseHelper.EVENTNAME, DatabaseHelper.EVENTDATE, DatabaseHelper.EVENTTIME},
                        DatabaseHelper.ID + " = ?",
                        new String[] {l+""},
                        null, null, null);

                // move to the first record
                if (cursor.moveToFirst()) {
                    // get the country details from the cursor
                    name = cursor.getString(1);
                    date = cursor.getString(2);
                    time = cursor.getString(3);
                }
                intent.putExtra(ItemList.ID, l);
                intent.putExtra(ItemList.EVENT_NAME, name);
                intent.putExtra(ItemList.EVENT_DATE, date);
                intent.putExtra(ItemList.EVENT_TIME, time);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteOpenHelper helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.MASTER,
                new String[]{DatabaseHelper.ID,DatabaseHelper.EVENTNAME},
                null,
                null,
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {DatabaseHelper.EVENTNAME},
                new int[] {android.R.id.text1});
        list_events.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_event:
                //start activity to add new event
                Intent i = new Intent(this, CreateEvent.class);
                startActivity(i);
                return true;
            case R.id.search_event:
                Intent j = new Intent(this, SearchForEvent.class);
                startActivity(j);
                return true;
            case R.id.home:
                Intent l = new Intent(this, MainActivity.class);
                startActivity(l);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
