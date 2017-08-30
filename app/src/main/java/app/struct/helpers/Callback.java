package app.struct.helpers;

import app.struct.network.I_Response;
import app.struct.views.PDialog;

/**
 * Created by arwin on 8/29/17.
 */

public class Callback implements I_Response<Boolean, String> {

    private PDialog pDialog;
    public Callback(PDialog pDialog) {
        this.pDialog = pDialog;
    }

    @Override
    public void onTaskCompleted(Boolean i) {
        pDialog.end();
    }

    @Override
    public void onTaskCompletedMessage(String s) {

    }
}
