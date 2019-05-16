package com.app.boxee.shopper.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.data.response.Consignment;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.fragments.OrderStatusFragment;
import com.app.boxee.shopper.utils.Utils;

import java.util.List;


/**
 * Created by Shafaq on 6/19/2018.
 */

public class OrderStatusAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final OrderStatusFragment fragment;
    private List<Consignment> list;
    private final RecyclerView recyclerview;
    private final Integer TRUEDATA = 0;
    private final Integer FALSEDATA = 1;
    Env env;


    public OrderStatusAdapter(OrderStatusFragment selectModeFragment, List<Consignment> list, RecyclerView selectModeRv, Env env) {
        this.fragment = selectModeFragment;
        this.env = env;
        this.list = list;
        this.recyclerview = selectModeRv;
    }

    public void setList(List<Consignment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!list.get(position).isFound()) {
            return TRUEDATA;
        } else {
            return FALSEDATA;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TRUEDATA) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_status_item, parent, false);
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
            dataHolder.barcode.setText(list.get(position).getConsignmentId() + "");
            dataHolder.webshop.setText(list.get(position).getWebshopName());
            if(Utils.getCurrentLanguage().equalsIgnoreCase("ar")){
                dataHolder.status.setText(list.get(position).getConsignmentActiivityAr());
            }else{
                dataHolder.status.setText(list.get(position).getConsignmentActiivity());
            }
           // dataHolder.status.setText(list.get(position).getConsignmentStatus());
//            String[] date = list.get(position).get().split(" ");

            dataHolder.amounttxt.setText(env.getAppOrderStatusWebshop());
            dataHolder.statustxt.setText(env.getAppOrderStatusStatus());
            if (list.get(position).getReturnPickupDate() != null) {
                //dataHolder.deliveryTxt.setText("Pickup Date");
               // dataHolder.deliveryDate.setText(list.get(position).getReturnPickupDate() == null ? "YYYY-MM-DD" : list.get(position).getReturnPickupDate());
            } else {
               // dataHolder.deliveryTxt.setText("Delivery Date");
               // dataHolder.deliveryDate.setText(list.get(position).getExpectedDeliveryDate() == null ? "YYYY-MM-DD" : list.get(position).getExpectedDeliveryDate());
            }

            //change request to remove dates from tracking
            if(list.get(position).getType().equals("pickup")){
//                dataHolder.deliveryDate.setVisibility(View.GONE);
//                dataHolder.deliveryTxt.setVisibility(View.GONE);
                //dataHolder.deliveryTxt.setText(env.getApppickupdate());
            }else{
//                dataHolder.deliveryDate.setVisibility(View.GONE);
//                dataHolder.deliveryTxt.setVisibility(View.GONE);
                //dataHolder.deliveryTxt.setText(env. getAppShipmentDetailsDeliveryDate());
            }

//            if (list.get(position).getExpectedDeliveryDate() != null) {
//
//                dataHolder.deliveryDate.setText(list.get(position).getExpectedDeliveryDate() + "");
//            } else {
//                dataHolder.deliveryDate.setText("YYYY-MM-DD");
//
//            }
//            if (list.get(position).getPaymentType().equalsIgnoreCase("pre paid")) {
//                dataHolder.amountLayout.setVisibility(View.GONE);
//                dataHolder.prepaid.setVisibility(View.VISIBLE);
//
//            } else {
//                dataHolder.prepaid.setVisibility(View.GONE);
//                dataHolder.amountLayout.setVisibility(View.VISIBLE);
//                dataHolder.currency.setText("SAR ");
//                dataHolder.amount.setText(list.get(position).getAmount() + "");
//
//            }


            if(list.get(position).getPaymentType().equalsIgnoreCase("pre paid")){
                dataHolder.amountLayout.setVisibility(View.GONE);
                dataHolder.prepaid.setVisibility(View.VISIBLE);
                dataHolder.amount.setText(env.getAppprepaid());
                //list.get(position).getPaymentType() + ""
                dataHolder.prepaid.setText(env.getAppprepaid());

//            paymentType.setVisibility(View.VISIBLE);
            }else{
                dataHolder.prepaid.setVisibility(View.GONE);
                dataHolder.amount.setText(list.get(position).getAmount()+ "");
                //dataHolder.prepaid.setText(list.get(position).getPaymentType());
            }

            if(list.get(position).getIsPaid() == 1){
                dataHolder.amountLayout.setVisibility(View.GONE);
                dataHolder.prepaid.setVisibility(View.VISIBLE);
                dataHolder.prepaid.setText(env.getApppaid());
            }
        } else {
            ViewHolderEmpty dataHolder = (ViewHolderEmpty) holder;
            dataHolder.barcode.setText(list.get(position).getConsignmentId() + "");
            dataHolder.detail.setText(env.getAppOrderStatusNoShipmentFound());


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!list.get(position).isFound())
                    Utils.replaceFragment(fragment.getFragmentManager(), OrderDetailsFragment.newInstance(list.get(position), null, null,null), R.id.fragment_container, true, true);
                else
                    Toast.makeText(fragment.getActivity(), env.getAppOrderStatusNoShipmentFound(), Toast.LENGTH_SHORT).show();
            }
        });
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
        public final TextView statustxt;
        public final TextView amounttxt;
        public final TextView webshop;
        public final TextView amount;
        public final TextView currency;
        public final TextView prepaid;
        public final TextView barcode;
        private final LinearLayout amountLayout;
        public final TextView deliveryTxt;


        public ViewHolder(View view) {
            super(view);
            deliveryDate = view.findViewById(R.id.expected_date_value);
            status = view.findViewById(R.id.department_status_value);
            statustxt = view.findViewById(R.id.deparment_Status_txt);
            webshop = view.findViewById(R.id.amount_value);
            amount = view.findViewById(R.id.amount);
            amounttxt = view.findViewById(R.id.amount_txt);
            deliveryTxt = view.findViewById(R.id.expected_date_txt);
            currency = view.findViewById(R.id.currency);
            prepaid = view.findViewById(R.id.prepaid);
            barcode = view.findViewById(R.id.barcode);
            amountLayout = view.findViewById(R.id.amount_layout);
            if (Utils.getCurrentLanguage().equals("ar")) {
                Typeface regular = Typeface.createFromAsset(fragment.getActivity().getAssets(), "font/ge_ss_text_regular.otf");
                Typeface bold = Typeface.createFromAsset(fragment.getActivity().getAssets(), "font/ge_ss_text_bold.otf");
//            dataHolder.barcode.setTypeface(bold);
                prepaid.setTypeface(bold);
                deliveryDate.setTypeface(regular);
                status.setTypeface(regular);
                webshop.setTypeface(regular);
//                amount.setTypeface(bold);
                deliveryDate.setTypeface(regular);
                status.setTypeface(regular);
                webshop.setTypeface(regular);

            }
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
