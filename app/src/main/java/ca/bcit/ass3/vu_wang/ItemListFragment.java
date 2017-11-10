package ca.bcit.ass3.vu_wang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ItemListFragment extends Fragment {

    private long id;
    private String name;
    private String unit;
    private String quantity;
    private String eventId;

    private Cursor cursor;
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    private Button addButton;
    private ListView food_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Intent intent = getActivity().getIntent();
        helper = new DatabaseHelper(getActivity());
        id = intent.getLongExtra(ItemList.ID, 0);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CreateItem.class);
                i.putExtra(CreateItem.EVENT_ID, id);
                startActivity(i);
            }
        });

        food_items = view.findViewById(R.id.foodItems);
        food_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ItemDetails.class);
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.DETAIL,
                new String[]{DatabaseHelper.ID, DatabaseHelper.ITEMNAME},
                DatabaseHelper.EVENTID + " = ?",
                new String[]{id + ""},
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_layout,
                cursor,
                new String[] {DatabaseHelper.ITEMNAME},
                new int[] {R.id.list_content});
        food_items.setAdapter(adapter);
    }
}
