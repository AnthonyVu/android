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

/**
 * Created by Anthony Vu on 11/1/2017.
 */

public class ChooseContributionItem extends AppCompatActivity {

    public static final String ID = "Contribution.id";
    public static final String EVENT_DATE = "Contribution.date";

    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLiteOpenHelper helper;
    private ListView itemList;

    private long id;
    private String name;
    private String date;
    private String quantity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contribution_item);
        itemList = (ListView)findViewById(R.id.itemList);
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        date = getIntent().getStringExtra(EVENT_DATE);
        id = getIntent().getLongExtra(ID, 0);
        cursor = db.query("EVENT_DETAIL",
                new String[]{"_id", "itemName"},
                "eventId = ? AND Quantity != ?",
                new String[]{id + "", "0"},
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {"itemName"},
                new int[] {android.R.id.text1});
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), Contribution.class);
                db = helper.getReadableDatabase();
                cursor = db.query("EVENT_DETAIL",
                        new String[] {"_id", "itemName", "Unit", "Quantity", "eventId"},
                        "_id = ?",
                        new String[] {l+""},
                        null, null, null);

                // move to the first record
                if (cursor.moveToFirst()) {
                    // get the country details from the cursor
                    name = cursor.getString(1);
                    quantity = cursor.getString(3);
                }
                intent.putExtra(Contribution.ITEM_ID, l +"");
                intent.putExtra(Contribution.ITEM_NAME, name);
                intent.putExtra(Contribution.QUANTITY, quantity);
                intent.putExtra(Contribution.DATE, date);
                startActivity(intent);
                finish();
            }
        });
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
