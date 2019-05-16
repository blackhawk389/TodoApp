package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.AddressModel;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.utils.Utils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

import view_utils.VectorDrawableUtils;

public class TimeLineAddressAdapter extends RecyclerView.Adapter<TimeLineAddressAdapter.TimeLineViewHolder> {

    private List<AddressModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private Env env;
    Typeface regular;
    Typeface bold;

    public TimeLineAddressAdapter(List<AddressModel> feedList, Orientation orientation, boolean withLinePadding, Env env) {
        mFeedList = feedList;
        mOrientation = orientation;
        this.env = env;
        mWithLinePadding = withLinePadding;
        setFonts();

    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
//            regular = Typeface.createFromAsset(mContext.getAssets(), "font/ge_ss_text_regular.otf");
//             bold = Typeface.createFromAsset(mContext.getAssets(), "font/ge_ss_text_bold.otf");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        if (mOrientation == Orientation.HORIZONTAL) {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_horizontal_line_padding : R.layout.item_timeline_horizontal, parent, false);
        } else {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline_address, parent, false);
        }

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        AddressModel timeLineModel = mFeedList.get(position);
//
        if (timeLineModel.getType().equalsIgnoreCase(env.getAppShipmentDetailsTo())) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.triangle_shape), ContextCompat.getColor(mContext, R.color.prepaid));
//        } else if (timeLineModel.getStatus() == OrderStatus.ACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {

            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.prepaid));
        }
        holder.type.setText(timeLineModel.getType()+"");
        holder.address.setText(timeLineModel.getAddress()+"");
    }

    @Override
    public int getItemCount() {
        return mFeedList==null?0 :2;
    }

    public void setList(ArrayList<AddressModel> mDataList) {
        this.mFeedList = mDataList;
        notifyDataSetChanged();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        private final TextView type;
        private final TextView address;
        TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
            type =itemView.findViewById(R.id.from_txt);
            address = itemView.findViewById(R.id.address_txt);
            address.setTypeface(regular);

        }
    }
}