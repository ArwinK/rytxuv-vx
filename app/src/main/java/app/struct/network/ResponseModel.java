package app.struct.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arwin on 7/3/17.
 */

public class ResponseModel {

    public JSONObject getJson() {
        if (json == null){
            String values = "{}";
            try {
                json = new JSONObject(values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    JSONObject json = null;
    JSONArray jarray = null;

    public JSONArray getJarray() {
        return jarray;
    }

    public void setJarray(JSONArray jarray) {
        this.jarray = jarray;
    }

    int status_code = 401;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public Boolean getIs_logged_in() {
        return is_logged_in;
    }

    public void setIs_logged_in(Boolean is_logged_in) {
        this.is_logged_in = is_logged_in;
    }

    Boolean is_logged_in = false;

    @Override
    public String toString() {
        return "ResponseModel{" +
                "json=" + json +
                ", jarray=" + jarray +
                ", status_code=" + status_code +
                ", is_logged_in=" + is_logged_in +
                '}';
    }
}
