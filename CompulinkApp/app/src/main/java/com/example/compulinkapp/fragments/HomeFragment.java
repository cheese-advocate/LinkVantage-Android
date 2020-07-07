package com.example.compulinkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;
import com.example.compulinkapp.classes.FileHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Home");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText itemEntered = view.findViewById(R.id.list_item);
        Button addItem = view.findViewById(R.id.list_add_btn);
        ListView listView = view.findViewById(R.id.todo_list);

        items = FileHelper.readData(getContext());
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, items);
        listView.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemEntered.getText().toString();

                if(item.trim().equals(""))
                {
                    Toast.makeText(getContext(), "No text entered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    adapter.add(item);
                    itemEntered.setText("");

                    FileHelper.writeData(items, getContext());

                    Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                adapter.notifyDataSetChanged();
                FileHelper.writeData(items, getContext());
                Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
