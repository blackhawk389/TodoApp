package com.app.boxee.shopper.utils;

import android.support.v4.app.Fragment;


import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.ConsignmentFirebaseData;
import com.app.boxee.shopper.data.Status;
import com.app.boxee.shopper.data.WebShop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FirebaseUtil {


    public static interface FireBaseResponse<T> {
        public void sendResponse(T resp);
    }

    public static void checkData(String barCode, Fragment fragment, FireBaseResponse response) {
        if (barCode == null || barCode.isEmpty())
            return;
        DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference(fragment.getString( R.string.consignment));
        myRef.keepSynced(true);

        //myRef.orderByChild(barCode).orderByValue();
        myRef.child(barCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    fragment.getActivity().runOnUiThread(() -> {
                        try {
                            if (dataSnapshot.hasChildren()) {

                                    if ((BuildConfig.FLAVOR.equalsIgnoreCase("dev") ||BuildConfig.FLAVOR.equalsIgnoreCase("live")) && !barCode.startsWith("1")) {
                                        response.sendResponse(null);
                                        return;
                                    } else if ( BuildConfig.FLAVOR.equalsIgnoreCase("staging") && barCode.startsWith("1")) {
                                        response.sendResponse(null);
                                        return;
                                    }
                                response.sendResponse(dataSnapshot.getValue(ConsignmentFirebaseData.class));
                                return;
                            }
                            response.sendResponse(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.sendResponse(null);
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                response.sendResponse(null);
            }
        });

    }

    public static void checkStatus(String status, Fragment fragment, FireBaseResponse response) {

        DatabaseReference myRef;

        myRef = FirebaseDatabase.getInstance().getReference(fragment.getString((R.string.status)));
        myRef.keepSynced(true);
        //myRef.orderByChild(barCode).orderByValue();
        myRef.child(status).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    fragment.getActivity().runOnUiThread(() -> {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                response.sendResponse(dataSnapshot.getValue(Status.class));
                                return;
                            }
                            response.sendResponse(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                response.sendResponse(null);
            }
        });

    }

    public static void checkWebShop(String webshop, Fragment fragment, FireBaseResponse response) {

        DatabaseReference myRef;

        myRef = FirebaseDatabase.getInstance().getReference(fragment.getString((R.string.webshop)));
        //myRef.orderByChild(barCode).orderByValue();
        myRef.keepSynced(true);

        myRef.child(webshop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    fragment.getActivity().runOnUiThread(() -> {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                response.sendResponse(dataSnapshot.getValue(WebShop.class));
                                return;
                            }
                            response.sendResponse(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                response.sendResponse(null);
            }
        });

    }


}
