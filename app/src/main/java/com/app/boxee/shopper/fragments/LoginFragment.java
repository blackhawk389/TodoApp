package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.dailog.SearchListDialog;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.LoginRequest;
import com.app.boxee.shopper.data.response.LoginResponse;
import com.app.boxee.shopper.database.entity.GenericResponse;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    // FOR DESIGN
    @BindView(R.id.main_content)
    ConstraintLayout main;
    @BindView(R.id.login_txt)
    TextView loginTxt;
    @BindView(R.id.code_spinner)
    EditText codeSpinner;
    @BindView(R.id.input_num_et)
    EditText editText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.login_aggrement)
    TextView aggrementTxt;
    private List<String> CityStrings;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel viewModel;
    private Env env;
    String deviceId = "";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getInstance(getActivity());
        setStrings();
        setFonts();
        listeners();
        return view;

    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            loginTxt.setTypeface(bold);
            loginButton.setTypeface(bold);
            aggrementTxt.setTypeface(regular);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }


    private void listeners() {
        loginButton.setOnClickListener(this::onClick);
//        codeSpinner.setOnClickListener(this::onClick);
        aggrementTxt.setOnClickListener(this::onClick);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                if (editable.length() == 4) {
//                    editText.setText(editText.getText().toString() + " ");
//                    int pos = editText.getText().length();
//                    editText.setSelection(pos);
//                }
            }
        });
    }

    private void setStrings() {
        ((MainActivity) getActivity()).hideAppBar(true);
        ((MainActivity) getActivity()).setBack(true);
        aggrementTxt.setText(Html.fromHtml(env.getAppLoginLoginTerms()));
        loginTxt.setText(env.getAppLoginSignIn());
        loginButton.setText(env.getAppLoginBtnLogin());
        codeSpinner.setText(env.getApp99());

//        deviceId = Settings.Secure.getString(getActivity().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        deviceId = "ecQFGNJmD1Y:APA91bH7AsEJrOC5NOcDLiChb_d4xOcnvnk-TGsy3Nw5coxaxFKGjT3wz4gSJ0Mhtmjb05UrqvFLHr9FB3TKVYclSrTJ8gD_mrvQvq7WyugCLaveMziDKPWfQ1cZWDwuThbZ7LRlXBqO";
//        aggrementTxt.setText(env.getAppLoginLoginTerms());
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                if (!editText.getText().toString().isEmpty()&&editText.getText().toString().trim().length()==9) {
                    viewModel.init(getActivity(), this, new LoginRequest(formatePhone(), "step-1", FirebaseMessagingService.getToken(getActivity()), Vals.SOURCE));
                    viewModel.getLoginResponse().observe(this, response -> updateUI(response));

                } else {
                    if (editText.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), env.getAppLoginNumberRequiredValidation(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), env.getAppLoginIncorrectNumberValidation(), Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            case R.id.code_spinner:
                CityStrings = new ArrayList<>();
                CityStrings.add("Saudi Arabia");
                SearchListDialog searchListDialog = new SearchListDialog();
                searchListDialog.setList(CityStrings);
                searchListDialog.setCancelable(true);
                searchListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Saudi Arabia")) {
                            codeSpinner.setText(env.getApp99());
                            searchListDialog.dismiss();
                        }

                    }
                });

                searchListDialog.show(getFragmentManager(), null);
                break;
            case R.id.login_aggrement:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://clexsa.com/terms-use-privacy-policy"));
                startActivity(browserIntent);
                break;

        }
    }

    private void updateUI(GenericResponse<LoginResponse> response) {
        if (response != null) {

            if (response.getCode()== 200 ) {
                Utils.replaceFragment(getFragmentManager(), NumberVerificationFragment.newInstance(response, formatePhone(),null), R.id.fragment_container, true, true);
            } else {
                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String formatePhone() {
        char[] phone = editText.getText().toString().replaceAll(" ", "").toCharArray();
        String abc = "";
        for (int i = 0; i < phone.length; i++) {
//          if(i==2){
//              abc=abc+"-"+phone[i];
//          }
            if (i == 0) {
                abc = abc + phone[i];
            } else {
                abc = abc + phone[i];
            }

        }

        return codeSpinner.getText().toString() + abc;
    }
}
