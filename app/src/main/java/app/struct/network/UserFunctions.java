package app.struct.network;

import android.content.Context;

import app.struct.auth.UserDetails;
import app.struct.constants.Constant;
import app.struct.models.LoginModel;
import app.struct.utils.NameValuePair;

import java.util.ArrayList;

/**
 * Created by arwin on 7/3/17.
 */

public class UserFunctions {
    private final UserDetails usd;
    Context context;
    JSONParser jsonParser;

    public UserFunctions(Context context) {
        jsonParser = new JSONParser();
        this.context = context;
        UserDetails usd = new UserDetails();
        this.usd = usd;
    }

    public ResponseModel login(String email, String password) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new NameValuePair(LoginModel.KEY_EMAIL, email));
        params.add(new NameValuePair(LoginModel.KEY_PASSWORD, password));
        ResponseModel rep = jsonParser.requestPOST(Constant.KEY_DOMAIN + "auth/login", params);
        return rep;
    }

//    public ResponseModel sendlead(String text) {
//        ResponseModel rep = jsonParser.requestPOSTJSON(Constant.KEY_RESOURCES + "sales-leads" + "?access_token=" + usd.getToken(context), text);
//        return rep;
//
//    }

    public ResponseModel getMarkers(){
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("", usd.getToken(context)));
        ResponseModel rep = jsonParser.requestGET(Constant.KEY_DOMAIN + "protected/appy-hour" +"?token=" + usd.getToken(context), params);

        return rep;
    }


}
