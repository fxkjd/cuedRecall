package com.ke2g.cued_recall;

import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created on 18/04/2015.
 */
public class CuedRecallIntent {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private static boolean isPointEqual(Point source, Point target, int tolerance) {
        boolean isEqual = false;
        //Log.d(TAG,"Point (x,y): " + source.getX() + ","+ source.getY() + " compared with: "+target.getX()+","+target.getY());
        //Log.d(TAG,"distance: "+((target.getX() - source.getX())*(target.getX() - source.getX()) + (target.getY() - source.getY())*(target.getY() - source.getY())) + " tolerance " + (tolerance*tolerance));
        //(x - center_x)^2 + (y - center_y)^2 < radius^2.
        if ((target.getX() - source.getX()) * (target.getX() - source.getX()) + (target.getY() - source.getY()) * (target.getY() - source.getY()) < (tolerance * tolerance)) {
            isEqual = true;
        }
        return isEqual;
    }

    public static boolean arePointsEqual(ArrayList<Point> source, ArrayList<Point> target, int tolerance) {
        Log.d(TAG, "tolerance: " + tolerance);
        boolean isEqual = true;
        if (source != null && target != null) {
            if (source.size() == target.size()) {
                for (int i = 0; i < source.size(); i++) {
                    if (!isPointEqual(source.get(i), target.get(i), tolerance)) {
                        isEqual = false;
                    }
                    //Log.d(MainActivity.TAG, "Point " + i + " is equal? " + isPointEqual(source.get(i), target.get(i), tolerance));
                }
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    public static String createHash(ArrayList<Point> points,int tolerance) {
        String hash;
        String grids = getGrid(points, tolerance);
        hash = BCrypt.hashpw(grids, BCrypt.gensalt());
        Log.d(TAG, "Grid text " + hash);

        return hash;
    }

    private static String getGrid(ArrayList<Point> points, int tolerance) {
        String grids = "";
        DecimalFormat df = new DecimalFormat("##");
        df.setRoundingMode(RoundingMode.DOWN);

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).getX();
            int y = points.get(i).getY();
            Log.d(TAG, "Point " + i + " grid: " + df.format(x / 8) + "" + df.format(y / 8));
            grids += df.format(x / tolerance) + "" + df.format(y / tolerance);
        }

        Log.d(TAG, "Grid text " + grids);
        return grids;
    }

    public static boolean areHashEqual(ArrayList<Point> points, String userHash, int tolerance) {
        String newGrid = getGrid(points, tolerance);
        Log.d(TAG, "Grid compare: " + newGrid);
        return BCrypt.checkpw(newGrid, userHash);
    }


}
