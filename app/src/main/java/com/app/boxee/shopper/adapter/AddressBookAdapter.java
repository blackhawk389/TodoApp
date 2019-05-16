package com.app.boxee.shopper.adapter;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;

import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.response.myAdresses.ShopperAddressItem;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.fragments.AddAddressFragment;
import com.app.boxee.shopper.fragments.DashboardFragment;
import com.app.boxee.shopper.fragments.MapPlacePickerFragment;
import com.app.boxee.shopper.fragments.MyAddressesFragment;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.LocationViewModel;


import java.util.List;
import java.util.Locale;

/**
 * Created by Shafaq on 4/12/2018.
 */

public class AddressBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private final RecyclerView recyclerView;
    private final Env env;
    private final TinyDB tinydb;
    private List<ShopperAddressItem> list;
    private Fragment fragment;
    private AlertDialog alertDialog;
    Context context;
    private LocationViewModel viewModel;
    private MetadataData matadata;
    //private String cityName;


    public AddressBookAdapter(Fragment addressBookFragment, RecyclerView addressListrv, Env env, TinyDB tinyDB, List<ShopperAddressItem> addressList, MetadataData matadata) {
        this.fragment = addressBookFragment;
        this.recyclerView = addressListrv;
        this.env = env;
        this.tinydb = tinyDB;
        this.matadata = matadata;
        this.list = addressList;

    }

    public void setList(List<ShopperAddressItem> list) {
        this.list = list;
        for (ShopperAddressItem item:list) {
            for(int i = 0; i< matadata.getLocations().getEn().size(); i++){
                if(matadata.getLocations().getEn().get(i).getLocationId().equals(list.get(list.indexOf(item)).getCityId())){
                    item.setCityName( matadata.getLocations().getEn().get(i).getName());
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(list == null ||list.isEmpty()) {
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_address, parent, false);
            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.address_book_item, parent, false);
            return new AddressBookAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {
        if(holder1 instanceof  EmptyViewHolder){
            ((EmptyViewHolder) holder1).address.setText(env.getAppmyaddresstitle());
        }else {
            ((ViewHolder) holder1).defaultCheck.setText(env.getApppinlocationmakedefault());
            ((ViewHolder) holder1).defaulttxt.setText(env.getAppdefaultaddrss());
            if (list.get(position).getIsDefault() == 1) {
                ((ViewHolder) holder1).defaulttxt.setVisibility(View.VISIBLE);
                ((ViewHolder) holder1).defaultCheck.setVisibility(View.GONE);

            } else {
                ((ViewHolder) holder1).defaulttxt.setVisibility(View.GONE);
                ((ViewHolder) holder1).defaultCheck.setVisibility(View.VISIBLE);
            }
            ShopperAddressItem item = list.get(position);
            ((ViewHolder) holder1).name.setText(item.getTag());
            ((ViewHolder) holder1).address.setText(item.getAddressHierarchy() + ", "+ item.getCityName());
            ((ViewHolder) holder1).phone.setText(list.get(position).getPhone() == null ? "" : list.get(position).getPhone());

            ((ViewHolder) holder1).defaultCheck.setOnClickListener(v -> {
                ((MyAddressesFragment) fragment).makedefault(list.get(position).getAddressId() + "");
            });
            ((ViewHolder) holder1).btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.replaceFragment(fragment.getFragmentManager(), AddAddressFragment.newInstance(list.get(position), false), R.id.fragment_container, true, true);

                }
            });
            ((ViewHolder) holder1).btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (list.get(position).getIsDefault() == 1) {
//                    env.getAppAddressDefaultDeleteDialog()
//                    env.getAppAddressDefaultDeleteDialogOk()
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(env.getAppdeletedefaultaddress());
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppOk(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        alertDialog.setCancelable(true);
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                        // Change the alert dialog buttons text and background color
                        positiveButton.setTextColor(Color.parseColor("#0044B8"));

                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(env.getAppaddressdeleteaddress());
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, env.getAppYes(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alertDialog.dismiss();
                                ((MyAddressesFragment) fragment).deleteAddress(list.get(position).getAddressId() + "");
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, env.getAppNo(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        alertDialog.setCancelable(true);
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                        // Change the alert dialog buttons text and background color

                        positiveButton.setTextColor(Color.parseColor("#0044B8"));
                        Button negButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                        // Change the alert dialog buttons text and background color
                        negButton.setTextColor(Color.parseColor("#0044B8"));
                    }

//                Utils.replaceFragment(fragment.getFragmentManager(), AddAddressFragment.newInstance(true), R.id.fragment_container, true, true);

                }
            });
            ((ViewHolder) holder1).btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MyAddressesFragment) fragment).location();
//                Utils.replaceFragment(fragment.getFragmentManager(), MapPlacePickerFragment.newInstance(null), R.id.fragment_container, true, true);

                }
            });
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//            Utils.replaceFragment(fragment.getFragmentManager(), AddAddressFragment.newInstance(true), R.id.fragment_container, true, true);
                    int backStackCount = fragment.getFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 1) {
//incoming or tracking or outgoing
                        ((MyAddressesFragment) fragment).updateLocation(list.get(position));
//                    Toast.makeText(fragment.getActivity(),"cdc",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null||list.isEmpty() ? 1 : list.size();
    }


    @Override
    public void onClick(View view) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView btnDelete;
        private final ImageView btnEdit;
        private final TextView name;
        private final TextView address;

        private final TextView phone;
        private final TextView defaulttxt;
        private final Button defaultCheck;
        private final ImageView btnLocation;


        public ViewHolder(View view) {
            super(view);
            btnDelete = (ImageView) view.findViewById(R.id.delete_btn);
            btnLocation = (ImageView) view.findViewById(R.id.location_btn);
            btnEdit = (ImageView) view.findViewById(R.id.edit_btn);
            name = (TextView) view.findViewById(R.id.heading);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            defaulttxt = (TextView) view.findViewById(R.id.ship_add);
            defaultCheck = (Button) view.findViewById(R.id.makeDefault);
            if (Locale.getDefault().getLanguage().equals("ar")) {
//                defaulttxt.setTypeface(((MainActivity) fragment.getActivity()).vrFont);
//                defaultCheck.setTypeface(((MainActivity) fragment.getActivity()).vrFont);
            }
        }

    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        private final TextView address;

        public EmptyViewHolder(View view) {
            super(view);
            address = (TextView) view.findViewById(R.id.detail_txt);
            if (Locale.getDefault().getLanguage().equals("ar")) {
//                defaulttxt.setTypeface(((MainActivity) fragment.getActivity()).vrFont);
//                defaultCheck.setTypeface(((MainActivity) fragment.getActivity()).vrFont);
            }
        }


    }
}