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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchedEvents extends AppCompatActivity {

    public static final String EVENT_NAME = "SearchedEvents.name";
    private String eventName;
    private ListView searchedEvents;
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private Cursor cursor;

    private String name;
    private String date;
    private String time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_events);
        eventName = getIntent().getStringExtra(EVENT_NAME);
        searchedEvents = (ListView)findViewById(R.id.searchedEvents);

        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        cursor = db.query("EVENT_MASTER",
                new String[]{"_id", "Name"},
                "Name = ?" ,
                new String[]{eventName},
                null,
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {"Name"},
                new int[] {android.R.id.text1});
        searchedEvents.setAdapter(adapter);

        searchedEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ItemList.class);
                db = helper.getReadableDatabase();
                cursor = db.query("EVENT_MASTER",
                        new String[] {"_id", "Name", "Date", "Time"},
                        "_id = ?",
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
            case R.id.add_pledge:
                Intent k = new Intent(this, ChooseContributionEvent.class);
                startActivity(k);
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
