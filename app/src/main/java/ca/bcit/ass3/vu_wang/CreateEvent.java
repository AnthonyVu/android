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

public class CreateEvent extends AppCompatActivity {
    private SQLiteDatabase db;
    private EditText name;
    private EditText date;
    private EditText time;
    private Button doneButton;
    private Button cancelButton;
    private ContentValues values;
    private boolean checkOne;
    private boolean checkTwo;
    private boolean checkThree;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        values = new ContentValues();
        name = (EditText)findViewById(R.id.name);
        date = (EditText)findViewById(R.id.date);
        time = (EditText)findViewById(R.id.time);
        checkOne = false;
        checkTwo = false;
        checkThree = false;
        doneButton = (Button)findViewById(R.id.doneButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        SQLiteOpenHelper helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkOne = false;
                } else {
                    values.put(DatabaseHelper.EVENTNAME, charSequence.toString());
                    checkOne = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkTwo = false;
                } else {
                    values.put(DatabaseHelper.EVENTDATE, charSequence.toString());
                    checkTwo = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    checkThree = false;
                } else {
                    values.put(DatabaseHelper.EVENTTIME, charSequence.toString());
                    checkThree = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOne && checkTwo && checkThree) {
                    db.insert(DatabaseHelper.MASTER, null, values);
                    finish();
                } else {
                    Toast.makeText(CreateEvent.this, getResources().getString(R.string.fill), Toast.LENGTH_SHORT).show();
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
