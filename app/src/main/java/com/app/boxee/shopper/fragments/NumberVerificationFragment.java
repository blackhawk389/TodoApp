package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.request.NumberVerificationRequest;
import com.app.boxee.shopper.data.response.Data;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.reciever.OtpVerificationReceiver;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.NumberVerificationViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import okhttp3.internal.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberVerificationFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{

    @BindView(R.id.main_content)
    RelativeLayout main;
    @BindView(R.id.btn_submit)
    Button submit;
    @BindView(R.id.one)
    EditText oneet;
    @BindView(R.id.two)
    EditText twoet;
    @BindView(R.id.three)
    EditText threeet;
    @BindView(R.id.four)
    EditText fouret;
    @BindView(R.id.five)
    EditText fiveet;
    @BindView(R.id.six)
    EditText sixet;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.btn_resend)
    Button resend;

    private LoginResponse loginResponse;
    private Env env;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private NumberVerificationViewModel viewModel;
    private String phone;
    String deviceId = "";
    private OtpVerificationReceiver otpVerificationReceiver;
    private ShopperData shopper;

    public NumberVerificationFragment() {
        // Required empty public constructor
    }

    public static NumberVerificationFragment newInstance(GenericResponse<LoginResponse> response, String phone,@Nullable EditProfileRequest request) {

        Bundle args = new Bundle();
        args.putParcelable("login", response.getData());
        args.putString("phone", phone);
        args.putParcelable("EditRequest", request);
        NumberVerificationFragment fragment = new NumberVerificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_verification, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setStrings();
        setFonts();
        listeners();
        smsReceiver();
        return view;
    }

    private void setFonts() {
        if(Utils.getCurrentLanguage().equals("ar")){
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            heading.setTypeface(regular);
            resend.setTypeface(bold);
            submit.setTypeface(bold);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NumberVerificationViewModel.class);
        shopper = viewModel.init();

    }

    private void updateUI(ShopperData response) {
        if (response != null &&response.getShopper()!=null ) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ((MainActivity)getActivity()).setShopperName(
                    response.getShopper().getName() == null || response.getShopper().getName().equals("") &&
                            response.getShopper().getLname() == null || response.getShopper().getLname() == ""
            ? null : response.getShopper().getName() +" "+ response.getShopper().getLname());
            TinyDB tinyDB = TinyDB.getInstance();
            tinyDB.putString(Vals.CUSTOMER_TOKEN,response.getApiToken());
            Utils.replaceFragment(getFragmentManager(), HomeFragment.newInstance(response), R.id.fragment_container, false, true);
        }
    }

    private void listeners() {
        oneet.addTextChangedListener(new GenericTextWatcher(oneet));
        twoet.addTextChangedListener(new GenericTextWatcher(twoet));
        threeet.addTextChangedListener(new GenericTextWatcher(threeet));
        fouret.addTextChangedListener(new GenericTextWatcher(fouret));
        fiveet.addTextChangedListener(new GenericTextWatcher(fiveet));
        sixet.addTextChangedListener(new GenericTextWatcher(sixet));
        submit.setOnClickListener(this::onClick);
        resend.setOnClickListener(this::onClick);
        oneet.setOnFocusChangeListener(this);
    }

    private void setStrings() {
        resend.setText(env.getAppresendcode());
        if (getArguments() != null) {
            loginResponse = getArguments().getParcelable("login");
            phone = getArguments().getString("phone");
//            autoFillPin(loginResponse.getSecurity());
        }
        ((MainActivity) getActivity()).hideAppBar(false);
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppNumberVerificationTitle(), true);
        String[] headingstr = env.getAppNumberVerificationHeading().split("<");

        heading.setText(headingstr[0]+phone.charAt(phone.length()-3)+phone.charAt(phone.length()-2)+phone.charAt(phone.length()-1));
        submit.setText(env.getAppTrackingBtnSubmit());
