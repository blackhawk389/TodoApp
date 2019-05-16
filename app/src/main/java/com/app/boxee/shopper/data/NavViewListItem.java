package com.app.boxee.shopper.data;

import android.support.annotation.NonNull;


public class NavViewListItem {

    @NonNull
    public final String icon;
    @NonNull
    public final String title;

    private boolean selected;

    public NavViewListItem(@NonNull String icon, @NonNull String title) {
        this.icon = icon;
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
