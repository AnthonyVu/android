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

public class ItemDetailsFragment extends Fragment {

    private TextView itemName;
    private TextView itemUnit;
    private TextView itemQuantity;
    private Button deleteItemButton;
    private Button editItemButton;

    private String id;
    private String name;
    private String unit;
    private String quantity;

    private SQLiteDatabase db;
    private SQLiteOpenHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        helper = new DatabaseHelper(getActivity());

        itemName = view.findViewById(R.id.itemName);
        itemUnit = view.findViewById(R.id.itemUnit);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        deleteItemButton = view.findViewById(R.id.deleteButton);
        editItemButton = view.findViewById(R.id.editButton);

        id = getActivity().getIntent().getStringExtra(ItemDetails.ITEM_ID);
        name = getActivity().getIntent().getStringExtra(ItemDetails.ITEM_NAME);
        unit = getActivity().getIntent().getStringExtra(ItemDetails.ITEM_UNIT);
        quantity = getActivity().getIntent().getStringExtra(ItemDetails.ITEM_QUANTITY);

        itemName.setText(getResources().getString(R.string.itemLocale) + ": " + name);
        itemUnit.setText(getResources().getString(R.string.unitLocale) + ": " + unit);
        itemQuantity.setText(getResources().getString(R.string.quantityLocale) + ": " + quantity);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getWritableDatabase();
                db.delete(DatabaseHelper.DETAIL, DatabaseHelper.ID + " = ?", new String[]{id});
                getActivity().finish();
            }
        });

        editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditItem.class);
                i.putExtra(EditItem.ITEM_ID, id);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }
}
