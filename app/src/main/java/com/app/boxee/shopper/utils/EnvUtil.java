package com.app.boxee.shopper.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.data.Env;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class EnvUtil {

    private static Env env;
    private static String FILENAME = "";
    private static String FILENAME_EN = "BoxeeShopper_EN.json";
    private static String FILENAME_AR = "BoxeeShopper_AR.json";
    private static String APP_LANGUAGE = "";
    private static String URLEn_DEV = "https://devtracking.myboxee.net/app/constants/CLEXEnv.json";
    private static String URLAr_DEV = "https://devtracking.myboxee.net/app/constants/CLEXEnvArabic.json";
    private static String URLEn_STAGING = "https://stagetracking.myboxee.net/app/constants/CLEXEnv.json";
    private static String URLAr_STAGING = "https://stagetracking.myboxee.net/app/constants/CLEXEnvArabic.json";


    public static boolean downloaded = true;               //hardcoded : to pick local JSON always
    public static boolean downloadedEn;

    public static boolean downloadedAr;
    private static String encJson;
    public static String storageUrlEn = "";
    public static String storageUrlAr = "";



    public static Env getInstance(Context context) {
//        if(env != null) {
//            return env;
//        }else {
        return EnvUtil.getEnv(context);
//        }
    }

    public static Env getEnv(Context context) {
        if (TinyDB.dbContext == null) {
            TinyDB.dbContext = context.getApplicationContext();
        }
        TinyDB tinydb = TinyDB.getInstance();

//        if (env != null) {
//            return env;
//        } else {
//            String json = loadJSONFromAsset(context, FILENAME, false);
        APP_LANGUAGE = tinydb.getString(Vals.CURRENT_LANGUAGE);
//        URLEn_DEV = tinydb.getString(Vals.URL_EN);
//        URLAr_DEV = tinydb.getString(Vals.URL_AR);

        if (APP_LANGUAGE.equalsIgnoreCase("ar")) {
            FILENAME = FILENAME_AR;
        } else {
            FILENAME = FILENAME_EN;
//                URL= tinydb.getString(Vals.URL_AR);

        }
//        if(Utils.isOnline(context))
//        encJson = loadJSONFromAsset(context, FILENAME, false);
//        else{
        encJson = loadJSONFromAsset(context, FILENAME, true);

//        }

        try {
//                String strDec = AESEnDecryption.decrypt(encJson, "mPedKN8jjb62c64USZqvVUwLgJgYMSHX").trim();
            env = new GsonBuilder().create().fromJson(encJson, Env.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
//                try {
//                    env = new GsonBuilder().create().fromJson(json, Env.class);
//                } catch (Exception e2) {
//                    json = loadJSONFromAsset(context, FILENAME, true);
//                    env = new GsonBuilder().create().fromJson(json, Env.class);
//                }
        }

        return env;
//        }
    }


    private static String loadJSONFromAsset(Context context, String fileName, boolean forceLoadFromAsset) {

        String json = null;
        try {
            File file = new File(context.getFilesDir(), fileName);
            InputStream is;
            if (file.exists() && !forceLoadFromAsset) {
                is = new FileInputStream(file);
                if (!downloaded) {
                    downloadJson(context);
                }
            } else {
                is = context.getAssets().open("json/" + fileName);
                if (!forceLoadFromAsset && !downloaded) {
                    downloadJson(context);
                }
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


//    public static JsonElement getValueByKey(Context context, String key) {
//        String json = loadJSONFromAsset(context, FILENAME, false);
//        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
//        return jsonObject.get(key);
//    }


//    public static String getWhoIsPatientValue(@NonNull String label) {
//        if (env != null) {
//            for (Item item : env.appStepOnePatients) {
//                if (item.label.equals(label))
//                    return item.value;
//            }
//        }
//        return null;
//    }

    private static void downloadJson(final Context context) {
        downloadAr(context);
        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            storageUrlEn = URLEn_STAGING;
        } else {
            storageUrlEn = URLEn_DEV;

        }
//            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//            StorageReference reference = firebaseStorage.getReferenceFromUrl(storageUrl);
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String str = "";
//                    String ENV_URL="https://dev-cloud.myhealthathand.com/api/get-encrypted-json";
////                    String ENV_URL="";
//                    if(BuildConfig.FLAVOR.equalsIgnoreCase("live")){
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_LIVE;
//                        ENV_URL="https://app-cloud.myhealthathand.com/api/get-encrypted-json";
//                    } else if(BuildConfig.FLAVOR.equalsIgnoreCase("staging")){
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_STAGING;
//                        ENV_URL="https://staging-cloud.myhealthathand.com/api/get-encrypted-json";
//                    } else{
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_DEV;
//                        ENV_URL="https://dev-cloud.myhealthathand.com/api/get-encrypted-json";
//                    }
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(storageUrlEn).openConnection();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedInputStream is = new BufferedInputStream(
                                urlConnection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ((line = br.readLine()) != null) {
                            str += line;
                        }
                        br.close();
                        is.close();
                        urlConnection.disconnect();

//                        String dec = new String(AESEnDecryption.decrypt(str, "mPedKN8jjb62c64USZqvVUwLgJgYMSHX"));
//                        Logger.d(EnvUtil.class.getName(), "Decrypted:"+dec);
//                        str = dec;

                        FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), FILENAME_EN), false);
                        fos.write(str.getBytes());
                        fos.close();
                        downloadedEn = true;
                    }
                } catch (Exception e) {
                    downloadedEn = false;
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000); // delay
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (downloadedEn && downloadedAr) {
                    downloaded = true;
                }
                if (downloaded) {
                    env = null;
                    getEnv(context);
                }
            }
        }.execute();

    }

    private static void downloadAr(Context context) {

        if (BuildConfig.FLAVOR.equalsIgnoreCase("live")) {

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            storageUrlAr = URLAr_STAGING;
        } else {
            storageUrlAr = URLAr_DEV;

        }
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference reference = firebaseStorage.getReferenceFromUrl(storageUrl);
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String str = "";
//                    String ENV_URL="https://dev-cloud.myhealthathand.com/api/get-encrypted-json";
////                    String ENV_URL="";
//                    if(BuildConfig.FLAVOR.equalsIgnoreCase("live")){
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_LIVE;
//                        ENV_URL="https://app-cloud.myhealthathand.com/api/get-encrypted-json";
//                    } else if(BuildConfig.FLAVOR.equalsIgnoreCase("staging")){
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_STAGING;
//                        ENV_URL="https://staging-cloud.myhealthathand.com/api/get-encrypted-json";
//                    } else{
////                        ENV_URL= Vals.JSON_URL_ENV_CLOUD_DEV;
//                        ENV_URL="https://dev-cloud.myhealthathand.com/api/get-encrypted-json";
//                    }
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(storageUrlAr).openConnection();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedInputStream is = new BufferedInputStream(
                                urlConnection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ((line = br.readLine()) != null) {
                            str += line;
                        }
                        br.close();
                        is.close();
                        urlConnection.disconnect();

//                        String dec = new String(AESEnDecryption.decrypt(str, "mPedKN8jjb62c64USZqvVUwLgJgYMSHX"));
//                        Logger.d(EnvUtil.class.getName(), "Decrypted:"+dec);
//                        str = dec;

                        FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), FILENAME_AR), false);
                        fos.write(str.getBytes());
                        fos.close();
                        downloadedAr = true;
                    }
                } catch (Exception e) {
                    downloadedAr = false;
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000); // delay
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (downloadedAr && downloadedEn) {
                    downloaded = true;
                }
                if (downloaded) {
                    env = null;
                    getEnv(context);
                }
            }
        }.execute();

    }

}
