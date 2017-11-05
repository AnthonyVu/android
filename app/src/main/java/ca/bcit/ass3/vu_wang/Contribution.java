package ca.bcit.ass3.vu_wang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anthony Vu on 11/1/2017.
 */

public class Contribution extends AppCompatActivity {

    public static final String ITEM_ID = "itemID";
    public static final String ITEM_NAME = "itemName";
    public static final String QUANTITY = "quantity";
    public static final String DATE = "date";

    private TextView id;
    private TextView name;
    private TextView quantity;
    private TextView date;
    private Button addButton;
    private Button cancelButton;
    private EditText contributionView;
    private ContentValues contributionValues;
    private ContentValues detailValues;

    private String itemId;
    private String itemName;
    private String itemQuantity;
    private String eventDate;
    private String amount;
    private int contribution;

    private SQLiteDatabase db;
    private Cursor cursor;
    private SQLiteOpenHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);
        helper = new DatabaseHelper(this);
        contributionValues = new ContentValues();
        detailValues = new ContentValues();

        id = (TextView)findViewById(R.id.itemID);
        name = (TextView)findViewById(R.id.itemName);
        quantity = (TextView)findViewById(R.id.itemQuantity);
        date = (TextView)findViewById(R.id.date);
        addButton = (Button)findViewById(R.id.addButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        contributionView = (EditText)findViewById(R.id.amount);

        itemId = getIntent().getStringExtra(ITEM_ID);
        itemName = getIntent().getStringExtra(ITEM_NAME);
        itemQuantity = getIntent().getStringExtra(QUANTITY);
        eventDate = getIntent().getStringExtra(DATE);

        id.setText(itemId);
        name.setText(itemName);
        quantity.setText(itemQuantity);
        date.setText(eventDate);

        contributionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    contribution = Integer.parseInt(charSequence.toString());
                } catch (NumberFormatException e) {
                    contribution = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getReadableDatabase();
                cursor = db.query("EVENT_DETAIL",
                        new String[] {"_id", "itemName", "Unit", "Quantity", "eventId"},
                        "_id = ?",
                        new String[] {itemId},
                        null, null, null);

                // move to the first record
                if (cursor.moveToFirst()) {
                    amount = cursor.getString(3);
                }
                if(contribution != 0 && (Integer.parseInt(amount) - contribution) >= 0) {
                    contributionValues.put("Name", itemName);
                    contributionValues.put("Quantity", contribution);
                    contributionValues.put("Date", eventDate);
                    contributionValues.put("detailId", itemId);
                    db.insert("CONTRIBUTION", null, contributionValues);

                    detailValues.put("Quantity", (Integer.parseInt(amount) - contribution) + "");
                    db.update("EVENT_DETAIL", detailValues, "_id = ?", new String[]{itemId});
                    finish();
                }
                else {
                    Toast.makeText(Contribution.this, "You're doing something wrong. Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
