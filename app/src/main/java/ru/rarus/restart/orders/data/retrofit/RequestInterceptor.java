package ru.rarus.restart.orders.data.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.rarus.restart.orders.data.info.InfoStorage;

/**
 * Created by lysmik on 27.12.16.
 */

public class RequestInterceptor implements Interceptor{

    private InfoStorage infoStorage;

    public RequestInterceptor(InfoStorage infoStorage) {
        this.infoStorage = infoStorage;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("token", infoStorage.getToken())
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
