package com.ke2g.cued_recall;

import android.app.ListActivity;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;


public class StatisticsActivity extends ListActivity {

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        // use your custom adapter
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, getUsers());
        ListView listView = getListView();
        listView.addHeaderView(header);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<User> getUsers() {

        ArrayList<User> users = new ArrayList<>();
        Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            if(!entry.getKey().equals("tolerance") && !entry.getKey().equals("numPoints") && !entry.getKey().equals("discreType") ) {
                String json = entry.getValue().toString();
                Gson gson = new Gson();
                User u = gson.fromJson(json, User.class);
                users.add(u);
            }
        }

        return users;
    }
}
