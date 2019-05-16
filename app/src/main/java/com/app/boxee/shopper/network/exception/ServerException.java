package com.app.boxee.shopper.network.exception;

import android.util.Log;

import com.app.boxee.shopper.BuildConfig;

import org.json.JSONObject;

import java.io.IOException;

public class ServerException extends IOException {
    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private int errorCode;
    private String errorMessage;

    public static ServerException getError(String message) {
        String errorMessage = "Error";
        int errorCode = -1;
        if (message != null) {
            try {
                JSONObject jsonObject = new JSONObject(message);
                if (jsonObject.has("code")) {
                    errorCode = jsonObject.getInt("code");
                }
                if (jsonObject.has("message")) {
                    errorMessage = jsonObject.getString("message");
                }
//                if (jsonObject.has("Message")) {
//                    errorMessage = jsonObject.getString("Message");
//                }
//                if (jsonObject.has("errors")) {
//                    Object error = jsonObject.get("errors");
//                    if (error instanceof JSONObject)
//                        errorMessage = ((JSONObject) error).getString("message");
//                    else
//                        errorMessage = ((JSONArray) error).getJSONObject(0).getString("message");
//                }
            } catch (Exception e) {
                if (e instanceof org.json.JSONException) {
                    if (BuildConfig.DEBUG)
                        Log.e("Message", "" + message);
                    // errorMessage = message;
                }
                e.printStackTrace();
            }
        }
        return new ServerException(errorMessage, errorCode);
    }

    public ServerException(String message, int code) {
        super(message);
        errorCode = code;
        errorMessage = message;


    }
}