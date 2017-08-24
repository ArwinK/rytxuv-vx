package app.struct.network;

import app.struct.utils.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by arwin on 7/3/17.
 */

public interface IParser<T, Y> {
    T requestGET(Y t, ArrayList<NameValuePair> m);
    T requestPOST(Y t, ArrayList<NameValuePair> m);
}
