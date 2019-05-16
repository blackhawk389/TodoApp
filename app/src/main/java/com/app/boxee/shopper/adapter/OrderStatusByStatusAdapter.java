package com.app.boxee.shopper.adapter;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.AppLocationsOption;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.fragments.OrderStatusByStatusFragment;
import com.app.boxee.shopper.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import view_utils.CircularProgressBar;


/**
 * Created by Shafaq on 6/19/2018.
 */

public class OrderStatusByStatusAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final OrderStatusByStatusFragment fragment;
    private ArrayList<OrderDetailsByStatusRespose> list;
    private final RecyclerView recyclerview;
    private final Integer TRUEDATA = 0;
    private final Integer FALSEDATA = 1;
    private SelectModel status;
    private Env env;
    private final List<AppLocationsOption> optionIncomingStrings;
    private final List<AppLocationsOption> optionOutgoingStrings;

    public OrderStatusByStatusAdapter(OrderStatusByStatusFragment selectModeFragment, ArrayList<OrderDetailsByStatusRespose> list, RecyclerView selectModeRv, Env env) {
        this.fragment = selectModeFragment;
        this.list = list;
        this.recyclerview = selectModeRv;
        this.env = env;
        this.optionIncomingStrings = env.getAppHomeOptionsIncoming();
        this.optionOutgoingStrings = env.getAppHomeOptionsOutgoing();
    }

    public void setList(ArrayList<OrderDetailsByStatusRespose> list, SelectModel status) {
        this.list = list;
        this.status = status;
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
//        if (list.get(position).isFound()) {
        return TRUEDATA;
//        } else {
//            return FALSEDATA;
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRUEDATA) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_status_by_status_item, parent, false);

            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_status_empty_item, parent, false);

            return new ViewHolderEmpty(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            ViewHolder dataHolder = (ViewHolder) holder;
            dataHolder.statustxt.setText(env.getAppOrderStatusStatus());
            dataHolder.webshoptxt.setText(env.getAppOrderStatusWebshop());
            dataHolder.barcode.setText(list.get(position).getConsignmentId() + "");
            dataHolder.webshop.setText(list.get(position).getWebshopName());

            if(status.getMode().equalsIgnoreCase("outgoing")){
                dataHolder.deliveryDatetxt.setText(env.getApppickupdate());
            }else{
                dataHolder.deliveryDatetxt.setText(env.getAppOrderStatusDeliveryDate());
            }
           // dataHolder.status.setText(list.get(position).getStatus() + "");
            //if(list.get(position).getActivity().equalsIgnoreCase("")){
            if(!Utils.getCurrentLanguage().equalsIgnoreCase("ar")) {
                dataHolder.status.setText(list.get(position).getActivity() + "");
            }else{
                dataHolder.status.setText(list.get(position).getActivityAr() + "");
            }
//            }else{
//
//            }



            if(status.getMode().equalsIgnoreCase("outgoing") && list.get(position).getReturnPickupDate() != null){
                dataHolder.deliveryDatetxt.setText(env.getApppickupdate());
                dataHolder.deliveryDate.setText(list.get(position).getReturnPickupDate() + "");
            } else if(status.getMode().equalsIgnoreCase("incoming") && list.get(position).getDeliveryDate() != null){
                dataHolder.deliveryDatetxt.setText(env.getAppOrderStatusDeliveryDate());
                dataHolder.deliveryDate.setText(list.get(position).getDeliveryDate() + "");
//                if(list.get(position).getIsPaid() == 1){
//                    dataHolder.prepaid.setText("Paid");
//                }else if (list.get(position).getBillingType().equalsIgnoreCase("pre paid")){
//                    dataHolder.prepaid.setText("Pre Paid");
//                }else{
//                    dataHolder.prepaid.setText("SAR " + list.get(position).getAmount());
//                }
            }else {
                dataHolder.deliveryDate.setText("YYYY-MM-DD");
            }

            if(status.getStatus().equalsIgnoreCase(env.getAppReturnHistory())){
                dataHolder.expectedtxt.setVisibility(View.VISIBLE);
                dataHolder.expectedvalue.setVisibility(View.VISIBLE);
                dataHolder.expectedtxt2.setVisibility(View.GONE);
                dataHolder.expectedvalue2.setVisibility(View.GONE);

                //del
                dataHolder.expectedtxt.setText(env.getAppOrderStatusDeliveryDate());
                //pickup
                dataHolder.deliveryDatetxt.setText(env.getApppickupdate());

              //  list.get(position).getActivity().equalsIgnoreCase("delivered")

//                ?
//                list.get(position).getReturnPickupDate() : list.get(position).getPopAt().split(" ")[0]
                if(list.get(position).getStatus().equalsIgnoreCase("returned final")){
                    //del
                    dataHolder.expectedvalue.setText(list.get(position).getReturnPickupDate());
                    //del
                    dataHolder.deliveryDate.setText( list.get(position).getPopAt() == null ? "--" : list.get(position).getPopAt().split(" ")[0]);

                }else if(list.get(position).getStatus().equalsIgnoreCase("delivered")){
                    dataHolder.expectedvalue.setText(list.get(position).getPodAt().split(" ")[0]);
                    //del
                    dataHolder.deliveryDate.setText(list.get(position).getPopAt() == null ? "--" : list.get(position).getPopAt().split(" ")[0]);
                }




//                dataHolder.expectedtxt2.setText("Return Date");
//                dataHolder.expectedvalue2.setText(list.get(position).getReturnPickupDate() + "");
            }

            if(status.getStatus().equalsIgnoreCase("returned to shipper") || status.getStatus().equalsIgnoreCase("تم ارجاع الشحنة إلى المرسل")){
                dataHolder.deliveryDatetxt.setText(env.getApporderstatus());
                dataHolder.deliveryDate.setText(list.get(position).getReturnPickupDate() + "");
            }
//            if (list.get(position).getReturnPickupDate() != null) {
////                String[] date = list.get(position).getDeliveryDate().split(" ");
//
//            } else {
//                dataHolder.deliveryDate.setText("YYYY-MM-DD");
//
//            }



//            if (list.get(position).getBillingType().equalsIgnoreCase("pre paid")) {
            dataHolder.amountLayout.setVisibility(View.GONE);
            dataHolder.prepaid.setVisibility(View.VISIBLE);


//            if(list.get(position).getIsPaid() == 1){
//                dataHolder.prepaid.setText("Paid");
//            }else if (list.get(position).getBillingType().equalsIgnoreCase("pre paid")){
//                dataHolder.prepaid.setText("Pre Paid");
//            }else{
//                dataHolder.prepaid.setText("SAR " + list.get(position).getAmount());
//            }

            if(list.get(position).getBillingType().equalsIgnoreCase("pre paid")){
              //  dataHolder.prepaid.setText(list.get(position).getBillingType() + "");
                //dataHolder.prepaid.setText(env.getAppprepaid());
                dataHolder.prepaid.setText(env.getAppprepaid());
            }else {
                dataHolder.prepaid.setText("SAR " + list.get(position).getAmount());
            }

            if(status.getMode().equalsIgnoreCase("incoming")) {
                if (list.get(position).getIsPaid() == 1) {
                    dataHolder.prepaid.setText(env.getApppaid());
                }else if(list.get(position).getBillingType().equalsIgnoreCase("pre paid")){
                    dataHolder.prepaid.setText(env.getAppprepaid());
                }else{
                    dataHolder.prepaid.setText("SAR " + list.get(position).getAmount());
                }
            }

//            if(list.get(position).getIsPaid() == 1){
//                dataHolder.prepaid.setText("Paid");
//            }else if (list.get(position).getBillingType().equalsIgnoreCase("pre paid")){
//                dataHolder.prepaid.setText("Pre Paid");
//            }else{
//                dataHolder.prepaid.setText("SAR " + list.get(position).getAmount());
//            }

//            } else {
//                dataHolder.prepaid.setVisibility(View.GONE);
//                dataHolder.amountLayout.setVisibility(View.VISIBLE);
//                dataHolder.currency.setText("SAR ");
//                dataHolder.amount.setText(list.get(position).getAmount() + "");

//            }
//        }
//        } else {
//            ViewHolderEmpty dataHolder = (ViewHolderEmpty) holder;
//            dataHolder.barcode.setText(list.get(position).getc() + "");
//
//
            int animationDuration = 2000; // 2500ms = 2,5s
            if (status.getStatus().equalsIgnoreCase(optionIncomingStrings.get(0).getName()) || status.getStatus().equalsIgnoreCase(optionOutgoingStrings.get(0).getName())) {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(25, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.aqua));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.aquaOpacity));
            } else if (status.getStatus().equalsIgnoreCase(optionIncomingStrings.get(1).getName()) || status.getStatus().equalsIgnoreCase(optionOutgoingStrings.get(1).getName())) {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(50, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.mustard));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.mustardOpacity));
            } else if (status.getStatus().equalsIgnoreCase(optionIncomingStrings.get(2).getName()) || status.getStatus().equalsIgnoreCase(optionOutgoingStrings.get(2).getName())) {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(75, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimaryDark));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimaryDarkOpacity));
            } else if (status.getStatus().equalsIgnoreCase(optionIncomingStrings.get(3).getName()) || status.getStatus().equalsIgnoreCase(optionOutgoingStrings.get(3).getName())) {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.prepaid));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.prepaidOpacity));
            }else if (status.getStatus().equalsIgnoreCase(optionIncomingStrings.get(4).getName()) || status.getStatus().equalsIgnoreCase(optionOutgoingStrings.get(4).getName())) {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_red));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_redOpacity));
            } else {
                ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
                ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_red));
                ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_redOpacity));
            }
            setFonts(dataHolder);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (list.get(position).)
                Utils.replaceFragment(fragment.getFragmentManager(), OrderDetailsFragment.newInstance(null, list.get(position), status,null), R.id.fragment_container, true, true);
