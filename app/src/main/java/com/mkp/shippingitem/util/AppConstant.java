package com.mkp.shippingitem.util;


import com.mkp.shippingitem.service.LoginPresenterApi;
import com.mkp.shippingitem.service.ShippingPresenterApi;

public class AppConstant {


    public static final String BASE_URL = "https://alita.massindo.com/api/v1/";
    public static final int READ_TIMEOUT = 30000;
    public static final int CONNECTION_TIMEOUT = 30000;
    public static String RESULT = "RESULT";
    private static ShippingPresenterApi svcApi;
    private static LoginPresenterApi loginApi;

    public static ShippingPresenterApi getApiShippingList() {
        if (svcApi != null) {
            return svcApi;
        } else {
            svcApi = new ShippingPresenterApi();
        }
        return svcApi;
    }


    public static LoginPresenterApi getLoginApi() {
        if (loginApi != null) {
            return loginApi;
        } else {
            loginApi = new LoginPresenterApi();
        }
        return loginApi;
    }
}
