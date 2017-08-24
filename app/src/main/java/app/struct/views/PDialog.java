package app.struct.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by arwin on 7/3/17.
 */

public class PDialog {
    Context context;
    AlertDialog pdialog;

    public PDialog() {
        // TODO Auto-generated constructor stub
    }

    public PDialog(Context context) {
        this.context = context;
        pdialog = new ProgressDialog(context);
    }

    public void start(String text) {

        pdialog.show();
        pdialog.setMessage(text);
        pdialog.setTitle(text);

    }

    public void start(String text, Boolean cancelable) {

        pdialog.show();
        pdialog.setMessage(text);
        pdialog.setTitle(text);
        pdialog.setCancelable(cancelable);

    }

    public void update(String text) {
        pdialog.setMessage(text);
    }

    public void end() {
        pdialog.dismiss();
    }

}