//                else
//                    Toast.makeText(fragment.getActivity(), "No Shipment were found against this Order Number!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFonts(ViewHolder dataHolder) {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(fragment.getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(fragment.getActivity().getAssets(), "font/ge_ss_text_bold.otf");
//            dataHolder.barcode.setTypeface(bold);
            dataHolder.prepaid.setTypeface(bold);
//            dataHolder.deliveryDate.setTypeface(regular);
            dataHolder.status.setTypeface(regular);
            dataHolder.webshop.setTypeface(regular);
//            dataHolder.amount.setTypeface(bold);
            dataHolder.deliveryDatetxt.setTypeface(regular);
            dataHolder.statustxt.setTypeface(regular);
            dataHolder.webshoptxt.setTypeface(regular);

        }
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        RecyclerView.ViewHolder holder = recyclerview.findContainingViewHolder(view);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView deliveryDate;
        public final TextView status;
        public final TextView webshop;
        public final TextView amount;
        public final TextView currency;
        public final TextView prepaid;
        public final TextView barcode;
        private final LinearLayout amountLayout;
        public final CircularProgressBar circularProgressBar;
        private final TextView deliveryDatetxt;
        private final TextView statustxt;
        private final TextView webshoptxt;
        private final TextView expectedtxt;
        private final TextView expectedvalue;
        private final TextView expectedtxt2;
        private final TextView expectedvalue2;


        public ViewHolder(View view) {
            super(view);
            circularProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_icon);
            deliveryDate = view.findViewById(R.id.expected_date_value);
            status = view.findViewById(R.id.department_status_value);
            webshop = view.findViewById(R.id.amount_value);
            deliveryDatetxt = view.findViewById(R.id.expected_date_txt);
            statustxt = view.findViewById(R.id.deparment_Status_txt);
            webshoptxt = view.findViewById(R.id.amount_txt);
            amount = view.findViewById(R.id.amount);
            currency = view.findViewById(R.id.currency);
            prepaid = view.findViewById(R.id.prepaid);
            barcode = view.findViewById(R.id.barcode);
            amountLayout = view.findViewById(R.id.amount_layout);
            expectedtxt = view.findViewById(R.id.expected_date_txt1);
            expectedvalue = view.findViewById(R.id.expected_date_value1);
            expectedtxt2 = view.findViewById(R.id.expected_date_txt2);
            expectedvalue2 = view.findViewById(R.id.expected_date_value2);
        }
    }

    private class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public final TextView barcode;
        public final TextView detail;


        public ViewHolderEmpty(View view) {
            super(view);
            barcode = view.findViewById(R.id.barcode);
            detail = view.findViewById(R.id.detail_txt);
        }
    }
}
