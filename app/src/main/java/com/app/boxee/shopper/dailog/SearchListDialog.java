package com.app.boxee.shopper.dailog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.app.boxee.shopper.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class SearchListDialog extends DialogFragment implements TextWatcher {

    private List<String> list;
    private List<String> filteredList;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private EditText etSearch;
    private boolean animate = true;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        View view = View.inflate(getContext(), R.layout.search_list_dialog, null);

        listView = (ListView) view.findViewById(R.id.lv_search_list_dialog);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        etSearch = (EditText) view.findViewById(R.id.et_search_list_dialog_search);
        etSearch.addTextChangedListener(this);
//        if (Locale.getDefault().getLanguage().equals("ar")) {
//            etSearch.setTypeface(((MainActivity) getActivity()).vrFont);
//        }else {
//            etSearch.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/centurygothic.ttf"));
//        }

        dialog.setView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (animate) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);
        dialog.getWindow().setLayout(getActivity().getResources().getDimensionPixelSize(R.dimen.dialog_width),
                getResources().getDimensionPixelSize(R.dimen.dialog_height));

        return dialog;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void animate(boolean animate) {
        this.animate = animate;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            updateFilteredList();
        } else {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        etSearch.removeTextChangedListener(this);
    }

    private void updateFilteredList() {
        filteredList = new LinkedList<>();
        for (String str : list) {
            if (str.toLowerCase().contains(etSearch.getText().toString().toLowerCase().trim())) {
                filteredList.add(str);
            }
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter);
    }


}
