package com.mkp.shippingitem.util;

import android.content.Context;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.mkp.shippingitem.util.AppConstant.BASE_URL;
import static com.mkp.shippingitem.util.AppConstant.CONNECTION_TIMEOUT;
import static com.mkp.shippingitem.util.AppConstant.READ_TIMEOUT;

public class GetHttpsURLConnection {

    private final static String TAG=GetHttpsURLConnection.class.getName();
    public GetHttpsURLConnection() {

    }

    public static HttpsURLConnection getHttpsURLConnection(Context context, String endPoint) throws IOException {

        URL url = new URL(BASE_URL + endPoint);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        HttpsTrustManager.allowAllSSL();
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        return urlConnection;
    }
}
