package com.app.boxee.shopper.dailog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.app.boxee.shopper.R;


public class LoaderDialog extends AppCompatDialogFragment {
    private boolean pause;
    private boolean error;
    private boolean start;

    public interface ChangePage {
        void changePage();
    }

    public static void showLoader(Fragment manager) {
        try {
            LoaderDialog loader = (LoaderDialog) manager.getChildFragmentManager().findFragmentByTag("loader");
            if (loader != null)
                return;
            LoaderDialog dailog = new LoaderDialog();
            dailog.setCancelable(false);
            dailog.show(manager.getChildFragmentManager(), "loader");
//            loader.getDialog().setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showLoaderTime(Fragment manager) {
        try {
            LoaderDialog loader = (LoaderDialog) manager.getChildFragmentManager().findFragmentByTag("loader");
            int time = manager.getResources().getInteger(R.integer.timeLoad);
            if (loader != null) {
                loader.setTime(time);
                return;
            }
            loader = new LoaderDialog();
            Bundle bundle = new Bundle();
            bundle.putInt(manager.getString(R.string.timeLoad), manager.getResources().getInteger(R.integer.timeLoad));
            loader.setArguments(bundle);
            loader.show(manager.getChildFragmentManager(), "loader");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTime(int time) {
        if (time > 0) {
            new Handler().postDelayed(() -> {
                try {
                    if (pause) {
                        dismissAllowingStateLoss();
                    } else {
                        dismiss();
                    }
                    if (getParentFragment() instanceof ChangePage) {
                        ((ChangePage) getParentFragment()).changePage();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }, time);
        }
    }

    public static void hideLoader(Fragment manager) {
        LoaderDialog loader = null;
        try {
            loader = (LoaderDialog) manager.getChildFragmentManager().findFragmentByTag("loader");
            if (loader != null) {
                if (loader.pause) {
                    loader.dismissAllowingStateLoss();
                } else {
                    loader.dismiss();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null)
                loader.error = true;
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
        if (error)
            dismiss();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = null;
        try {
            ft = manager.beginTransaction();
            ft.add(this, tag);
            if (pause)
                ft.commitAllowingStateLoss();
            else
                ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!start) {
//            WindowManager.LayoutParams attrs = getDialog().getWindow().getAttributes();
//            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//            getDialog().getWindow().setAttributes(attrs);
            start = true;
            setTime(getArguments() == null ? -1 : getArguments().getInt(getString(R.string.timeLoad), -1));

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.progress, container);
    }
}
