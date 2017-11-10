package ca.bcit.ass3.vu_wang;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailsFragment extends Fragment {

    private long id;
    private TextView eventName;
    private TextView eventDate;
    private TextView eventTime;
    private Button deleteEventButton;
    private Button editButton;

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        Intent intent = getActivity().getIntent();
        id = intent.getLongExtra(ItemList.ID, 0);

        deleteEventButton = view.findViewById(R.id.deleteEventButton);
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new DatabaseHelper(getActivity());
                db = helper.getWritableDatabase();
                db.delete(DatabaseHelper.MASTER, DatabaseHelper.ID + " = ?", new String[]{id+""});
                db.delete(DatabaseHelper.DETAIL, DatabaseHelper.EVENTID + " = ?", new String[]{id+""});
//                finish();
            }
        });

        editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditEvent.class);
                i.putExtra(EditEvent.EVENT_ID, id + "");
                startActivity(i);
//                finish();
            }
        });

        eventName = view.findViewById(R.id.eventName);
        eventName.setText(getResources().getString(R.string.eventNameLocale) + ": " + intent.getStringExtra(ItemList.EVENT_NAME));
        eventDate = view.findViewById(R.id.eventDate);
        eventDate.setText(getResources().getString(R.string.eventDateLocale) + ": " + intent.getStringExtra(ItemList.EVENT_DATE));
        eventTime = view.findViewById(R.id.eventTime);
        eventTime.setText(getResources().getString(R.string.eventTimeLocale) + ": " + intent.getStringExtra(ItemList.EVENT_TIME));

        return view;
    }
}
