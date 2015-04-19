package com.ke2g.cued_recall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doLogin(View V){
        if(!getUsername().equals("")) {
            Intent i = new Intent(this, returnIntent.class);
            startActivityForResult(i, 1);
        }
    }

    private ArrayList<Point> getUserPoints(String username){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(username, "");

        if(json.equals("")){
            return null;
        } else {
            ArrayList<Point> points = gson.fromJson(json, new TypeToken<ArrayList<Point>>(){}.getType());
            return points;
        }
    }

    private int getTolerance(){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        return  Integer.parseInt(SP.getString("tolerance", "75"));
    }

    private String getUsername() {
        EditText et = (EditText) findViewById(R.id.editText_login);
        return et.getText().toString().trim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<Point> points =  data.getParcelableArrayListExtra("RESULT_CLICKS");

            //check if points are the saved ones
            if(CuedRecallIntent.arePointsEqual(points, getUserPoints(getUsername()), getTolerance())){
                Toast.makeText(this, "Correct username and password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Username and password don't match", Toast.LENGTH_LONG).show();
            }
        }
    }
}
