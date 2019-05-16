package com.app.boxee.shopper.services;

import android.util.Log;

import com.app.boxee.shopper.utils.SubscribeToken;
import com.app.boxee.shopper.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Shafaq on 1/11/2018.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        Utils.sendRegistrationToServer(getApplicationContext(), refreshedToken, null);
        SubscribeToken.subscribeToken(getApplicationContext());
  }

}
