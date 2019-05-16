package com.app.boxee.shopper.utils;

import com.app.boxee.shopper.BuildConfig;

public class Vals {

    public static final String SOURCE = "android";
    public static final String VERSION_ENV = "env_version";
    public static final String URL_EN = "url_en";
    public static final String URL_AR = "url_ar";
    public static String CURRENT_LANGUAGE = "current_language";
    private static String BASE_URL_RELEASE_LOCATION = "https://devlocate.myboxee.net/";
    private static String BASE_URL_STAGING_LOCATION = "https://devlocate.myboxee.net/";
    private static String BASE_URL_DEV_LOCATION = "https://devlocate.myboxee.net/";

    private static String BASE_URL_RELEASE = "https://api.clexsa.com/";
    private static String BASE_URL_STAGING = "https://stageapi.myboxee.net/";
    private static String BASE_URL_DEV = "https://devapi.myboxee.net/";
    private static String BASE_URL_PAYFORT_TEST = "https://sbpaymentservices.payfort.com/";
    private static String BASE_URL_PAYFORT_PRODUCTION = "https://paymentservices.payfort.com/";
    public static String CUSTOMER_TOKEN = "customer_token";
    public static String GET_BASE_URL() {
        if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            return BASE_URL_DEV;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            return BASE_URL_STAGING;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
            return BASE_URL_RELEASE;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("local")) {
            return BASE_URL_DEV;
        } else {
            return BASE_URL_STAGING;
        }
    }

    public static String GET_BASE_URL_PAYFORT() {
        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
            return BASE_URL_PAYFORT_PRODUCTION;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            return BASE_URL_PAYFORT_TEST;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            return BASE_URL_PAYFORT_TEST;
        } else {
            return BASE_URL_PAYFORT_TEST;
        }
    }

    public static String GET_BASE_URL_LOCATION() {
        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {
            return BASE_URL_RELEASE_LOCATION;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            return BASE_URL_STAGING_LOCATION;
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            return BASE_URL_DEV_LOCATION;
        } else {
            return BASE_URL_DEV_LOCATION;
        }
    }

    //
//    public static String GET_WSS_URL() {
//        if (BuildConfig.FLAVOR.equals("dev")) {
//            return WSS_URL_DEV;
//        } else if (BuildConfig.FLAVOR.equals("staging")) {
//            return WSS_URL_STAGING;
//        } else if (BuildConfig.FLAVOR.equals("live")) {
//            return WSS_URL_RELEASE;
//        } else if (BuildConfig.FLAVOR.equals("local")) {
//            return WSS_URL_LOCAL;
//        } else {
//            return WSS_URL_DEV;
//
//        }
//    }
//
//    public static String GET_CLOUD_URL() {
//        if (BuildConfig.FLAVOR.equals("dev")) {
//            return BASE_URL_CLOUD_DEV;
//        } else if (BuildConfig.FLAVOR.equals("staging")) {
//            return BASE_URL_CLOUD_STAGING;
//        } else if (BuildConfig.FLAVOR.equals("live")) {
//            return BASE_URL_CLOUD_RELEASE;
//        } else if (BuildConfig.FLAVOR.equals("local")) {
//            return BASE_URL_CLOUD_DEV;
//        } else {
//            return WSS_URL_DEV;
//
//        }
//    }
//
//    public static int dpToPx(Context context, int dp) {
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
//    }
//
//    public static int getWidth(Activity activity) {
//        return getWidthAndHeight(activity)[0];
//    }
//
//    /**
//     * @param activity
//     * @return int imageArray [0] = width [1] = height
//     */
//
//    public static int[] getWidthAndHeight(Activity activity) {
//        int width = 0;
//        int height = 0;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            Display display = activity.getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            width = size.x;
//            height = size.y;
//        } else {
//
//            Display display = activity.getWindowManager().getDefaultDisplay();
//            width = display.getWidth();
//            height = display.getHeight();
//        }
//        int[] data = {width, height};
//        return data;
//    }
//
//    public final static boolean isValidEmail(CharSequence target) {
//        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//    }
//
//    public static String removeFloat(float f) {
//        String a = "" + f;
//        if (a.endsWith(".0")) {
//            a = a.substring(0, a.length() - 2);
//        }
//        return a;
//    }
//
//    public static String removeDouble(double amount) {
//        double cici = amount;
//        DecimalFormat format = new DecimalFormat("#,###.##");
//        return format.format(cici);
//    }
//
//    public static String capWords(String str) {
//        String[] strArray = str.split(" ");
//        StringBuilder builder = new StringBuilder();
//        for (String s : strArray) {
//            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
//            builder.append(cap + " ");
//        }
//        return builder.toString();
//    }
//
//    public static DisplayImageOptions getOptions() {
//        DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
//                .resetViewBeforeLoading(true)
//                .showImageOnLoading(R.drawable.doctor)
//                .showImageForEmptyUri(R.drawable.doctor)
//                .showImageOnFail(R.drawable.doctor)
//                .cacheInMemory(true).cacheOnDisc(true).build();
//        return displayOptions;
//    }
//
//    public static boolean containsCaseInsensitive(String s, List<String> l) {
//        for (String string : l) {
//            if (string.equalsIgnoreCase(s)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static DisplayImageOptions getDisplayOptions(int resourceId) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .resetViewBeforeLoading(true)  // default
//                .showImageOnLoading(resourceId) // resource or drawable
//                .showImageForEmptyUri(resourceId) // resource or drawable
//                .showImageOnFail(resourceId) // resource or drawable
//                .cacheInMemory(true) // default
//                .cacheOnDisc(true) // default
//                .build();
//
//        return options;
//    }
//
//

