package ca.bcit.ass3.vu_wang;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetails extends AppCompatActivity {
    public static final String ITEM_ID = "itemDetails.id";
    public static final String ITEM_NAME = "itemDetails.name";
    public static final String ITEM_UNIT = "itemDetails.unit";
    public static final String ITEM_QUANTITY = "itemDetails.quantity";
    public static final String ITEM_EVENT_ID = "itemDetails.eventId";

    private TextView itemName;
    private TextView itemUnit;
    private TextView itemQuantity;
    private Button deleteItemButton;
    private Button editItemButton;

    private String id;
    private String name;
    private String unit;
    private String quantity;
    private String eventId;

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        helper = new DatabaseHelper(this);
        itemName = (TextView)findViewById(R.id.itemName);
        itemUnit = (TextView)findViewById(R.id.itemUnit);
        itemQuantity = (TextView)findViewById(R.id.itemQuantity);
        deleteItemButton = (Button)findViewById(R.id.deleteButton);
        editItemButton = (Button)findViewById(R.id.editButton);
        id = getIntent().getStringExtra(ITEM_ID);
        name = getIntent().getStringExtra(ITEM_NAME);
        unit = getIntent().getStringExtra(ITEM_UNIT);
        quantity = getIntent().getStringExtra(ITEM_QUANTITY);
        eventId = getIntent().getStringExtra(ITEM_EVENT_ID);

        itemName.setText(getResources().getString(R.string.itemLocale) + ": " + name);
        itemUnit.setText(getResources().getString(R.string.unitLocale) + ": " + unit);
        itemQuantity.setText(getResources().getString(R.string.quantityLocale) + ": " + quantity);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getWritableDatabase();
                db.delete(DatabaseHelper.DETAIL, DatabaseHelper.ID + " = ?", new String[]{id});
                finish();
            }
        });

        editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditItem.class);
                i.putExtra(EditItem.ITEM_ID, id);
                startActivity(i);
                finish();
            }
        });
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
