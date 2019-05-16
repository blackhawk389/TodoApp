package com.app.boxee.shopper.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.widget.Toast;

import com.app.boxee.shopper.BuildConfig;
import com.app.boxee.shopper.api.MetadataRestAPI;
import com.app.boxee.shopper.api.NodeRestAPI;
import com.app.boxee.shopper.api.RestAPI;
import com.app.boxee.shopper.database.ShopperDatabase;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.network.exception.ErrorRequestException;
import com.app.boxee.shopper.network.exception.ServerException;
import com.app.boxee.shopper.repositories.ShopperRepository;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.boxee.shopper.application.App.context;


@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---
    @Inject
    @Named("provideRetrofit")
    Retrofit mRetrofit;
    @Inject
    @Named("provideRetrofit2")
    Retrofit mRetrofit2;
    @Inject
    @Named("provideRetrofit3")
    Retrofit mRetrofit3;
    @Inject
    @Named("provideRetrofit4")
    Retrofit mRetrofit4;

    @Provides
    @Singleton
    ShopperDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                ShopperDatabase.class, "ShopperDatabase.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    ShopperDao provideUserDao(ShopperDatabase database) {
        return database.userDao();
    }

    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    ShopperRepository provideUserRepository(RestAPI webservice, ShopperDao shopperDao, Executor executor, TinyDB tinyDB, NodeRestAPI nodeWebservice, MetadataRestAPI metadataRestAPI) {
        return new ShopperRepository(webservice, shopperDao, executor, tinyDB, nodeWebservice, metadataRestAPI);
    }


    // --- NETWORK INJECTION ---

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Named("provideRetrofit")
    Retrofit provideRetrofit(Application application, Gson gson, TinyDB tinyDB) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ChuckInterceptor(application))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = null;
                        if (tinyDB.getString(Vals.CUSTOMER_TOKEN) != null && !tinyDB.getString(Vals.CUSTOMER_TOKEN).equalsIgnoreCase("")) {
                            request = original.newBuilder()
                                    .addHeader("x-auth-token", tinyDB.getString(Vals.CUSTOMER_TOKEN))
                                    .method(original.method(), original.body())
                                    .build();
                            Response response = chain.proceed(request);

                            long tx = response.sentRequestAtMillis();
                            long rx = response.receivedResponseAtMillis();
                            double seconds = (rx - tx) / 1000.0;
                            System.out.println("response time : " + seconds + " ms");
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("live")
                                    && BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {

                            } else {
                                Utils.showToast(context, "response time : " + seconds + " secs");
                            }
                            if (response == null || !response.isSuccessful()) {
                                try {
                                    throw new ErrorRequestException(response);
                                } catch (ErrorRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (response != null && response.code() != 200) {
                                throw ServerException.getError(Utils.getStringFromInputStream(response.body().byteStream()));
                            }
                            return response;
                        } else {
                            request = original.newBuilder()
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    }
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        String baseUrl = "";
        if (tinyDB.getString("CUSTOM_BASE_URL").equalsIgnoreCase("")) {
            if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
                baseUrl = "https://dev.myboxee.net/";
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
                baseUrl = "https://stage.myboxee.net/";
            } else {
                baseUrl = "https://customer.clexsa.com/";
            }
        } else {
            if (tinyDB.getString("CUSTOM_BASE_URL").contains("dev")) {
                baseUrl = "https://dev.myboxee.net/";
            } else if (tinyDB.getString("CUSTOM_BASE_URL").contains("stage")) {
                baseUrl = "https://stage.myboxee.net/";
            } else {
                baseUrl = "https://customer.clexsa.com/";
            }


        }
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return mRetrofit;
    }

    @Provides
    @Named("provideRetrofit3")
    Retrofit provideRetrofit3(Application application, Gson gson, TinyDB tinyDB) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ChuckInterceptor(application))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = null;

                        if (tinyDB.getString(Vals.CUSTOMER_TOKEN) != null && !tinyDB.getString(Vals.CUSTOMER_TOKEN).equalsIgnoreCase("")) {
                            request = original.newBuilder()
                                    .addHeader("x-auth-token", tinyDB.getString(Vals.CUSTOMER_TOKEN))
                                    .addHeader("lang", Utils.getCurrentLanguage().equalsIgnoreCase("") ? "en" : Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
//                            }
//                            else{
//                                request = original.newBuilder()
//                                        .method(original.method(), original.body())
//                                        .build();
//                            }

                            Response response = chain.proceed(request);

                            long tx = response.sentRequestAtMillis();
                            long rx = response.receivedResponseAtMillis();
                            double seconds = (rx - tx) / 1000.0;
                            System.out.println("response time : " + seconds + " ms");
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("live")
                                    && BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {

                            } else {
                                Utils.showToast(context, "response time : " + seconds + " secs");
                            }
                            if (response == null || !response.isSuccessful()) {
                                try {
                                    throw new ErrorRequestException(response);
                                } catch (ErrorRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (response != null && response.code() != 200) {
                                throw ServerException.getError(Utils.getStringFromInputStream(response.body().byteStream()));
                            }
                            return response;
                        } else {
                            request = original.newBuilder()
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    }
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        String baseUrl = "";
        if (tinyDB.getString("CUSTOM_BASE_URL").equalsIgnoreCase("")) {
            baseUrl = Vals.GET_BASE_URL();
        } else {
            baseUrl = tinyDB.getString("CUSTOM_BASE_URL");
        }
        mRetrofit3 = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return mRetrofit3;
    }

    @Provides
    @Named("provideRetrofit2")
    Retrofit provideRetrofit2(Application application, Gson gson, TinyDB tinyDB) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ChuckInterceptor(application))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = null;
                        if (tinyDB.getString(Vals.CUSTOMER_TOKEN) != null && !tinyDB.getString(Vals.CUSTOMER_TOKEN).equalsIgnoreCase("")) {
                            request = original.newBuilder()
                                    .addHeader("x-auth-token", tinyDB.getString(Vals.CUSTOMER_TOKEN))
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
//                            }
//                            else{
//                                request = original.newBuilder()
//                                        .method(original.method(), original.body())
//                                        .build();
//                            }

                            Response response = chain.proceed(request);

                            long tx = response.sentRequestAtMillis();
                            long rx = response.receivedResponseAtMillis();
                            double seconds = (rx - tx) / 1000.0;
                            System.out.println("response time : " + seconds + " ms");
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("live")
                                    && BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {

                            } else {
                                Utils.showToast(context, "response time : " + seconds + " secs");

                            }
                            if (response == null || !response.isSuccessful()) {
                                try {
                                    throw new ErrorRequestException(response);
                                } catch (ErrorRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (response != null && response.code() != 200) {
                                throw ServerException.getError(Utils.getStringFromInputStream(response.body().byteStream()));
                            }
                            return response;
                        } else {
                            request = original.newBuilder()
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
//                            return chain.proceed(original);
                        }
                    }
                }).connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        String baseUrl = "";

        if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            baseUrl = "https://devnode.myboxee.net:3009/";


        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("staging")) {
            baseUrl = "https://stagenode.myboxee.net:3010";
        } else {
            baseUrl = "https://node.myboxee.net:3011/";
        }
        mRetrofit2 = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build();
        return mRetrofit2;
    }

    @Provides
    @Named("provideRetrofit4")
    Retrofit ProvideRetrofit4 (Application application, Gson gson, TinyDB tinyDB) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ChuckInterceptor(application))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = null;
                        if (tinyDB.getString(Vals.CUSTOMER_TOKEN) != null && !tinyDB.getString(Vals.CUSTOMER_TOKEN).equalsIgnoreCase("")) {
                            request = original.newBuilder()
                                    .addHeader("x-auth-token", tinyDB.getString(Vals.CUSTOMER_TOKEN))
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
                            Response response = chain.proceed(request);

                            long tx = response.sentRequestAtMillis();
                            long rx = response.receivedResponseAtMillis();
                            double seconds = (rx - tx) / 1000.0;
                            System.out.println("response time : " + seconds + " ms");
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("live")
                                    && BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {

                            } else {
                                Utils.showToast(context, "response time : " + seconds + " secs");
                            }
                            if (response == null || !response.isSuccessful()) {
                                try {
                                    throw new ErrorRequestException(response);
                                } catch (ErrorRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (response != null && response.code() != 200) {
                                throw ServerException.getError(Utils.getStringFromInputStream(response.body().byteStream()));
                            }
                            return response;
                        } else {
                            request = original.newBuilder()
                                    .addHeader("lang", Utils.getCurrentLanguage())
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    }
                }).connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        String baseUrl = "http://api.boxee.local";

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return mRetrofit;
    }

    @Provides
    @Singleton
    NodeRestAPI provideApiNodeWebservice(@Named("provideRetrofit2") Retrofit restAdapter) {
        return restAdapter.create(NodeRestAPI.class);
    }

    @Provides
    @Singleton
    MetadataRestAPI provideApiMetadataWebservice(@Named("provideRetrofit") Retrofit restAdapter) {
        return restAdapter.create(MetadataRestAPI.class);
    }

    @Provides
    @Singleton
    RestAPI provideApiWebservice(@Named("provideRetrofit3") Retrofit restAdapter) {
        return restAdapter.create(RestAPI.class);
    }

    @Provides
    @Singleton
    TinyDB provideSharedPreference(Application application) {
        return new TinyDB(application);
    }

}
