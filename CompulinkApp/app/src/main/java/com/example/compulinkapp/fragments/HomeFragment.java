package com.example.compulinkapp.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;
import com.example.compulinkapp.classes.FileHelper;
import com.example.compulinkapp.classes.Notification;
import java.util.ArrayList;
import java.util.Calendar;

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

        /**
         * Initialisation of variables required for home screen list
         */
        final EditText itemEntered = view.findViewById(R.id.list_item);
        final Button addItem = view.findViewById(R.id.list_add_btn);
        ListView listView = view.findViewById(R.id.todo_list);
        /**
         * Populate the list with items from the created file if there are items stored in the file
         */
        items = FileHelper.readData(getContext());
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, items);
        listView.setAdapter(adapter);

        /**
         * Adds item to the list when clicked
         */
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

                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
                addItem.startAnimation(anim);
            }
        });

        /**
         * When an item in the list is Long clicked on it will be removed
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                adapter.notifyDataSetChanged();
                FileHelper.writeData(items, getContext());
                Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        /**
         * User friendliness toast message
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "To delete an Item press and hold the item", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Code for creating and scheduling the notification
         */
        Calendar repeatTime = Calendar.getInstance();
        repeatTime.set(Calendar.HOUR_OF_DAY, 9);
        repeatTime.set(Calendar.MINUTE, 0);

        /**
         * Ensures that if the time scheduled is in the past the notification gets moved on another day
         * This will prevent notifications from displaying immediately on page open if it's past scheduled time
         */
        Calendar now = Calendar.getInstance();
        if(repeatTime.before(now))
        {
            repeatTime.add(Calendar.DAY_OF_MONTH, 1); //Add one day to the alarm
        }

        Intent intent = new Intent(getContext(), Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * Notification or action is set to take place daily at 9:00AM
         */
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, repeatTime.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
    }
}
