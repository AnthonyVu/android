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
import android.widget.ListView;

public class SearchedEventsFragment extends Fragment {

    private String eventName;
    private ListView searchedEvents;
    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;
    private Cursor cursor;

    private String name;
    private String date;
    private String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searched_events, container, false);


        eventName = getActivity().getIntent().getStringExtra(SearchedEvents.EVENT_NAME);
        searchedEvents = view.findViewById(R.id.searchedEvents);

        helper = new DatabaseHelper(getActivity());
        db = helper.getReadableDatabase();
        cursor = db.query(DatabaseHelper.MASTER,
                new String[]{DatabaseHelper.ID, DatabaseHelper.EVENTNAME},
                DatabaseHelper.EVENTNAME + " = ?" ,
                new String[]{eventName},
                null,
                null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_layout,
                cursor,
                new String[] {DatabaseHelper.EVENTNAME},
                new int[] {R.id.list_content});
        searchedEvents.setAdapter(adapter);

        searchedEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ItemList.class);
                db = helper.getReadableDatabase();
                cursor = db.query(DatabaseHelper.MASTER,
                        new String[] {DatabaseHelper.ID, DatabaseHelper.EVENTNAME, DatabaseHelper.EVENTDATE, DatabaseHelper.EVENTTIME},
                        DatabaseHelper.ID + " = ?",
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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
