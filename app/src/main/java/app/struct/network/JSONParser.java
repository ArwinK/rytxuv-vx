package app.struct.network;

import android.content.SharedPreferences;
import android.util.Log;

import app.struct.LoginActivity;
import app.struct.models.MarkerModel;
import app.struct.utils.NameValuePair;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by arwin on 7/3/17.
 */

public class JSONParser implements IParser<ResponseModel, String> {

    @Override
    public ResponseModel requestGET(String uri, ArrayList<NameValuePair> map) {
        ResponseModel resp = new ResponseModel();
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();

        for (int i = 0; i < map.size(); i++) {
            urlBuilder.addQueryParameter(map.get(i).key, map.get(i).value);
        }

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.e("Request", request.toString());
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (response.isSuccessful()) {
                resp.setIs_logged_in(true);
                resp.setStatus_code(response.code());

                try {
                    resp.setJson(new JSONObject(response.body().string()));
                    JSONArray restArray = resp.getJson().getJSONArray("appy_hours");

                    //ArrayList marker = MarkerModel.makeArrayList(restArray);


                    //Log.e("RequestA", resp.getJson().getJSONArray("appy_hours").getJSONObject(1).getJSONObject("user").toString());
                    Log.e("RequestB", restArray.toString());

                } catch (JSONException j) {
                    j.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                } catch (IllegalStateException h) {
                    h.printStackTrace();
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return resp;

    }

    @Override
    public ResponseModel requestPOST(String uri, ArrayList<NameValuePair> map) {
        Logger log = LoggerFactory.getLogger(LoginActivity.class);
        log.error("Network", uri);
        ResponseModel resp = new ResponseModel();
        OkHttpClient client = new OkHttpClient();

        MultipartBuilder builder = new MultipartBuilder();
        builder.type(MultipartBuilder.FORM);

        for (int i = 0; i < map.size(); i++) {
            builder.addFormDataPart(map.get(i).key, map.get(i).value);

        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(uri)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            resp.setStatus_code(404);
            e.printStackTrace();
        }

        try {
            if (response.isSuccessful()) {
                try {

                    JSONObject values = new JSONObject(response.body().string());
                    resp.setJson(values);

                    // int status = Integer.parseInt(values.getString("status_code"));

                    resp.setStatus_code(response.code());
//                    if(status == 200){
                        resp.setIs_logged_in(true);
//                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                resp.setStatus_code(response.code());
                try {
                    resp.setJson(new JSONObject(response.body().string()));
                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.e("Response", resp.toString());
        return resp;
    }
}
