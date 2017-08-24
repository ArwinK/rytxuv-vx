package app.struct.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import app.struct.network.I_Response;

/**
 * Created by arwin on 7/3/17.
 */

public class AltDialog {

    I_Response<Boolean, String> response;
    String title;
    String message;
    Context context;
    String positive, negative;
    Boolean cancellable = true;
    AlertDialog alert11;

    public AltDialog(Context context, I_Response<Boolean, String> response, String title, String message, String positve, String negative) {
        this.response = response;
        this.message = message;
        this.title = title;
        this.context = context;
        this.negative = negative;
        this.positive = positve;
    }

    public AltDialog(Context context, I_Response<Boolean, String> response, String title, String message, String positve, String negative, Boolean cancellable) {
        this.response = response;
        this.message = message;
        this.title = title;
        this.context = context;
        this.negative = negative;
        this.positive = positve;
        this.cancellable = cancellable;
    }

    public AltDialog(Context context, String title, String message) {
        this.message = message;
        this.title = title;
        this.context = context;
    }

    public void showTempAlat() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });


        alert11 = builder1.create();
        alert11.show();
    }


    public void show() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(cancellable);

        builder1.setPositiveButton(
                positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        response.onTaskCompleted(true);
                    }
                });

        builder1.setNegativeButton(
                negative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        response.onTaskCompleted(false);
                        dialog.cancel();
                    }
                });

        alert11 = builder1.create();
        alert11.show();
    }

    public void dismiss() {
        try {
            alert11.dismiss();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
