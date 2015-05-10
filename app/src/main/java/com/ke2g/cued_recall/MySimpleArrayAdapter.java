package com.ke2g.cued_recall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created on 10/05/2015.
 */

public class MySimpleArrayAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final ArrayList<User> values;

    public MySimpleArrayAdapter(Context context, ArrayList<User> values) {
        super(context, R.layout.row_view, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_view, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tv_username);
        TextView tvTries = (TextView) rowView.findViewById(R.id.tv_tries);

        textView.setText(values.get(position).getUsername());
        String triesText = (values.get(position).getTotalLogins() - values.get(position).getInvalidLogins()) +" of "+ values.get(position).getTotalLogins() + " attempts";
        tvTries.setText(triesText);
        return rowView;
    }
}