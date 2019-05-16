package com.app.boxee.shopper.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class ContactUsFragment extends Fragment {

    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.fone)
    TextView fone;
    @BindView(R.id.email)
    TextView email;
    Env env;


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        setFonts();
        env = EnvUtil.getEnv(getActivity());
        ((MainActivity) getActivity()).hideAppBar(false);
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppcontactus(), true);

        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            heading.setTypeface(bold);
            fone.setTypeface(regular);
            email.setTypeface(regular);
        }

    }

    private void setString(){
        heading.setText(env. getAppcontactus());
        email.setText(env. getAppcontactussuport());
        fone.setText(env.getAppcontactussuportphone());
    }

}
