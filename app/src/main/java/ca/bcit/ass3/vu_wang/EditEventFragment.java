package ca.bcit.ass3.vu_wang;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditEventFragment extends Fragment {

    private SQLiteDatabase db;
    private EditText name;
    private EditText date;
    private EditText time;
    private Button saveButton;
    private Button cancelButton;
    private ContentValues values;

    private String eventId;
    private boolean checkOne;
    private boolean checkTwo;
    private boolean checkThree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);
        SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
        db = helper.getWritableDatabase();
        eventId = getActivity().getIntent().getStringExtra(EditEvent.EVENT_ID);

        values = new ContentValues();
        name = view.findViewById(R.id.name);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        checkOne = false;
        checkTwo = false;
        checkThree = false;
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOne && checkTwo && checkThree) {
                    db.update(DatabaseHelper.MASTER, values, DatabaseHelper.ID + " = ?", new String[]{eventId});
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.fill), Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
