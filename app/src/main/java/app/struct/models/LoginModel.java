package app.struct.models;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.struct.MainActivity;
import app.struct.R;
import app.struct.databases.LoginDb;
import app.struct.gps.GPSTracker;
import app.struct.helpers.SessionManager;
import app.struct.network.I_Response;
import app.struct.tasks.LoginTask;
import app.struct.views.AltDialog;
import app.struct.views.PDialog;

/**
 * Created by arwin on 7/3/17.
 */

public class LoginModel {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GRANT_TYPE = "grant_type";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_CLIENT_SECRET = "client_secret";
    public static final String KEY_ACCESS_TOKEN = "token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    private final TextView txtWarningText;
    private final TextView txtResetPassword;
    private final GPSTracker gpsTracker;
    Button btnLogin;
    EditText editUserName;
    Activity activity;
    private AltDialog altDialog;
    private EditText editPassword;
    private PDialog pdialog;
    SessionManager session;

    public LoginModel(final Activity activity) {
        this.activity = activity;
        this.activity.setContentView(R.layout.activity_login);
        setId(btnLogin, R.id.btnlogin);
        setIdEdit(editPassword, R.id.txtpassword);
        setIdEdit(editUserName, R.id.txt_username);
        txtWarningText = (TextView) activity.findViewById(R.id.txt_warning_text);
        txtResetPassword = (TextView) activity.findViewById(R.id.txt_reset_password);
        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.startActivity(new Intent(activity, ActivityResetPassword.class));
            }
        });

        pdialog = new PDialog(activity);

        gpsTracker = new GPSTracker(activity, false);
        if (!gpsTracker.providersEnabled()) {
            altDialog = new AltDialog(activity, new AltResponse(), "Turn GPS On", "You have to turn your gps on to use Sales MIS", "Yes I have turned on", "Exit", false);
            altDialog.show();
        }

//        session = new SessionManager(activity);
//
//        if(session.isLoggedIn()){
//            Intent intent = new Intent(activity, MainActivity.class);
//            activity.startActivity(intent);
//        }

        LoginDb lsd = LoginDb.getInstance(activity);
        lsd.getReadableDatabase();
        if (lsd.getRowCount() > 0) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        }
    }

    private void setIdEdit(EditText edit, int id) {
        edit = (EditText) activity.findViewById(id);
        switch (id) {
            case R.id.txtpassword:
                setEditPassword(edit);

                break;
            case R.id.txt_username:
                setEditUserName(edit);
                break;

            default:
                break;
        }
    }

    private void setId(Button btn, int id) {
        btn = (Button) activity.findViewById(id);
        click(btn);
    }

    private void Toaster(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void click(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (btn.getId()) {
                    case R.id.btnlogin:
                        login();
                        break;
                    default:
                        break;
                }

            }
        });

    }

    private void login() {

        if (!gpsTracker.providersEnabled()) {
            altDialog = new AltDialog(activity, new AltResponse(), "Turn GPS On", "You have to turn your gps   to use Sales MIS", "Yes I have turned on", "Exit", false);
            altDialog.show();

            return;
        }
        if (!check_empty(getEditUserName())
                && !check_empty(getEditPassword())) {
            pdialog.start("Logging in");
            new LoginTask(getEditUserName().getText().toString(), getEditPassword().getText().toString(), activity, new LoginCallback()).execute();
        } else {
            Toaster("Complete all fields");
        }

    }

    private boolean check_empty(EditText edit) {
        if (edit.getText().toString().equals("")) {

            return true;
        }
        return false;
    }

    private EditText getEditPassword() {
        return editPassword;
    }

    private void setEditPassword(EditText editPassword) {
        this.editPassword = editPassword;
    }

    private EditText getEditUserName() {
        return editUserName;
    }

    private void setEditUserName(EditText edit_user_name) {
        this.editUserName = edit_user_name;
    }

    public void onResume() {

        if (!gpsTracker.providersEnabled()) {

            altDialog = new AltDialog(activity, new AltResponse(), "Turn GPS On", "You have to turn on your gps to use this application", "Yes I have turned on", "Exit", false);
            altDialog.show();
        }

    }

    private class AltResponse implements I_Response<Boolean, String> {

        @Override
        public void onTaskCompleted(Boolean i) {
            pdialog.end();
            if (i) {
                altDialog.dismiss();
                if (!gpsTracker.providersEnabled()) {
                    altDialog = new AltDialog(activity, new AltResponse(), "Turn GPS On", "You have to turn your gps  on to use Sales MIS", "Yes I have turned it on", "Exit", false);
                    altDialog.show();
                    Toaster("Please turn on your gps");
                }

            } else {
                activity.finish();
            }
        }

        @Override
        public void onTaskCompletedMessage(String s) {
            txtWarningText.setText(s);
        }
    }

    private class LoginCallback implements I_Response<Boolean, String> {

        @Override
        public void onTaskCompleted(Boolean i) {
            pdialog.end();
            txtWarningText.setVisibility(View.GONE);
            if(i){
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }

        }

        @Override
        public void onTaskCompletedMessage(String s) {
            txtWarningText.setText(s);
        }
    }

}
