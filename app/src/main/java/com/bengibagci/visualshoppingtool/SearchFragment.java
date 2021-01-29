package com.bengibagci.visualshoppingtool;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SearchFragment extends Fragment {

    public static  String btn;
    public static ListView productList;

    public static Button searchButton;
    public static EditText editTextInput;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextInput = view.findViewById(R.id.editTextInput);
        productList = view.findViewById(R.id.productList);

        radioGroup = view.findViewById(R.id.radioGroup);

        searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);
                btn = radioButton.getText().toString();
                String term = editTextInput.getText().toString();

                new ProductInformation(getActivity().getApplicationContext(), term, btn).execute();
            }
        });
    }
}
