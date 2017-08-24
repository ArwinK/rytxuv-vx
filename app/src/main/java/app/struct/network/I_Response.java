package app.struct.network;

/**
 * Created by arwin on 7/3/17.
 */

public interface I_Response<T, X> {
    void onTaskCompleted(T i);
    void onTaskCompletedMessage(X x);
}
