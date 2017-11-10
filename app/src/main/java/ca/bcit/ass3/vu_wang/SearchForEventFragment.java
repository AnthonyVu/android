package ca.bcit.ass3.vu_wang;

import android.content.Intent;
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

public class SearchForEventFragment extends Fragment {

    private EditText eventName;
    private Button searchButton;
    private Button cancelButton;
    private String event;
    private boolean check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_for_event, container, false);

        eventName = view.findViewById(R.id.eventName);
        searchButton = view.findViewById(R.id.searchButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        check = false;
        eventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    check = false;
                } else {
                    check = true;
                    event = charSequence.toString();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check) {
                    Intent i = new Intent(view.getContext(), SearchedEvents.class);
                    i.putExtra(SearchedEvents.EVENT_NAME, event);
                    startActivity(i);
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
}
