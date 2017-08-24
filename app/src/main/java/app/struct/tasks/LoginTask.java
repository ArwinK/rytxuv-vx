package app.struct.tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import app.struct.databases.LoginDb;
import app.struct.models.LoginModel;
import app.struct.network.I_Response;
import app.struct.network.ResponseModel;
import app.struct.network.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arwin on 7/3/17.
 */

public class LoginTask extends AsyncTask<String, String, ResponseModel> {
    String username;
    String password;
    Context context;
    I_Response<Boolean, String> booleanI_response;
    Boolean can_connect = false;

    public LoginTask(String username, String password, Context context, I_Response<Boolean, String> booleanI_response) {
        this.username = username;
        this.password = password;
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
            rep = usf.login(username, password);
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
                JSONObject jobj = responseModel.getJson();

                Log.e("Request3", responseModel.getJson().toString());
                LoginDb ldb = LoginDb.getInstance(context);
                ldb.getWritableDatabase();
                ldb.resetTables();
                try {
                    ldb.save(jobj.getString(LoginModel.KEY_ACCESS_TOKEN));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
