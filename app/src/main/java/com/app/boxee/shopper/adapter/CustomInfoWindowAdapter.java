package com.app.boxee.shopper.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.map_info_window,
                null);

        TextView tvBranch = (TextView) v.findViewById(R.id.tv_info_window_branch);
        TextView tvAddress = (TextView) v.findViewById(R.id.tv_info_window_address);
        TextView tvPhone = (TextView) v.findViewById(R.id.tv_info_window_phone);
        Button btn_directions = (Button) v.findViewById(R.id.btn_directions);

        try {
            tvBranch.setText(marker.getTitle());
            tvAddress.setText(marker.getSnippet().split(";")[0]);
            tvPhone.setText(String.valueOf(marker.getSnippet().split(";")[1]));
            tvPhone.setVisibility(View.GONE);
            btn_directions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_directions, 0, 0, 0);

//            btn_directions.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Uri gmmIntentUri = Uri.parse("geo:"+marker.getPosition().latitude+","+marker.getPosition().longitude);
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//                        context.startActivity(mapIntent);
//                    }
//                }
//            });
//            OnInfoWindowElemTouchListener infoButtonListener = new OnInfoWindowElemTouchListener(btn_directions, context.getResources().getDrawable(R.drawable.button_rounded), context.getResources().getDrawable(R.drawable.button_rounded)) {
//                @Override
//                protected void onClickConfirmed(View v, Marker marker) {
//                    // Here we can perform some action triggered after clicking the button
//                    Uri gmmIntentUri = Uri.parse("geo:"+marker.getPosition().latitude+","+marker.getPosition().longitude);
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//                        context.startActivity(mapIntent);
//                    }                }
//            };
//           btn_directions.setOnTouchListener(infoButtonListener);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }
}
