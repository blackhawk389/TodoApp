package com.app.boxee.shopper.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.activities.SplashActivity;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.main_content)
    ConstraintLayout main;
    // FOR DESIGN
    @BindView(R.id.button_1)
    ConstraintLayout btnLogin;
    @BindView(R.id.button_2)
    ConstraintLayout btnTrack;
    @BindView(R.id.button_3)
    ConstraintLayout btnLocation;
    @BindView(R.id.button_4)
    ConstraintLayout btnCard;
    @BindView(R.id.login_txt)
    TextView loginTxt;
    @BindView(R.id.tracking_txt)
    TextView trackingTxt;
    @BindView(R.id.location_txt)
    TextView locationTxt;
    @BindView(R.id.card_txt)
    TextView cardTxt;
    @BindView(R.id.welcome_tv)
    TextView welcomeTxt;
    @BindView(R.id.skip_tv)
    TextView skipTxt;
    private Env env;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        setStrings();
        setFonts();
        listeners();
        return view;
    }

    private void setFonts() {
        if(Utils.getCurrentLanguage().equals("ar")){
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            welcomeTxt.setTypeface(bold);
            loginTxt.setTypeface(bold);
            trackingTxt.setTypeface(bold);
            locationTxt.setTypeface(bold);
            cardTxt.setTypeface(bold);
            skipTxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/worksans_semibold.ttf"));

        }
        else{
            skipTxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf"));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
//        this.configureViewModel();
    }

//    private void configureViewModel() {
//        updateUI();
//    }
//
//    private void updateUI() {
//
//    }

    private void listeners() {
        btnLogin.setOnClickListener(this::onClick);
        btnCard.setOnClickListener(this::onClick);
        btnTrack.setOnClickListener(this::onClick);
        btnLocation.setOnClickListener(this::onClick);
        skipTxt.setOnClickListener(this::onClick);

    }

    private void setStrings() {
        ((MainActivity) getActivity()).hideAppBar(true);
        ((MainActivity) getActivity()).setBack(true);
        env = EnvUtil.getInstance(getActivity());
        loginTxt.setText(env.getAppDashboardSignIn());
        trackingTxt.setText(env.getAppDashboardTrackOrder());
        locationTxt.setText(env.getAppDashboardClexLocations());
        cardTxt.setText(env.getAppdashboardservice());
        if (TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
            skipTxt.setText(env.getAppLanguages().get(0).getName());
        } else {
            skipTxt.setText(env.getAppLanguages().get(1).getName());
        }
        welcomeTxt.setText(env.getAppDashboardWelcome());

    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                //login
                Utils.replaceFragment(getFragmentManager(), LoginFragment.newInstance(), R.id.fragment_container, true, true);
                break;
            case R.id.button_2:
                //tracking
                Utils.replaceFragment(getFragmentManager(), TrackingFragment.newInstance(true, null), R.id.fragment_container, true, true);

                break;
            case R.id.button_3:
                //location
                Utils.replaceFragment(getFragmentManager(), LocationFragment.newInstance(true), R.id.fragment_container, true, true);

                break;
            case R.id.button_4:
                //help
                Utils.replaceFragment(getFragmentManager(), WebViewFragment.newInstance(Vals.GET_BASE_URL()+"cms/services/", true, true) , R.id.fragment_container, true, true);

                break;
            case R.id.skip_tv:
                //skip
                String locale = Locale.getDefault().getLanguage().equals("ar") ? "en" : "ar";
                Utils.setLocale(getActivity(), locale);
                TinyDB.getInstance().putString(Vals.CURRENT_LANGUAGE, Locale.getDefault().getLanguage());
                    Intent refresh = new Intent(getActivity(), SplashActivity.class);
                    startActivity(refresh);
                   this.getActivity().finish();
//                getActivity().recreate();

                break;

        }
    }
}
