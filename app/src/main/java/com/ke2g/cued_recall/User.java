package com.ke2g.cued_recall;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created on 10/05/2015.
 */
public class User implements Parcelable {

    ArrayList<Point> points;
    String username;
    String hash;
    int invalidLogins;
    int totalLogins;

    public User(ArrayList<Point> points, String hash, String username) {
        this.points = points;
        this.hash = hash;
        this.username = username;
        this.invalidLogins = 0;
        this.totalLogins = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(points);
        dest.writeString(hash);
        dest.writeString(username);
        dest.writeInt(invalidLogins);
        dest.writeInt(totalLogins);
    }

    // Parcelling part
    public User(Parcel in){
        in.readTypedList(points, Point.CREATOR);
        this.hash = in.readString();
        this.username = in.readString();
        this.invalidLogins = in.readInt();
        this.totalLogins = in.readInt();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getInvalidLogins() {
        return invalidLogins;
    }

    public int getTotalLogins() {
        return totalLogins;
    }

    public String getHash() {
        return hash;
    }

    public void setInvalidLogins(int invalidLogins) {
        this.invalidLogins += invalidLogins;
    }

    public void setTotalLogins(int totalLogins) {
        this.totalLogins += totalLogins;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
