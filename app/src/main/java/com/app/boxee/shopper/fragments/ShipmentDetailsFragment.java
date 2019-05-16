package com.app.boxee.shopper.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.adapter.TimeLineAddressAdapter;
import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.ShipmentDetailsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentDetailsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerViewAddress)
    RecyclerView recyclerView;
    @BindView(R.id.barcode)
    TextView barcode;
    @BindView(R.id.expected_date_value)
    TextView date;
    @BindView(R.id.expected_date_txt)
    TextView datetxt;
    @BindView(R.id.expected_time_txt)
    TextView webshoptxt;
    @BindView(R.id.webshop_value)
    TextView webshop;
    @BindView(R.id.department_status_value)
    TextView status;
    @BindView(R.id.amount_value)
    TextView amount;
    @BindView(R.id.cash_type)
    TextView paymentType;
    @BindView(R.id.main_content)
    NestedScrollView relativelayout;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShipmentDetailsViewModel viewModel;

    private TimeLineAddressAdapter mTimeLineAdapter;
    private Consignment consignment;
    SelectModel statusModel;
    private ArrayList<AddressModel> addressModels;
    private Env env;
    @BindView(R.id.deparment_Status_txt)
    TextView statustxt;

    @BindView(R.id.amount_txt)
    TextView amounttxt;

    public ShipmentDetailsFragment() {
        // Required empty public constructor
    }

    public static ShipmentDetailsFragment newInstance(Consignment consignmentFirebaseData) {

        Bundle args = new Bundle();
        args.putParcelable("consignment", consignmentFirebaseData);
        //args.putParcelable("status_tracking", status);
        ShipmentDetailsFragment fragment = new ShipmentDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_details, container, false);
        ButterKnife.bind(this, view);
        env = EnvUtil.getEnv(getActivity());
        setViews();
        setStrings();
        listeners();
        return view;
    }

    private void setViews() {
        if (getArguments() != null) {
            consignment = (Consignment) getArguments().getParcelable("consignment");
//            statusModel = (SelectModel) getArguments().getParcelable("status_tracking");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAddressAdapter(addressModels
                , Orientation.VERTICAL, false, env);
        recyclerView.setAdapter(mTimeLineAdapter);
        createModelAddress(consignment);

        date.setVisibility(View.GONE);
        datetxt.setVisibility(View.GONE);

//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
//                relativelayout.setRotationY(180);
//            }
//        }
    }

    private void createModelAddress(@Nullable Consignment respose) {
        if (respose != null) {
            addressModels = new ArrayList<>();
            addressModels.add(new AddressModel(env.getAppShipmentDetailsFrom(), respose.getConsignorAddress()));
            addressModels.add(new AddressModel(env.getAppShipmentDetailsTo(), respose.getConsigneeAddress()));
            ((TimeLineAddressAdapter) recyclerView.getAdapter()).setList(addressModels);
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
    }


    private void listeners() {
    }

    private void setStrings() {
        barcode.setText(consignment.getConsignmentId() + "");
        webshoptxt.setText(env.getAppOrderStatusWebshop());
        statustxt.setText(env.getAppOrderStatusStatus());
        amounttxt.setText(env.getAppShipmentDetailsAmount());

        if (consignment.getExpectedDeliveryDate() != null) {
            date.setText(consignment.getExpectedDeliveryDate() == null ? "YYYY-MM-DD" : consignment.getExpectedDeliveryDate());
        } else {
            date.setText("YYYY-MM-DD");
        }
        webshop.setText(consignment.getWebshopName() == null ? "" : consignment.getWebshopName());

       // status.setText(consignment.getConsignmentStatus());
        if(Utils.getCurrentLanguage().equalsIgnoreCase("ar")){
            status.setText(consignment.getConsignmentActiivityAr());
        }else {
            status.setText(consignment.getConsignmentActiivity());
        }
        //consignment.getReturnPickupDate() != null
        if (consignment.getReturnPickupDate() != null) {

            date.setText(consignment.getReturnPickupDate() == null ? "YYYY-MM-DD" : consignment.getReturnPickupDate());
        } else {
            date.setText(consignment.getExpectedDeliveryDate() == null ? "YYYY-MM-DD" : consignment.getExpectedDeliveryDate());
        }

        if(consignment.getType().equals("pickup")){
            datetxt.setText(env.getApppickupdate());
        }else{
            datetxt.setText(env. getAppShipmentDetailsDeliveryDate());
        }

        if(consignment.getPaymentType().equalsIgnoreCase("pre paid")){
            amount.setText(env.getAppprepaid() + "");
            paymentType.setText(env.getAppprepaid());
//            paymentType.setVisibility(View.VISIBLE);
        }else{
            amount.setText("SAR " + consignment.getAmount());
            paymentType.setText(consignment.getPaymentType());

                if (consignment.getIsPaid() == 1) {
                    paymentType.setText(env.getApppaid());
                } else if (consignment.getPaymentType().equalsIgnoreCase("pre paid")) {
                    paymentType.setText(env.getAppprepaid());
                } else {
                    paymentType.setText(consignment.getPaymentType());
                }
        }





//        if(consignment.getPaymentType().equalsIgnoreCase("pre paid")){
//            amount.setText(env.getAppprepaid());
//           // paymentType.setVisibility(View.GONE);
//        }else{
//            amount.setText("SAR " + consignment.getAmount());
//            paymentType.setText(consignment.getPaymentType());
//        }

//        if (consignment.getIsPaid() == 1) {
//            paymentType.setText("Paid");
//        }


//        Configuration config = getActivity().getResources().getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//                //in Right To Left layout
////                selectModeRv.setRotationY(180);
//                relativelayout.setRotationY(180);
//            }
//        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
