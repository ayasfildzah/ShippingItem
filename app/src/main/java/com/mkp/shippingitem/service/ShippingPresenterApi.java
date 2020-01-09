package com.mkp.shippingitem.service;

import android.content.Context;

import com.mkp.shippingitem.model.DataModel;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.GetHttpsURLConnection;
import com.mkp.shippingitem.util.HttpsTrustManager;
import com.mkp.shippingitem.util.LogHelper;
import com.mkp.shippingitem.util.ReadResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.mkp.shippingitem.util.AppConstant.CONNECTION_TIMEOUT;
import static com.mkp.shippingitem.util.AppConstant.READ_TIMEOUT;

public class ShippingPresenterApi {
    private static final String TAG = ShippingPresenterApi.class.getName();

    public ArrayList<DataModel> dm(Context context) throws IOException {
        try {
            HttpsTrustManager.allowAllSSL();
            HttpsURLConnection conection = GetHttpsURLConnection.getHttpsURLConnection(context, "shipping_items");
            conection.setRequestMethod("GET");
            conection.setReadTimeout(READ_TIMEOUT);
            conection.setConnectTimeout(CONNECTION_TIMEOUT);
            conection.connect();
            ArrayList<DataModel> data1 = new ArrayList<DataModel>();
            if (conection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                AppConstant.RESULT = ReadResponse.readResponse(conection);
                try {
                    DataModel model = new DataModel();
                    JSONObject jo = new JSONObject(AppConstant.RESULT);
                    model.setStatus(jo.getString("status"));
                    model.setMessage(jo.getString("message"));
                    JSONArray ja = jo.getJSONArray("result");
                    for (int x = 0; x < ja.length(); x++) {
                        JSONObject jo1 = ja.getJSONObject(x);
                        DataModel model1 = new DataModel();
                        model1.setId(jo1.getInt("id"));
                        model1.setDelivery_number(jo1.getString("delivery_number"));
                        model1.setSttus(jo1.getString("status"));
                        model1.setCreated_at(jo1.getString("created_at"));

                        data1.add(model1);
                    }
                    LogHelper.verbose(TAG, "result: " + AppConstant.RESULT);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return data1;
            }

        } catch (SocketTimeoutException e1) {
            e1.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e1);
        } catch (InterruptedIOException e2) {
            e2.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e2);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e3);
        }
        return null;
    }


}