//        deviceId = "ecQFGNJmD1Y:APA91bH7AsEJrOC5NOcDLiChb_d4xOcnvnk-TGsy3Nw5coxaxFKGjT3wz4gSJ0Mhtmjb05UrqvFLHr9FB3TKVYclSrTJ8gD_mrvQvq7WyugCLaveMziDKPWfQ1cZWDwuThbZ7LRlXBqO";
    }
    private void smsReceiver() {
        otpVerificationReceiver = new OtpVerificationReceiver();


        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(otpVerificationReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        OtpVerificationReceiver.bindListener(messageText -> {
            Log.e("MainActivity", "messageReceived: " + messageText);
            //otpField.setText(messageText);

//            oneet.setText(messageText.charAt(0) + "");
//            twoet.setText(messageText.charAt(1) + "");
//            threeet.setText(messageText.charAt(2) + "");
//            fouret.setText(messageText.charAt(3) + "");
            autoFillPin(messageText);
            // mListener.hideProgress();

//            otpPresenter.onValidateOtp(messageText, getArguments().getString(Constant.PHONE_NUMBER));
            viewModel.init(getActivity(), this, new NumberVerificationRequest(phone, "step-2",getSecurity(), FirebaseMessagingService.getToken(getActivity()),Vals.SOURCE));
            viewModel.getNumberVerificationResponse().observe(this, response -> updateUI(response));

            if (getActivity() != null)
                Toast.makeText(getActivity(), messageText, Toast.LENGTH_SHORT).show();
        });
    }

    private void autoFillPin(String security) {
        oneet.setText(security.charAt(0) + "");
        twoet.setText(security.charAt(1) + "");
        threeet.setText(security.charAt(2) + "");
        fouret.setText(security.charAt(3) + "");
        fiveet.setText(security.charAt(4) + "");
        sixet.setText(security.charAt(5) + "");
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (!oneet.getText().toString().isEmpty() &&
                        !twoet.getText().toString().isEmpty() &&
                        !threeet.getText().toString().isEmpty() &&
                        !fouret.getText().toString().isEmpty() &&
                        !fiveet.getText().toString().isEmpty() &&
                        !sixet.getText().toString().isEmpty()) {
                    if(getArguments().getParcelable("EditRequest")!=null){
                        //secondary number
                        viewModel.getstep2(getActivity(), this,(EditProfileRequest) getArguments().getParcelable("EditRequest") , shopper,getSecurity());
                        viewModel.getNumberVerificationResponsesec().observe(this, response -> updateUIGetStep2(response));

                    }else {
                        viewModel.init(getActivity(), this, new NumberVerificationRequest(phone, "step-2", getSecurity(), FirebaseMessagingService.getToken(getActivity()), Vals.SOURCE));
                        viewModel.getNumberVerificationResponse().observe(this, response -> updateUI(response));
                    }

                } else {
                    Toast.makeText(getActivity(), env.getAppNumberVerificationIncorrectValidation(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_resend:
                deviceId = FirebaseMessagingService.getToken(getActivity());
                viewModel.loginAgain(getActivity(), this, new LoginRequest(phone,"step-1",deviceId,Vals.SOURCE));
                viewModel.getLoginResponse().observe(this, response ->updateResend(response));
                break;
        }
    }

    private void updateUIGetStep2(GenericResponse<Data> response) {
        if(response.getCode()==200 && !response.getError()){
            viewModel.editProfile(getActivity(), this,(EditProfileRequest) getArguments().getParcelable("EditRequest"), shopper);
            viewModel.getShopper().observe(this, response1 -> updateCompletonUI(response1));
            Toast.makeText(getActivity(),response.getData().getSuccessMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCompletonUI(Shopper response) {
        if (response != null) {
            ((MainActivity) getActivity()).setShopperName(
                    response.getName() == null || response.getName().equals("") &&
                            response.getLname() == null || response.getLname() == ""
                            ? null : response.getName() +" "+ response.getLname());
            getFragmentManager().popBackStack();
            getFragmentManager().popBackStack();
        }
    }

    private String getSecurity() {
        return oneet.getText().toString()+twoet.getText().toString()+threeet.getText().toString()+
                fouret.getText().toString()+fiveet.getText().toString()+sixet.getText().toString();
    }

    private void updateResend(GenericResponse<LoginResponse> response) {
        if(response != null) {
            if (response.getCode() ==200) {
                loginResponse = response.getData();
                smsReceiver();
                oneet.setText("");
                oneet.requestFocus();
                twoet.setText("");
                threeet.setText("");
                fouret.setText("");
                fiveet.setText("");
                sixet.setText("");
                Toast.makeText(getActivity(),response.getMessage(),Toast.LENGTH_SHORT).show();

//                autoFillPin(response.getData().getSecurity());
//                Utils.replaceFragment(getFragmentManager(), NumberVerificationFragment.newInstance(response,formatePhone()), R.id.fragment_container, true, true);
            }
            else {
                Toast.makeText(getActivity(),response.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

//        if(oneet.getText().length() == 0|| twoet.getText().length() == 0 || threeet.getText().length() == 0
//                || fouret.getText().length() == 0 || fiveet.getText().length() == 0 || sixet.getText().length() == 0 ) {
//            if (oneet.getText().length() == 0) {
//                oneet.requestFocus();
//            } else if (twoet.getText().length() == 0) {
//                twoet.requestFocus();
//            } else if (threeet.getText().length() == 0) {
//                threeet.requestFocus();
//            } else if (fouret.getText().length() == 0) {
//                fouret.requestFocus();
//            } else if (fiveet.getText().length() == 0) {
//                fiveet.requestFocus();
//            } else if (sixet.getText().length() == 0) {
//                sixet.requestFocus();
//            }
//        }else{
//            Utils.hideKeyboard(getActivity());
//        }

    }


    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.one:
                    if (text.length() == 1)
                        twoet.requestFocus();
                    break;
                case R.id.two:
                    if (text.length() == 1)
                        threeet.requestFocus();
                    break;
                case R.id.three:
                    if (text.length() == 1)
                        fouret.requestFocus();
                    break;
                case R.id.four:
                    if (text.length() == 1)
                        fiveet.requestFocus();
                    break;
                case R.id.five:
                    if (text.length() == 1)
                        sixet.requestFocus();
                    break;
                case R.id.six:
                    Utils.hideKeyboard(getActivity());
                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}


