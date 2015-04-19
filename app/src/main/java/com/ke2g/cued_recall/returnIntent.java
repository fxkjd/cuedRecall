package com.ke2g.cued_recall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;


public class returnIntent extends Activity {

    public static final String TAG = returnIntent.class.getSimpleName();

    int x;
    int y;
    //int tolerance = 100;
    int totalClicks = 3;
    ArrayList<Point> clicks = new ArrayList<Point>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_return_intent);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //tolerance = Integer.parseInt(SP.getString("tolerance", "100"));
        totalClicks = Integer.parseInt(SP.getString("numPoints", "3"));

        ImageView targetImage = (ImageView) findViewById(R.id.image);
        targetImage.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ImageView imageView = ((ImageView) v);
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    float eventX = event.getX();
                    float eventY = event.getY();
                    float[] eventXY = new float[]{eventX, eventY};

                    Matrix invertMatrix = new Matrix();
                    ((ImageView) v).getImageMatrix().invert(invertMatrix);

                    invertMatrix.mapPoints(eventXY);
                    x = Integer.valueOf((int) eventXY[0]);
                    y = Integer.valueOf((int) eventXY[1]);

                    if(clicks.size() < totalClicks){
                        clicks.add(new Point(x, y));
                        //Toast.makeText(returnIntent.this, "X: " + x + ", Y: " + y, Toast.LENGTH_SHORT).show();
                        if(clicks.size() >= totalClicks){
                            returnResult();
                        }
                    }

                }
                return true;
            }

        });
    }

    public void returnResult(){
        Intent i = getIntent();
        i.putParcelableArrayListExtra("RESULT_CLICKS", clicks);
        setResult(RESULT_OK, i);
        finish();
    }

}
