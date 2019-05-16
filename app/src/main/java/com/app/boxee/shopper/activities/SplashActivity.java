/**
 * Copyright (c) Serhan CANOVA - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Created and written by Serhan CANOVA <serhancanova@gmail.com>, 8.9.2016
 **/

package com.app.boxee.shopper.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.R;
import com.app.boxee.shopper.application.App;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.di.component.DaggerAppComponent;
import com.app.boxee.shopper.repositories.ShopperRepository;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.SplashViewModel;
import com.app.boxee.shopper.view_models.UserProfileViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "FIRE_BASE";
    Env env;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SplashViewModel viewModel;
    private DatabaseReference myRef;
    private DatabaseReference splashTimeRef;
    private String splashTime = "3";
    private TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        tinydb = TinyDB.getInstance(SplashActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply();

                    }
                });
        configureDagger();
        configureViewModel();
        if (!Utils.isOnline(this)) {
            createDailog();
        } else {
            EnvUtil.downloaded = false;
            env = EnvUtil.getEnv(SplashActivity.this.getApplicationContext());
            startPage();
        }
//        configureFirebase();

    }

    private void configureFirebase() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        splashTimeRef = database.getReference("SplashTime");
//        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
//            myRef = database.getReference("Config");
//        } else {
//            myRef = database.getReference("Config-stg");
//        }
//        if (Utils.isOnline(this)) {
//            splashTimeRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    splashTime = dataSnapshot.getValue().toString();
//                    startPage();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    splashTime = "3";
//                    startPage();
//                }
//            });
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    tinydb.putString(Vals.VERSION_ENV, dataSnapshot.child("version").getValue().toString());
//                    tinydb.putString(Vals.URL_EN, dataSnapshot.child("env_en").getValue().toString());
//                    tinydb.putString(Vals.URL_AR, dataSnapshot.child("env_ar").getValue().toString());
//                    EnvUtil.downloaded = false;
//                    env = EnvUtil.getEnv(SplashActivity.this.getApplicationContext());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException());
//                    createDailog();
//                }
//
//            });
//
//        } else {
//            createDailog();
//        }
    }

    private void createDailog() {
        env = EnvUtil.getEnv(SplashActivity.this.getApplicationContext());
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Network not available");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppOk(), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                if (Utils.isOnline(SplashActivity.this)) {
//                    startActivity(new Intent(SplashActivity.this, SplashActivity.class));
//                    SplashActivity.this.finish();
                    EnvUtil.downloaded = false;
                    env = EnvUtil.getEnv(SplashActivity.this.getApplicationContext());
                    startPage();
                } else {
                    SplashActivity.this.finish();
                }
            }
        });
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor("#0044B8"));
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        viewModel.init(this);

//        viewModel.getMataData().observe(this, response -> startPage(response));

//        startPage();

    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }


    void startPage() {
        if (tinydb.getString(Vals.CUSTOMER_TOKEN).equalsIgnoreCase("")
                && !BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
            showBaseUrlDialog();
        } else {
            changePage();
        }
    }

    private void changePage() {
        if (!FirebaseMessagingService.getToken(this).equalsIgnoreCase("")) {
            DaggerAppComponent.builder().application(getApplication()).build().inject((App) getApplication());
            new Handler().postDelayed(
                    () -> {
                        Intent activity = new Intent(SplashActivity.this, MainActivity.class);
                        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("cn")) {
                            activity.putExtra("CN", getIntent().getStringExtra("cn"));
                        }
                        startActivity(activity);
                        overridePendingTransition(0, R.anim.splashfadeout);
                        finish();

                    }, Integer.parseInt(splashTime) * 1000);
        } else {
            changePage();
            Toast.makeText(this, "Fetching FCM token..", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBaseUrlDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_base_url_dialog, null);

        TextView title = dialogView.findViewById(R.id.title);
        EditText baseUrlEdit = dialogView.findViewById(R.id.baseUrlEdit);
        baseUrlEdit.setText(Vals.GET_BASE_URL());
        Button submitBtn = dialogView.findViewById(R.id.submitBtn);
        submitBtn.setText("SUBMIT");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinydb.putString("CUSTOM_BASE_URL", baseUrlEdit.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                changePage();
            }
        });

        dialog.setView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}