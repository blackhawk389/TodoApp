package com.app.boxee.shopper.fragments;


import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    private Env env;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.feedback)
    Button feedback;
    @BindView(R.id.complaints)
    Button complaints;
    @BindView(R.id.call)
    Button call;

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance(boolean parentDashboard) {

        Bundle args = new Bundle();
        args.putBoolean("parentDashboard", parentDashboard);
        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        setStrings();
        setFonts();
        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
//            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            call.setTypeface(bold);
            chat.setTypeface(bold);
            complaints.setTypeface(bold);
            feedback.setTypeface(bold);
//            heading.setTypeface(bold);
//            barcodesEt.setTypeface(regular);
//            submit.setTypeface(bold);

        }
    }

    private void setStrings() {
        env = EnvUtil.getInstance(getActivity());

        if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
            ((MainActivity) getActivity()).hideAppBar(false);
            ((MainActivity) getActivity()).setBack(true);
            ((MainActivity) getActivity()).changeTitle("Help and Support", true);
        } else {
            Configuration config = getActivity().getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    //in Right To Left layout
//                selectModeRv.setRotationY(180);
                    //relativeLayout.setRotationY(180);

                }
            }
        }
    }

}
