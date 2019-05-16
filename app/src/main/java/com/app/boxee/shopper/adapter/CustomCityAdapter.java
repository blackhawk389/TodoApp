package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.SpinnerModel;

import java.util.List;

public class CustomCityAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<SpinnerModel> items;
    private final int mResource;

    public CustomCityAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }


    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView numOffersTv = (TextView) view.findViewById(R.id.heading);

        SpinnerModel offerData = items.get(position);
//
//        offTypeTv.setText(offerData.getOfferType());
        numOffersTv.setText(offerData.getName());
//        maxDiscTV.setText(offerData.getMaxDicount());

        return view;
    }
}