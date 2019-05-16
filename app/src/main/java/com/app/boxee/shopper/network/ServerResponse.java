package com.app.boxee.shopper.network;

import org.json.JSONException;

import java.text.ParseException;

/**
 * Created by Nadir on 12/15/2017.
 */

public interface ServerResponse<T> {
    void sendData(T response) throws ParseException, JSONException;
    interface ServerBack<U>{
        void sendData(U response, String t) throws ParseException, JSONException;
    }
}
