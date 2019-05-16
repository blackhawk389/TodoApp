package com.app.boxee.shopper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.response.OrderDetailsByStatusRespose;
import com.app.boxee.shopper.fragments.OrderDetailsFragment;
import com.app.boxee.shopper.fragments.OrderHistoryListFragment;
import com.app.boxee.shopper.utils.Utils;

import java.util.ArrayList;


/**
 * Created by Shafaq on 6/19/2018.
 */

public class OrderHistoryListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final OrderHistoryListFragment fragment;
    private ArrayList<OrderDetailsByStatusRespose> list;
    private final RecyclerView recyclerview;
    private final Integer TRUEDATA = 0;
    private final Integer FALSEDATA = 1;


    public OrderHistoryListAdapter(OrderHistoryListFragment selectModeFragment, ArrayList<OrderDetailsByStatusRespose> list, RecyclerView selectModeRv) {
        this.fragment = selectModeFragment;
        this.list = list;
        this.recyclerview = selectModeRv;
    }

    public void setList(ArrayList<OrderDetailsByStatusRespose> list) {
        this.list = list;
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
            dataHolder.barcode.setText(list.get(position).getConsignmentId() + "");
            dataHolder.webshop.setText(list.get(position).getWebshopName());
            dataHolder.status.setText(list.get(position).getAmount()+"");
            if (list.get(position).getDeliveryDate() != null) {
//                String[] date = list.get(position).getDeliveryDate().split(" ");
                dataHolder.deliveryDate.setText(list.get(position).getDeliveryDate() + "");
            } else {
//                dataHolder.deliveryDate.setText("");

            }
//            if (list.get(position).getBillingType().equalsIgnoreCase("pre paid")) {
                dataHolder.amountLayout.setVisibility(View.GONE);
                dataHolder.prepaid.setVisibility(View.VISIBLE);
                dataHolder.prepaid.setText(list.get(position).getBillingType());

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
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (list.get(position).)
                    Utils.replaceFragment(fragment.getParentFragment().getFragmentManager(), OrderDetailsFragment.newInstance(null,list.get(position), null,null), R.id.fragment_container, true, true);
//                else
//                    Toast.makeText(fragment.getActivity(), "No Shipment were found against this Order Number!!",Toast.LENGTH_SHORT).show();
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
        public final TextView webshop;
        public final TextView amount;
        public final TextView currency;
        public final TextView prepaid;
        public final TextView barcode;
        private final LinearLayout amountLayout;


        public ViewHolder(View view) {
            super(view);
            deliveryDate = view.findViewById(R.id.expected_date_value);
            status = view.findViewById(R.id.department_status_value);
            webshop = view.findViewById(R.id.amount_value);
            amount = view.findViewById(R.id.amount);
            currency = view.findViewById(R.id.currency);
            prepaid = view.findViewById(R.id.prepaid);
            barcode = view.findViewById(R.id.barcode);
            amountLayout = view.findViewById(R.id.amount_layout);
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
