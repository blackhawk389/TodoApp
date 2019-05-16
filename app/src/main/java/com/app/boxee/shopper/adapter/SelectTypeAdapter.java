package com.app.boxee.shopper.adapter;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.app.boxee.shopper.data.AppLocationsOption;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.SelectModel;
import com.app.boxee.shopper.fragments.OrderStatusByStatusFragment;
import com.app.boxee.shopper.fragments.OrderStatusFragment;
import com.app.boxee.shopper.fragments.SelectTypeFragment;
import com.app.boxee.shopper.R;
import com.app.boxee.shopper.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import view_utils.CircularProgressBar;


/**
 * Created by Shafaq on 6/19/2018.
 */

public class SelectTypeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final SelectTypeFragment fragment;
    private final Env env;
    private final List<AppLocationsOption> optionIncomingStrings;
    private final List<AppLocationsOption> optionOutgoingStrings;
    private ArrayList<SelectModel> list;
    private final RecyclerView recyclerview;

    public SelectTypeAdapter(SelectTypeFragment selectModeFragment, ArrayList<SelectModel> optionIncoming, RecyclerView selectModeRv, Env env) {
        this.fragment = selectModeFragment;
        this.list = optionIncoming;
        this.recyclerview = selectModeRv;
        this.env = env;
        this.optionIncomingStrings = env.getAppHomeOptionsIncoming();
        this.optionOutgoingStrings = env.getAppHomeOptionsOutgoing();
    }

    public void setList(ArrayList<SelectModel> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_mode_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        holder.circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
//        holder.circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundProgressBarColor));
//        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
//        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2000; // 2500ms = 2,5s
        ((ViewHolder) holder).itemView.setOnClickListener(this);
        ((ViewHolder) holder).status.setText(list.get(position).getStatus());
        ((ViewHolder) holder).count.setText(Integer.parseInt(list.get(position).getCount()) < 10 ? "0" + list.get(position).getCount() : list.get(position).getCount());
        if (list.get(position).getStatus().equalsIgnoreCase(optionIncomingStrings.get(0).getName()) || list.get(position).getStatus().equalsIgnoreCase(optionOutgoingStrings.get(0).getName())) {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(25, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.aqua));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.aquaOpacity));
        } else if (list.get(position).getStatus().equalsIgnoreCase(optionIncomingStrings.get(1).getName()) || list.get(position).getStatus().equalsIgnoreCase(optionOutgoingStrings.get(1).getName())) {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(50, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.mustard));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.mustardOpacity));
        } else if (list.get(position).getStatus().equalsIgnoreCase(optionIncomingStrings.get(2).getName()) || list.get(position).getStatus().equalsIgnoreCase(optionOutgoingStrings.get(2).getName())) {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(75, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimaryDark));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.colorPrimaryDark));
        } else if (list.get(position).getStatus().equalsIgnoreCase(optionIncomingStrings.get(3).getName()) || list.get(position).getStatus().equalsIgnoreCase(optionOutgoingStrings.get(3).getName())) {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.prepaid));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.prepaidOpacity));
        }
        else if (list.get(position).getStatus().equalsIgnoreCase(optionIncomingStrings.get(4).getName()) || list.get(position).getStatus().equalsIgnoreCase(optionOutgoingStrings.get(4).getName())) {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_red));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_redOpacity));
        } else {
            ((ViewHolder) holder).circularProgressBar.setProgressWithAnimation(100, animationDuration);
            ((ViewHolder) holder).circularProgressBar.setColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_red));
            ((ViewHolder) holder).circularProgressBar.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.dark_redOpacity));
        }
        holder.itemView.setOnClickListener(this::onClick);
        setFonts( ((ViewHolder) holder));
    }

    private void setFonts(ViewHolder holder) {
        if(Utils.getCurrentLanguage().equals("ar")){
            Typeface regular = Typeface.createFromAsset(fragment.getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            holder.status.setTypeface(regular);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        RecyclerView.ViewHolder holder = recyclerview.findContainingViewHolder(view);
        if (!list.get(holder.getAdapterPosition()).getCount().equalsIgnoreCase("0")) {
            Utils.replaceFragment(fragment.getParentFragment().getFragmentManager(), OrderStatusByStatusFragment.newInstance(list.get(holder.getAdapterPosition())), R.id.fragment_container, true, true);
        } else {
            Toast.makeText(fragment.getActivity(), env.getAppeemailnnoshipment(), Toast.LENGTH_SHORT).show();
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {


        private final CircularProgressBar circularProgressBar;
        private final TextView status;
        private final TextView count;

        public ViewHolder(View view) {
            super(view);
            circularProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_icon);
            status = (TextView) view.findViewById(R.id.barcode);
            count = (TextView) view.findViewById(R.id.count);

        }
    }
}
