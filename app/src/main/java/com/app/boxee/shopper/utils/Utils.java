package com.app.boxee.shopper.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.activities.SplashActivity;
import com.app.boxee.shopper.data.AppSidemenu;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.NavViewListItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.MediaStore.EXTRA_DURATION_LIMIT;
import static android.support.v4.content.FileProvider.getUriForFile;


public class Utils {
    public static boolean isOnline(Context context) {

        try {
            if (context != null) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null && ni.isConnected())
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static String isSubscribed(Context context) {
//        LoginResponse user = LoginResponse.getLoginUser(context);
//        SharedPreferences editor = context.getSharedPreferences(
//                context.getPackageName(), Context.MODE_PRIVATE);
//        String token = editor.getString(context.getString(R.string.push_token), null);
//        if (token != null && user != null) {
//            String userId = editor.getString(context.getString(R.string.user_id), null);
//            if (userId == null || !userId.equals(user.getUserLogin())) {
//                return token;
//            }
//        }
        return null;

    }

    public static void sendRegistrationToServer(Context context, String token, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.push_token), token);
        editor.putString(context.getString(R.string.user_id), userId);
        editor.commit();
    }

    public static void setLocale(Activity activity, String local) {
//        if (!BuildConfig.DEBUG)
//            Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(activity));
//        if (!BuildConfig.ENGLISH) {
        String languageToLoad = local; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT > 17) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            config.setLocales(new LocaleList(locale));
        }
            activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());


    }

    public static String getCurrentLanguage() {
        return TinyDB.getInstance().getString(Vals.CURRENT_LANGUAGE);

    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId, boolean bStack, boolean withTransition) {

        if (bStack == true) {
            try {
                if (withTransition) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right)
                            .replace(frameId, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(frameId, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
                            .commit();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (withTransition) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right)
                            .replace(frameId, fragment)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(frameId, fragment)
                            .commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeCurrentFragment(@NonNull FragmentManager fragmentManager) {
        try {
            fragmentManager.popBackStack();
        } catch (Exception e) {
        }
    }

    public static List<NavViewListItem> getNavViewItems(Activity activity) {
        List<NavViewListItem> items = new ArrayList<>();
        Env env = EnvUtil.getInstance(activity);
        List<AppSidemenu> array = env.getAppSidemenu();
//        String[] array = activity.getResources().getStringArray(R.array.nav_options);
//        NavViewListItem item = new NavViewListItem("ic_menu_profile",array.get(0).getValue());
//        items.add(new NavViewListItem("ic_menu_home", array.get(0).getValue()));
        NavViewListItem item = new NavViewListItem("ic_menu_profile", array.get(0).getValue());
        items.add(item);
//        items.add(new NavViewListItem("ic_menu_history", array.get(1).getValue()));
        items.add(new NavViewListItem("ic_menu_addresses", array.get(2).getValue()));
       // items.add(new NavViewListItem("ic_menu_help", array.get(3).getValue()));
        items.add(new NavViewListItem("ic_menu_contact", array.get(4).getValue()));
        items.add(new NavViewListItem("ic_menu_about", array.get(5).getValue()));
        items.add(new NavViewListItem("customersupport", array.get(7).getValue()));
//        items.add(new NavViewListItem("ic_menu_logout", array.get(7).getValue()));

        return items;
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public static int getLanguage(TinyDB tinyDB) {
        String language = tinyDB.getString(Vals.CURRENT_LANGUAGE);
        return language.equals("en") ? 1 : 2;

    }

    public static void showToast(Context context,String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public static File formatImage(Bitmap bitmap,Activity activity) {

        FileOutputStream out = null;
        File fileName = null;
        try {
            out = new FileOutputStream(fileName = getGPGPhotoFileUri(activity));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Throwable ignore) {
            }
        }


        return fileName;
    }


    public static File getGPGPhotoFileUri(Activity activity) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
            return image;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
