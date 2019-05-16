package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.view_models.LoginViewModel;

import java.util.Locale;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {


    Env env;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String url, boolean isDashBoard, boolean isservie) {

        Bundle args = new Bundle();
        args.putBoolean("isdash", isDashBoard);
        args.putBoolean("isservice", isservie);
        args.putString("url", url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        ButterKnife.bind(this, view);
        WebView myWebView =view.findViewById(R.id.webview);

        env = EnvUtil.getEnv(getActivity());

        String url = getArguments().getString("url");
        if(getArguments().getBoolean("isdash")){
            ((MainActivity) getActivity()).hideAppBar(false);
            ((MainActivity) getActivity()).setBack(true);
        }

        if(!getArguments().getBoolean("isservice")){
            ((MainActivity) getActivity()).changeTitle(env.getAppSideMenuAboutUS(), true);
        }else{
            ((MainActivity) getActivity()).changeTitle(env.getAppdashboardservice(), true);
        }



        if( Locale.getDefault().getLanguage().equals("en")){
            url = url + "en";
        }else{
            url = url + "ar";
        }


        Configuration config = getActivity().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                //in Right To Left layout
//                selectModeRv.setRotationY(180);
               // myWebView.setRotation(360);

            }
        }

        myWebView.loadUrl(url);
        return view;
    }


}
