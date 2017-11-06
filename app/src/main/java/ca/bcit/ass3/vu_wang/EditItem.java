package ca.bcit.ass3.vu_wang;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.Toast;

public class EditItem extends AppCompatActivity {

    public static final String ITEM_ID = "editItem.itemId";
    private SQLiteDatabase db;
    private EditText itemName;
    private EditText unit;
    private EditText quantity;
    private Button saveButton;
    private Button cancelButton;

    private ContentValues values;
    private String itemId;
    private boolean checkOne;
    private boolean checkTwo;
    private boolean checkThree;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        SQLiteOpenHelper helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        values = new ContentValues();
        itemName = (EditText)findViewById(R.id.itemName);
        unit = (EditText)findViewById(R.id.unit);
        quantity = (EditText)findViewById(R.id.quantity);
        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        itemId = getIntent().getStringExtra(ITEM_ID);
        checkOne = false;
        checkTwo = false;
        checkThree = false;
        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkOne = false;
                } else {
                    values.put(DatabaseHelper.ITEMNAME, charSequence.toString());
                    checkOne = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkTwo = false;
                } else {
                    values.put(DatabaseHelper.ITEMUNIT, charSequence.toString());
                    checkTwo = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkThree = false;
                } else {
                    values.put(DatabaseHelper.ITEMQUANTITY, charSequence.toString());
                    checkThree = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOne && checkTwo && checkThree) {
                    db.update(DatabaseHelper.DETAIL, values, DatabaseHelper.ID + " = ?", new String[]{itemId});
                    finish();
                } else {
                    Toast.makeText(EditItem.this, getResources().getString(R.string.fill), Toast.LENGTH_SHORT).show();
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

    protected void onDestroy() {
        super.onDestroy();
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
