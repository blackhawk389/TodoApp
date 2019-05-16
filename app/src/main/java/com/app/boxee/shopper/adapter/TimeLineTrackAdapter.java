package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.data.Orientation;
import com.app.boxee.shopper.data.TimeLineModel;
import com.app.boxee.shopper.data.response.ShipmentActivitiesTimeline;
import com.app.boxee.shopper.utils.Utils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

public class TimeLineTrackAdapter extends RecyclerView.Adapter<TimeLineTrackAdapter.TimeLineViewHolder> {

    private List<ShipmentActivitiesTimeline> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineTrackAdapter(List<ShipmentActivitiesTimeline> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
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
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline, parent, false);
        }

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        ShipmentActivitiesTimeline timeLineModel = mFeedList.get(position);
//
//        if (timeLineModel.getStatus() == OrderStatus.INACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
//        } else if (timeLineModel.getStatus() == OrderStatus.ACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
//        } else {

        holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.prepaid));
//        }
        if (timeLineModel.getUpdatedAt()!= null && !timeLineModel.getUpdatedAt().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            //holder.mTime.setVisibility(View.VISIBLE);

//            holder.mDate.setText("8/10/2018 11:18:20");
            String date = DateTimeUtils.parseDateTime(timeLineModel.getUpdatedAt(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM yyyy HH:mm");
            String[] dataTime = date.split(" ");
            holder.mDate.setText(dataTime[2] +  " " + dataTime[1] + " " + dataTime[0]);
            //holder.mTime.setText(dataTime[1]);
        } else {
            holder.mDate.setVisibility(View.GONE);
            holder.mTime.setVisibility(View.GONE);
        }

       // holder.mMessage.setText(timeLineModel.getDetail());
//        holder.muser.setVisibility(View.GONE);
        if(!Utils.getCurrentLanguage().equalsIgnoreCase("ar")) {
            holder.muser.setText(timeLineModel.getExternalDetail());
        }else{
            holder.muser.setText(timeLineModel.getExternalDetailAr());
        }
       // holder.mlocation.setText(timeLineModel.getCityName()+","+timeLineModel.getCountryName());
    }

    @Override
    public int getItemCount() {
        return mFeedList == null ? 0 : mFeedList.size();
    }

    public void setList(ArrayList<ShipmentActivitiesTimeline> mDataList) {
        this.mFeedList = mDataList;
        notifyDataSetChanged();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        private final TextView mDate;
        private final TextView mTime;
        private final TextView mMessage;
        private final TextView mlocation;
        private final TextView muser;


        TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.time_marker);
            mDate = itemView.findViewById(R.id.date);
            mTime = itemView.findViewById(R.id.time);
            mMessage = itemView.findViewById(R.id.status);
            mlocation = itemView.findViewById(R.id.locations);
            muser = itemView.findViewById(R.id.user);
            mTimelineView.initLine(viewType);
        }
    }
}