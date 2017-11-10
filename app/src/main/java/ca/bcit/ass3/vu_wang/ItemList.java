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
import android.widget.TextView;

public class ItemList extends AppCompatActivity {
    public static final String ID = "test.id";
    public static final String EVENT_NAME = "test.name";
    public static final String EVENT_DATE = "test.date";
    public static final String EVENT_TIME = "test.time";

    private Cursor cursor;
    private TextView eventName;
    private TextView eventDate;
    private TextView eventTime;
    private Button addButton;
    private Button editButton;
    private Button deleteEventButton;
    private ListView food_items;
    private long id;
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private String name;
    private String unit;
    private String quantity;
    private String eventId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        helper = new DatabaseHelper(this);
        id = getIntent().getLongExtra(ID, 0);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CreateItem.class);
                i.putExtra(CreateItem.EVENT_ID, id);
                startActivity(i);
            }
        });
        deleteEventButton = (Button) findViewById(R.id.deleteEventButton);
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete(DatabaseHelper.MASTER, DatabaseHelper.ID + " = ?", new String[]{id+""});
                db.delete(DatabaseHelper.DETAIL, DatabaseHelper.EVENTID + " = ?", new String[]{id+""});
                finish();
            }
        });
        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditEvent.class);
                i.putExtra(EditEvent.EVENT_ID, id + "");
                startActivity(i);
                finish();
            }
        });
        eventName = (TextView)findViewById(R.id.eventName);
        eventName.setText(getResources().getString(R.string.eventNameLocale) + ": " + getIntent().getStringExtra(EVENT_NAME));
        eventDate = (TextView)findViewById(R.id.eventDate);
        eventDate.setText(getResources().getString(R.string.eventDateLocale) + ": " + getIntent().getStringExtra(EVENT_DATE));
        eventTime = (TextView)findViewById(R.id.eventTime);
        eventTime.setText(getResources().getString(R.string.eventTimeLocale) + ": " + getIntent().getStringExtra(EVENT_TIME));
        food_items = (ListView)findViewById(R.id.foodItems);
        food_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ItemList.this, ItemDetails.class);
                db = helper.getReadableDatabase();
                cursor = db.query(DatabaseHelper.DETAIL,
                        new String[] {DatabaseHelper.ID, DatabaseHelper.ITEMNAME, DatabaseHelper.ITEMUNIT, DatabaseHelper.ITEMQUANTITY, DatabaseHelper.EVENTID},
                        DatabaseHelper.ID + " = ?",
                        new String[] {l+""},
                        null, null, null);

                // move to the first record
                if (cursor.moveToFirst()) {
                    // get the country details from the cursor
                    name = cursor.getString(1);
                    unit = cursor.getString(2);
                    quantity = cursor.getString(3);
                    eventId = cursor.getString(4);
                }
                intent.putExtra(ItemDetails.ITEM_ID, l + "");
                intent.putExtra(ItemDetails.ITEM_NAME, name);
                intent.putExtra(ItemDetails.ITEM_UNIT, unit);
                intent.putExtra(ItemDetails.ITEM_QUANTITY, quantity);
                intent.putExtra(ItemDetails.ITEM_EVENT_ID, eventId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.DETAIL,
                new String[]{DatabaseHelper.ID, DatabaseHelper.ITEMNAME},
                DatabaseHelper.EVENTID + " = ?",
                new String[]{id + ""},
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item_layout,
                cursor,
                new String[] {DatabaseHelper.ITEMNAME},
                new int[] {R.id.list_content});
        food_items.setAdapter(adapter);
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
