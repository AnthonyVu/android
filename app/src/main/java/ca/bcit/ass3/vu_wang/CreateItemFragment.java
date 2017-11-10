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

public class CreateItemFragment extends Fragment {

    private SQLiteDatabase db;
    private EditText itemName;
    private EditText unit;
    private EditText quantity;
    private Button addButton;
    private Button cancelButton;
    private ContentValues itemValues;

    private long ID;
    private boolean checkOne;
    private boolean checkTwo;
    private boolean checkThree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_item, container, false);
        itemValues = new ContentValues();
        ID = getActivity().getIntent().getLongExtra(CreateItem.EVENT_ID, 0);
        SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
        db = helper.getWritableDatabase();

        itemName = view.findViewById(R.id.itemName);
        unit = view.findViewById(R.id.unit);
        quantity = view.findViewById(R.id.quantity);
        addButton = view.findViewById(R.id.addButton);
        cancelButton = view.findViewById(R.id.cancelButton);


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
                    itemValues.put(DatabaseHelper.ITEMNAME, charSequence.toString());
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
                    itemValues.put(DatabaseHelper.ITEMUNIT, charSequence.toString());
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
                    itemValues.put(DatabaseHelper.ITEMQUANTITY, charSequence.toString());
                    checkThree = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkOne && checkTwo && checkThree) {
                    itemValues.put(DatabaseHelper.EVENTID, ID +"");
                    db.insert(DatabaseHelper.DETAIL, null, itemValues);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