//
//
//    public static void serverErrorDialog(Context context, String message) {
//        try {
//            String capFirst = message.substring(0, 1).toUpperCase() + message.substring(1);
//
//            ErrorDialog dialog = new ErrorDialog(context, R.style.DialogTheme, capFirst);
//            dialog.setCanceledOnTouchOutside(false);
//            //dialog.setView(dialogView);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            dialog.chkZebra();
////                new MaterialDialog.Builder(context)
//////                .title("Server Error")
////                    .content(capFirst)
////                    .contentGravity(GravityEnum.CENTER)
////                    .positiveText("Ok")
////                    .chkZebra();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void serverErrorDialog(Context context, String message, MaterialDialog.OnDismissListener onDismissListener) {
//        try {
//            String capFirst = message.substring(0, 1).toUpperCase() + message.substring(1);
//
//            ErrorDialog dialog = new ErrorDialog(context, R.style.DialogTheme, capFirst);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            dialog.setOnDismissListener(onDismissListener);
//            dialog.chkZebra();
//
////            MaterialDialog dialog = new MaterialDialog.Builder(context)
//////                .title("Server Error")
////                    .content(message)
////                    .contentGravity(GravityEnum.CENTER)
////                    .positiveText("Ok")
////                    .build();
////                    dialog.setOnDismissListener(onDismissListener);
////                    dialog.chkZebra();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<CountryCode> jsonFileToObject(Context context) {
//        ArrayList<CountryCode> arrayList;
//        Gson gson;
//        String json;
//
//        arrayList = new ArrayList<CountryCode>();
//        gson = new Gson();
//        try {
//            InputStream ims = context.getResources().getAssets().open("json/" + "countryCodes.json");
//            // reader = new InputStreamReader(ims);
//            int size = ims.available();
//            byte[] buffer = new byte[size];
//            ims.read(buffer);
//            ims.close();
//            json = new String(buffer, "UTF-8");
//            Type listType = new TypeToken<ArrayList<CountryCode>>() {
//            }.getType();
//            arrayList = gson.fromJson(json, listType);
//            Log.i("countrycode", String.valueOf(arrayList.get(0).getCode()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//
//    }


}
