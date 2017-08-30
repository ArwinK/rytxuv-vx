package app.struct.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import app.struct.databases.LoginDb;
import app.struct.models.LoginModel;
import app.struct.models.MarkerModel;
import app.struct.network.I_Response;
import app.struct.network.ResponseModel;
import app.struct.network.UserFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arwin on 7/3/17.
 */

public class MainTask extends AsyncTask<String, String, ResponseModel> {
    Context context;
    I_Response<Boolean, String> booleanI_response;
    Boolean can_connect = false;

    public MainTask(Context context, I_Response<Boolean, String> booleanI_response) {
        this.booleanI_response = booleanI_response;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            can_connect = true;
        }

    }

    @Override
    protected ResponseModel doInBackground(String... params) {
        UserFunctions usf = new UserFunctions(context);
        ResponseModel rep = null;
        if (can_connect) {
            rep = usf.getMarkers();

            if (rep.getJson() != null) {
                try {
                    // get stored json of all the active restaurants
                    String array = rep.getJson().getJSONArray("appy_hours").toString();

                    SharedPreferences resList = context.getSharedPreferences("res_list", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = resList.edit();
                    editor.putString("resArray", array);
                    editor.apply();

                    Log.d("resArray", array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return rep;
    }

    @Override
    protected void onPostExecute(ResponseModel responseModel) {
        if (!can_connect) {
            booleanI_response.onTaskCompleted(false);
            booleanI_response.onTaskCompletedMessage("Cannot connect to server.Check your connection settings");
            return;
        }
        switch (responseModel.getStatus_code()) {
            case 404:
                booleanI_response.onTaskCompleted(false);
                booleanI_response.onTaskCompletedMessage("Server Not Found");
                break;
            case 500:
                booleanI_response.onTaskCompleted(false);
                booleanI_response.onTaskCompletedMessage("A server error occurred.Please contact system administrator");
                break;
            case 200:
                booleanI_response.onTaskCompleted(true);
                booleanI_response.onTaskCompletedMessage("You successfully logged in");
                break;
            case 401:
                booleanI_response.onTaskCompleted(false);
                booleanI_response.onTaskCompletedMessage("Wrong username or password");
                break;

        }


    }
}
