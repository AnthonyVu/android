package ca.bcit.ass3.vu_wang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ItemDetails extends AppCompatActivity {
    public static final String ITEM_ID = "itemDetails.id";
    public static final String ITEM_NAME = "itemDetails.name";
    public static final String ITEM_UNIT = "itemDetails.unit";
    public static final String ITEM_QUANTITY = "itemDetails.quantity";
    public static final String ITEM_EVENT_ID = "itemDetails.eventId";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment itemDetailsFragment = new ItemDetailsFragment();
        fragmentTransaction.replace(R.id.itemDetailsPane, itemDetailsFragment);

        fragmentTransaction.commit();
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
