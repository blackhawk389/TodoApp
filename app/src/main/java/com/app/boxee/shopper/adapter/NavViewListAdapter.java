package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.NavViewListItem;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Vals;

import java.util.List;


public class NavViewListAdapter extends ArrayAdapter<NavViewListItem> {


    public NavViewListAdapter(Context context, int resource, List<NavViewListItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavViewListItem item = getItem(position);

        if (convertView == null) {
//            if (item.isSelected()) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.nav_view_item_selected, parent, false);
//            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.nav_view_item, parent, false);
//            }
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.iv_nav_view_item_icon);
//        if (item.isSelected()) {
//            icon.setImageResource(getContext().getResources().getIdentifier(item.icon + "_selected",
//                    "drawable", getContext().getPackageName()));
//        } else {
        icon.setImageResource(getContext().getResources().getIdentifier(item.icon,
                "drawable", getContext().getPackageName()));
//        }

        TextView title = (TextView) convertView.findViewById(R.id.tv_nav_view_item_title);
        title.setText(item.title);
        if (TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
            Typeface bold = Typeface.createFromAsset(getContext().getAssets(), "font/ge_ss_text_bold.otf");
            title.setTypeface(bold);
        }

        return convertView;
    }
}
