package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.app.boxee.shopper.R;

import java.util.ArrayList;

public class CustomAutoCompleteAdapter extends ArrayAdapter<String> {
    ArrayList<String> items,temp, suggestions;

    public CustomAutoCompleteAdapter(Context context, ArrayList<String> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_autocomplete_item, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.autoCompleteItem);
        if (txtCustomer != null)
            txtCustomer.setText(items.get(position));


        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String customer = (String) resultValue;
            return customer;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String people : temp) {
                    if (people.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> c = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}