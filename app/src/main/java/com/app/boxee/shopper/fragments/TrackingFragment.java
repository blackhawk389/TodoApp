package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.adapter.SelectTypeAdapter;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.data.response.TrackingResponse;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.TrackingViewModel;
import com.app.boxee.shopper.view_models.UserProfileViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackingFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_submit)
    Button submit;
    @BindView(R.id.barcodes_et)
    EditText barcodesEt;
    @BindView(R.id.main_content)
    RelativeLayout relativeLayout;
    @BindView(R.id.heading)
    TextView heading;
    String isCN;
    ViewModelProvider.Factory viewModelFactory;
    Env env;

    public TrackingFragment() {
        // Required empty public constructor
    }

    public static TrackingFragment newInstance(boolean parentDashboard, String isCN) {
        Bundle args = new Bundle();
        args.putBoolean("parentDashboard", parentDashboard);
        args.putString("isCN", isCN);
        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getContext());
        if (getArguments().getString("isCN") != null) {
            isCN = getArguments().getString("isCN");
        }
        if (barcodesEt.getText().length() == 12) {
            submit.setEnabled(true);
        }
        setStrings();
        setFonts();
        listeners();
        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            heading.setTypeface(bold);
//            barcodesEt.setTypeface(regular);
            submit.setTypeface(bold);

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        // this.configureViewModel(new String[]{});
    }

//    private void configureViewModel() {
//        updateUI();
//    }

//    private void updateUI() {
//
//
//    }

    private void listeners() {

        submit.setOnClickListener(this::onClick);
        barcodesEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                submit.setEnabled(true);

            }
        });
    }

    private void setStrings() {
        Env env = EnvUtil.getInstance(getActivity());
        if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
            ((MainActivity) getActivity()).hideAppBar(false);
            ((MainActivity) getActivity()).setBack(true);
            ((MainActivity) getActivity()).changeTitle(env.getAppDashboardTrackOrder(), true);

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
        submit.setText(env.getAppTrackingBtnSubmit());
        barcodesEt.setHint(env.getAppTrackingEtTrackingHint());
        heading.setText(env.getAppTrackingHeading());

        if (isCN != null && !isCN.equalsIgnoreCase("")) {
            Utils.replaceFragment(getParentFragment().getFragmentManager(), OrderStatusFragment.newInstance(new String[]{isCN}), R.id.fragment_container, true, true);
            getArguments().putString("isCN", "");
        }

    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
//                if (!barcodesEt.getText().toString().isEmpty() && barcodesEt.getText().toString().length() > 0) {
//                    String[] items = barcodesEt.getText().toString().split("\n");
//                    configureViewModel(items);
//
//                } else {
//                    Toast.makeText(getActivity(), "Please enter tracking number", Toast.LENGTH_LONG).show();
//                }
//                break;

                if ((!barcodesEt.getText().toString().isEmpty()) && barcodesEt.getText().toString().length() > 0) {
                    String[] items = barcodesEt.getText().toString().split("\n");
                    if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
                        Utils.replaceFragment(getFragmentManager(), OrderStatusFragment.newInstance(items), R.id.fragment_container, true, true);
                    } else {
                        Utils.replaceFragment(getParentFragment().getFragmentManager(), OrderStatusFragment.newInstance(items), R.id.fragment_container, true, true);
                    }
                } else {
                    Toast.makeText(getActivity(), env.getAppTrackingEnter(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


//    private void configureViewModel(String[] array) {
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrackingViewModel.class);
//        viewModel.init(this, array, getActivity());
//        viewModel.getTrackingList().observe(this, response -> updateUI(response));
//    }

//    private void updateUI(TrackingResponse response) {
//        if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
//            Utils.replaceFragment(getFragmentManager(), OrderStatusFragment.newInstance((ArrayList<Consignment>) response.getConsignments()), R.id.fragment_container, true, true);
//
//        } else {
//            Utils.replaceFragment(getParentFragment().getFragmentManager(), OrderStatusFragment.newInstance((ArrayList<Consignment>) response.getConsignments()), R.id.fragment_container, true, true);
//        }
//
//    }


}
