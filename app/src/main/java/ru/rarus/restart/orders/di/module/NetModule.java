package ru.rarus.restart.orders.di.module;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.rarus.restart.orders.MyApp;
import ru.rarus.restart.orders.data.info.InfoStorage;
import ru.rarus.restart.orders.data.retrofit.DateDeserializer;
import ru.rarus.restart.orders.data.retrofit.OsrmApi;
import ru.rarus.restart.orders.data.retrofit.RestAPI;
import ru.rarus.restart.orders.data.settings.RestartSettings;
import ru.rarus.restart.orders.di.module.utils.HashSHA1;


@Module
public class NetModule {

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder().create());
    }


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(RestartSettings restartSettings, InfoStorage infoStorage) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.header("X-Auth-Key", MyApp.getApp().getAndroidId());
            Long tsLong = System.currentTimeMillis() / 1000;
            requestBuilder.header("X-Auth-Timestamp", tsLong.toString());
            String sha1Token = HashSHA1.SHA1(MyApp.getApp().getAndroidId() + tsLong.toString() );
            requestBuilder.header("X-Auth-Token", sha1Token);

            requestBuilder.method(original.method(), original.body());
            HttpUrl.parse(restartSettings.getCurrentServerAddress()).host();

            HttpUrl newUrl = original.url().newBuilder()
                    .scheme(HttpUrl.parse(restartSettings.getCurrentServerAddress()).scheme())
                    .host(HttpUrl.parse(restartSettings.getCurrentServerAddress()).host())
                    .port(HttpUrl.parse(restartSettings.getCurrentServerAddress()).port())
                    .build();

            Request request = requestBuilder.url(newUrl).build();

            return chain.proceed(request);
        });



        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);

        return httpClient.build();
    }


    @Singleton
    @Provides
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    RestAPI provideRetrofit(RestartSettings restartSettings, OkHttpClient client, RxJavaCallAdapterFactory adapterFactory) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(restartSettings.getCurrentServerAddress())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(adapterFactory)
                    .client(client)
                    .build();
            return retrofit.create(RestAPI.class);
        } catch (Exception e){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.0.0.1")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(adapterFactory)
                    .client(client)
                    .build();
            return retrofit.create(RestAPI.class);
        }


    }

    @Singleton
    @Provides
    OsrmApi provideOsrmApi(RxJavaCallAdapterFactory adapterFactory) {
        Gson gson = new GsonBuilder().create();


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://router.project-osrm.org")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();

        return retrofit.create(OsrmApi.class);
    }


}
