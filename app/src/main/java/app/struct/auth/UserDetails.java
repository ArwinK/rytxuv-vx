package app.struct.auth;

import android.content.Context;

import java.util.HashMap;

import app.struct.databases.LoginDb;
import app.struct.models.LoginModel;

/**
 * Created by arwin on 7/3/17.
 */

public class UserDetails {
    public HashMap<String, String> getAll(Context context) {
        LoginDb ldb = LoginDb.getInstance(context);
        ldb.getWritableDatabase();
        HashMap<String, String> user = new HashMap<String, String>();
        user = ldb.getUserDetails();
        return user;
    }

    public String getToken(Context context) {
        LoginDb ldb = LoginDb.getInstance(context);
        ldb.getWritableDatabase();
        HashMap<String, String> user = new HashMap<String, String>();
        user = ldb.getUserDetails();
        String name = user.get(LoginModel.KEY_ACCESS_TOKEN);
        return name;

    }

    public String getRefreshToken(Context context) {
        LoginDb ldb = LoginDb.getInstance(context);
        ldb.getWritableDatabase();
        HashMap<String, String> user = new HashMap<String, String>();
        user = ldb.getUserDetails();
        String name = user.get(LoginModel.KEY_REFRESH_TOKEN);
        return name;

    }

    public boolean logout(Context activity, boolean logout) {
        LoginDb ldb = LoginDb.getInstance(activity);
        ldb.getWritableDatabase();
        ldb.resetTables();

        return true;
    }
}
