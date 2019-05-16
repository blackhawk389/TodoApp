package com.app.boxee.shopper.data;

import java.util.List;

/**
 * Created by Shafaq on 3/29/2018.
 */

public class NavigationModel {

    public static final int HEADER_TYPE=0;
    public static final int LIST_TYPE=1;
    public static final int OTHER_OPTION=2;
    public static final int TEXT_TYPE=3;
    public static final int HEADER_TYPE_LOGIN =4;
    public static final int SIGN_OUT =5;

    public  List<NavViewListItem> list;

    public int type;




    public NavigationModel(int type, List<NavViewListItem> list)
    {
        this.type=type;
        this.list = list;

    }
}
