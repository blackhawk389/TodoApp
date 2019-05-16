package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.User;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.ProfileViewModel;
import com.app.boxee.shopper.view_models.UserProfileViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.main_content)
    RelativeLayout main;
    @BindView(R.id.fName)
    TextView fName;
    @BindView(R.id.lName)
    TextView lName;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.submitBtn)
    Button submit;
    @BindView(R.id.cancelbtn)
    Button cancel;
    @BindView(R.id.input_num_et)
    EditText phone;
    @BindView(R.id.code_spinner)
    EditText code;

    @BindView(R.id.input_num_et_sec)
    EditText phoneSecondary;
    @BindView(R.id.code_spinner_sec)
    EditText codeSecondary;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;
    private ShopperData shopper;
    private EditProfileRequest request;
    Env env;
    boolean ischange = false;
    String phonenum;
    boolean isfieldchange = false;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setStrings();
        setListners();
        return view;
    }

    private void setListners() {
        submit.setOnClickListener(this::onClick);
        cancel.setOnClickListener(this::onClick);
    }

    private void setStrings() {
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppProfileTitle(), true);
        Configuration config = getActivity().getResources().getConfiguration();
        cancel.setText(env.getAppProfileBtnCancel());
        submit.setText(env.getAppProfileBtnSubmit());
        fName.setHint(env.getAppProfileFirstNameHint());
        lName.setHint(env.getAppProfileLastNameHint());
        email.setHint(env.getAppProfileEmailHint());

        phoneSecondary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!(phoneSecondary.getText().toString().equalsIgnoreCase("")) && phoneSecondary.getText().toString().length() == 9) {
                    ischange = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });




//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//               // main.setRotationY(180);
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        shopper = viewModel.init();
        updateUI(shopper);
    }

    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUI(@Nullable ShopperData user) {
        if (user != null) {
//            String[] name = user.getShopper().getName().split(" ");
//            if (name.length > 1) {
//                fName.setText(name[0]);
//                lName.setText(name[1]);
//            } else {
//                fName.setText(name[0]);
//            }
            try {
                fName.setText(user.getShopper().getName());
                lName.setText(user.getShopper().getLname());
                if(user.getShopper().getEmail().equalsIgnoreCase("")){
                    email.setHint(env.getAppProfileEmailHint());
                }else{
                    email.setText(user.getShopper().getEmail());
                }
                //email.setText(user.getShopper().getEmail().equalsIgnoreCase("") ? email.setHint(env.getAppProfileEmailHint()) : ;
                 phonenum = user.getShopper().getPhone().substring(0, 3);
//                String str = "";
//                for (int i = 0; i < phonenum.length; i++) {
//                    if (i == 0)
//                        str = str + user.getShopper().getPhone().charAt(3);
//                    else
//                        str = str + phonenum[i];
//                }
                phone.setText(user.getShopper().getPhone().substring(3));
                code.setText("+" + phonenum);
                if (user.getShopper().getAlternatePhone() != null) {
                    String code = user.getShopper().getAlternatePhone().substring(0, 3);
                    phonenum = user.getShopper().getAlternatePhone().substring(3);
                    phoneSecondary.setText(phonenum);
                    codeSecondary.setText("+" + code);
                }
            } catch (Exception ex) {

            }

        }

        fName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!fName.getText().toString().equalsIgnoreCase(user.getShopper().getName())){
                    isfieldchange = true;
                }

            }
        });
        lName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!lName.getText().toString().equalsIgnoreCase(user.getShopper().getLname())){
                    isfieldchange = true;
                }

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!email.getText().toString().equalsIgnoreCase(user.getShopper().getEmail())){
                    isfieldchange = true;
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelbtn:
                Utils.removeCurrentFragment(getFragmentManager());
                break;
            case R.id.submitBtn:

                if(isfieldchange
                        && fName.getText().toString().trim().length() > 0 &&
                        email.getText().toString().trim().length() > 0
                        &&  phone.getText().toString().trim().length() > 0
                && lName.getText().toString().trim().length() > 0){

                    viewModel.editProfile(getActivity(), this, new EditProfileRequest(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), FirebaseMessagingService.getToken(getActivity())), shopper);
                    viewModel.getShopper().observe(this, response -> updateCompletonUI(response));

//                    request = new EditProfileRequest(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), FirebaseMessagingService.getToken(getActivity()), codeSecondary.getText().toString() + phoneSecondary.getText().toString());
//                    viewModel.getstep1(getActivity(), this, request, shopper);
//                    viewModel.getAlternateResponse().observe(this, response -> updateUIstep1(response));

                }else if (phoneSecondary.getText().toString().trim().length() == 9 && ischange &&
                        !phoneSecondary.getText().toString().trim().equalsIgnoreCase(phonenum)) {

                    request = new EditProfileRequest(fName.getText().toString(), lName.getText().toString(), email.getText().toString(),
                            FirebaseMessagingService.getToken(getActivity()), codeSecondary.getText().toString() + phoneSecondary.getText().toString());
                    viewModel.getstep1(getActivity(), this, request, shopper);
                    viewModel.getAlternateResponse().observe(this, response -> updateUIstep1(response));

            } else if(ischange && phoneSecondary.getText().toString().trim().equalsIgnoreCase(phonenum)){

                    request = new EditProfileRequest(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), FirebaseMessagingService.getToken(getActivity()),
                            codeSecondary.getText().toString() + phoneSecondary.getText().toString());
                    viewModel.getstep1(getActivity(), this, request, shopper);
                    viewModel.getAlternateResponse().observe(this, response -> updateUIstep1(response));

                }else{
//                        (fName.getText().toString().trim().length() > 0
//                        && lName.getText().toString().trim().length() > 0
//                        && email.getText().toString().trim().length() > 0
//                        && phone.getText().toString().trim().length() > 0
//                        && code.getText().toString().trim().length() > 0) {
//                    if (phoneSecondary.getText().toString().trim().length() > 0 && phoneSecondary.getText().toString().trim().length() == 9) {
//                        request = new EditProfileRequest(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), FirebaseMessagingService.getToken(getActivity()), codeSecondary.getText().toString() + phoneSecondary.getText().toString());
//                        viewModel.getstep1(getActivity(), this, request, shopper);
//                        viewModel.getAlternateResponse().observe(this, response -> updateUIstep1(response));
////    Utils.replaceFragment(getFragmentManager(),NumberVerificationFragment.newInstance());
////                    } else {
//                        Toast.makeText(getActivity(), "Invalid secondary phone number", Toast.LENGTH_LONG).show();
//                    }
//                }else{

                  if(phoneSecondary.getText().toString().trim().length() < 9){
                      Toast.makeText(getActivity(), env.getAppprofilesecondarynumber(), Toast.LENGTH_LONG).show();
                  }else{
                      Toast.makeText(getActivity(), env.getAppenerateticketenterdetail(), Toast.LENGTH_LONG).show();
                  }
                }

                break;
        }

    }

    private void updateUIstep1(GenericResponse<LoginResponse> response) {
        if (response != null && response.getCode() == 200 && !response.getError()) {
            Utils.replaceFragment(getFragmentManager(), NumberVerificationFragment.newInstance(response,
                    codeSecondary.getText().toString() + phoneSecondary.getText().toString(), request), R.id.fragment_container, true, true);
        }
    }

    private void updateCompletonUI(Shopper response) {
        if (response != null) {
            ((MainActivity) getActivity()).setShopperName(response.getName() == null || response.getName().equals("") &&
                    response.getLname() == null || response.getLname() == ""
                    ? null : response.getName() +" "+ response.getLname());
            getFragmentManager().popBackStack();
        }
    }
}
