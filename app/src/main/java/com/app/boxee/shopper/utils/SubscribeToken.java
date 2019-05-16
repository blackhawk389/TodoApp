package com.app.boxee.shopper.utils;

import android.content.Context;
import android.provider.Settings;



/**
 * Created by Nadir on 9/26/2017.
 */

public class SubscribeToken implements Runnable {
    private static Thread thread;
    private final Context context;

    private SubscribeToken(Context context) {
        this.context = context;

    }

    public static void subscribeToken(Context context) {
        if (thread == null || !thread.isAlive()) {
            String token = Utils.isSubscribed(context);
            if (token != null) {
                thread = new Thread(new SubscribeToken(context));
                thread.start();
            }
        }
    }

    @Override
    public void run() {
//        ServerUtil.refreshToken(new TokenRequest(Util.getToken(context), Settings.Secure.getString(context.getContentResolver(),
//                Settings.Secure.ANDROID_ID)), context, (LogoutResponse response, ServerException exception) -> {
//            if (response.getCode() == 200) {
//                LoginResponse  user = LoginResponse.getLoginUser(context);
//                Utils.sendRegistrationToServer(context, Util.getToken(context), user.getUserLogin());
//            }
//        });
    }
}
