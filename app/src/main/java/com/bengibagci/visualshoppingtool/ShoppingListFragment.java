package com.bengibagci.visualshoppingtool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import static android.view.View.VISIBLE;

public class ShoppingListFragment extends Fragment {

    private PageViewModel pageViewModel;
    private String product;
    private ListView shoppingList;
    private EditText addItemInput;
    private ArrayAdapter<String> sAdapter;
    private Button addButton;
    private AlertDialog dialog;
    private Button backButton;
    private LinearLayout productListLayout;
    private LinearLayout shoppingListLayout;

    public static ListView productList;

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productList = view.findViewById(R.id.productList);

        shoppingList = view.findViewById(R.id.shopping_listView);
        addItemInput = view.findViewById(R.id.item_editText);
        addButton = view.findViewById(R.id.add_button);
        backButton = view.findViewById(R.id.backButton);
        productListLayout = view.findViewById(R.id.productListLayout);
        shoppingListLayout = view.findViewById(R.id.shoppingListLayout);

        shoppingListLayout.setVisibility(VISIBLE);
        productListLayout.setVisibility(View.INVISIBLE);


        sAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1);
        shoppingList.setAdapter(sAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = addItemInput.getText().toString();
                sAdapter.add(item);
                sAdapter.notifyDataSetChanged();
                addItemInput.setText("");
                pageViewModel.setProductList(shoppingList);
            }
        });

        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                product = shoppingList.getItemAtPosition(position).toString();
                Toast.makeText(getActivity().getApplicationContext(),product,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select your choice");

                final String[] markets = {"Migros", "A101", "ŞOK"};
                int checkedItem = -1;
                builder.setSingleChoiceItems(markets, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        shoppingListLayout.setVisibility(View.INVISIBLE);
                        productListLayout.setVisibility(VISIBLE);
                        switch(item)
                        {
                            case 0:
                                new ProductInformation(getActivity().getApplicationContext(), product, markets[0]).execute();
                                break;
                            case 1:
                                new ProductInformation(getActivity().getApplicationContext(), product, markets[1]).execute();
                                break;
                            case 2:
                                new ProductInformation(getActivity().getApplicationContext(), product, markets[2]).execute();
                                break;
                        }
                        dialog.dismiss();
                    }
                });

                dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListLayout.setVisibility(View.INVISIBLE);
                shoppingListLayout.setVisibility(VISIBLE);
            }
        });
    }
}
