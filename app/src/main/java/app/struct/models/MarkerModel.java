package app.struct.models;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arwin on 8/17/17.
 */

public class MarkerModel {
    private String longitude;
    private String title;
    private String latitude;
    private String snippet;
    private int iconResID;
    private int ID;
    private String name;
    private String email;
    private String description;
    private String location;
    private String dayOfWeek;
    private String hourType;
    private String companyName;
    private String date;
    private String dateFrom;
    private String dateTo;
    private String userId;
    private String userDescription;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getIconResID() {
        return iconResID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setHourType(String hourType) {
        this.hourType = hourType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getHourType() {
        return hourType;
    }

    public String getDate() {
        return date;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<MarkerModel> makeArrayList(JSONArray jsonArray) {
        ArrayList<MarkerModel> markerData = new ArrayList<MarkerModel>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                markerData.add(makeFromJson(jsonArray.getJSONObject(i)));
                //Log.e("LAS", markerData.get(i).getDayOfWeek());
            } catch (JSONException e) {
                //if(doLog) Log.e(TAG, CTAG + e.toString());
            }
        }

        return markerData;
    }

    public static MarkerModel makeFromJson(JSONObject jmarkerUser) {
        MarkerModel marker = new MarkerModel();
        try {

            JSONObject userJson = jmarkerUser.getJSONObject("user");

            // set user information from Json
//            marker.setID(userJson.getInt("id"));
//            marker.setName(userJson.getString("name"));
//            marker.setEmail(userJson.getString("email"));
//            marker.setUserDescription(userJson.getString(""));
            marker.setLatitude(userJson.getString("latitude"));
            marker.setLongitude(userJson.getString("longitude"));
//            marker.setLocation(userJson.getString("location"));

            // set company information from json
            marker.setCompanyName(jmarkerUser.getString("name"));
            marker.setHourType(jmarkerUser.getString("appy_hour_type"));
//            marker.setDayOfWeek(jmarkerUser.getString("day_of_week"));
//            marker.setDate(jmarkerUser.getString("date"));
//            marker.setDateFrom(jmarkerUser.getString("from"));
//            marker.setDateTo(jmarkerUser.getString("to"));
//            marker.setDescription(jmarkerUser.getString("description"));
//            marker.setUserId(jmarkerUser.getString("user_id"));


        } catch (JSONException e) {
            //if(doLog) Log.e(TAG, CTAG + e.toString());
        }
        return marker;
    }
}
