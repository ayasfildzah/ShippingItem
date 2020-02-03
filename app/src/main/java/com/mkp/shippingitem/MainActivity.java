package com.mkp.shippingitem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mkp.shippingitem.model.ResponLoginModel;
import com.mkp.shippingitem.model.ResponseShippingInsert;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private final String serverUrl = "http://alita.massindo.com/ShippingPresenterApi/v1/users/sign_in";
    protected EditText usermail;
    protected String enteredUsermail;
    private String enteredPassword;
    private EditText password;
    private SharedPreferences mPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usermail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  new SignPro().execute();*/


                if (usermail.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                } else if (usermail.getText().toString().length() <= 1 || password.getText().toString().length() <= 1) {
                    Toast.makeText(MainActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new sendLog().execute();
                }

                //ini di taro di button click yang inser
//                new insertShipping().execute();

            }
        });


    }

    private class sendLog extends AsyncTask<String, Void, ResponLoginModel> {
        private ProgressDialog pgDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            pgDialog.setTitle("\tRequest Login");
            pgDialog.setMessage("\tSedang cek data");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ResponLoginModel doInBackground(String... strings) {
            enteredUsermail = usermail.getText().toString();
            enteredPassword = password.getText().toString();
            try {
                return AppConstant.getLoginApi().LoginA(MainActivity.this, enteredUsermail, enteredPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponLoginModel responLoginModel) {
            super.onPostExecute(responLoginModel);
            pgDialog.dismiss();
            try {

                //Jika Kondisi sukses
                if(responLoginModel.getId() !=null){
                    Toast.makeText(MainActivity.this, "" + responLoginModel.getId(), Toast.LENGTH_SHORT).show();
                    LogHelper.verbose(TAG, "resultSuksesLogin: " + responLoginModel);

                    //Set data SharedPreferences
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("creator",responLoginModel.getName());
                    editor.commit();
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, ShowHistory.class);
                    startActivity(intent);
                }

                //Jika Kondisi Error
                if (responLoginModel.getError().equals("invalid email and password combination")) {
                    Toast.makeText(MainActivity.this, "" + responLoginModel.getError(), Toast.LENGTH_SHORT).show();
                    LogHelper.verbose(TAG, "resultError: " + responLoginModel.getError());
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.remove("creator");
                    editor.clear();
                    editor.commit();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }


    //Ini pindahin ke activity yang buat insert shipping
    private class insertShipping extends AsyncTask<String, Void, ResponseShippingInsert> {
        private ProgressDialog pgDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ResponseShippingInsert doInBackground(String... strings) {
          String delivery_number="2";
          String status="ok";
          String note="Dummy2";
            try {
                return AppConstant.getShippingInsertPresenterApi().insShipping(MainActivity.this, delivery_number, status,note);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseShippingInsert result) {
            super.onPostExecute(result);
            pgDialog.dismiss();
            try {

              LogHelper.verbose(TAG,"RESULT SHIPPING :"+result);
            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}