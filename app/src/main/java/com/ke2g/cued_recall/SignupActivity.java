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

import java.util.ArrayList;


public class SignupActivity extends ActionBarActivity {

    public static final String TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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

    public void doSignup(View V){
        if(!getUsername().equals("")) {
            Intent i = new Intent(this, returnIntent.class);
            startActivityForResult(i, 1);
        }
    }

    private void saveUserPoints(String username, ArrayList<Point> points){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        String hash = CuedRecallIntent.createHash(points);
        User u = new User(points, hash, username);
        Gson gson = new Gson();
        String json = gson.toJson(u);
        prefsEditor.putString(username, json);
        prefsEditor.commit();
    }

    private String getUsername() {
        EditText et = (EditText) findViewById(R.id.editText_signup);
        return et.getText().toString().trim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //get points from returnIntent
            ArrayList<Point> points =  data.getParcelableArrayListExtra("RESULT_CLICKS");

            //save to shared preferences username and points
            saveUserPoints(getUsername(), points);
            Toast.makeText(this, "Correct sign up", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }


}
