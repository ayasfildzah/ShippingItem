package com.mkp.shippingitem.service;

import android.content.Context;

import com.mkp.shippingitem.model.ResponLoginModel;
import com.mkp.shippingitem.util.GetHttpsURLConnection;
import com.mkp.shippingitem.util.HttpsTrustManager;
import com.mkp.shippingitem.util.LogHelper;
import com.mkp.shippingitem.util.ReadResponse;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.mkp.shippingitem.util.AppConstant.CONNECTION_TIMEOUT;
import static com.mkp.shippingitem.util.AppConstant.READ_TIMEOUT;

public class LoginPresenterApi {
    private static final String TAG = LoginPresenterApi.class.getName();

    public ResponLoginModel LoginA(Context context, String email, String password) throws IOException {

        try {
            HttpsTrustManager.allowAllSSL();
            HttpsURLConnection connection = GetHttpsURLConnection.getHttpsURLConnection(context, "users/sign_in");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = ("{\"email\":" + "\"" + email + "\"" + "," +
                    "\"password\":" + "\"" + password + "\"" + "}");
            LogHelper.verbose(TAG, "SendLogin" + data);
            OutputStream stream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            connection.connect();

            String response = ReadResponse.readResponse(connection);

            //JSON condition success email and password
           if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ResponLoginModel res = new ResponLoginModel();
                    res.setId(jsonObject.getString("id"));
                    res.setName(jsonObject.getString("name"));
                    res.setPhone(jsonObject.getString("phone"));
                    res.setEmail(jsonObject.getString("email"));
                    res.setPassword(jsonObject.getString("password"));
                    res.setPassword_confirmation(jsonObject.getString("password_confirmation"));
                    res.setCompany_id(jsonObject.getString("company_id"));
                    res.setImage(jsonObject.getString("image"));

                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


           }
           //JSON condition error email and password
            if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ResponLoginModel res = new ResponLoginModel();

                    res.setError(jsonObject.getString("error"));

                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } catch (SocketTimeoutException e1) {
            e1.printStackTrace();
            LogHelper.verbose(TAG, "LoginA: " + e1);
        } catch (InterruptedIOException e2) {
            e2.printStackTrace();
            LogHelper.verbose(TAG, "LoginA: " + e2);
        } catch (MalformedURLException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
            LogHelper.verbose(TAG, "LoginA: " + e3);
        } catch (Exception e4) {
            e4.printStackTrace();
            LogHelper.verbose(TAG, "LoginA: " + e4);
        }


        return null;
    }


}
