package com.amostra.net.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amostra.net.data.model.Login;
import com.amostra.net.data.model.Token;
import com.amostra.net.data.model.UserResponse;
import com.amostra.net.util.Constants;
import com.amostra.net.util.UserPrefManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private UserPrefManager userPrefManager;
    private Token token;
    private static Retrofit retrofit = null;
    private static Gson gson = new Gson();
    private Context context;

    public API(Context context){
        userPrefManager = new UserPrefManager(context);
        token = userPrefManager.loadObject(UserPrefManager.TOKEN_KEY, Token.class);
        this.context = context;
        loadClient();
    }

    private void loadClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .build();
    }

    public LoginAPI getClient(Class<LoginAPI> apiInterfaceClass) {

        return retrofit.create(apiInterfaceClass);
    }

    public void logoff(){
        userPrefManager.saveObject(UserPrefManager.TOKEN_KEY, new Token());
        userPrefManager.setValueBoolean(UserPrefManager.LOGIN_STATUS, false);
    }


    public ProductAPI apiProducts() {

        return retrofit.create(ProductAPI.class);

    }

    private OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();



                    Request.Builder requestBuilder = original.newBuilder();

                    if (token != null) requestBuilder.header("Authorization", "Bearer " + token.getValue());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken() {
        boolean result = false;
        if (token != null) {
            try {

                String tokenId = token.getValue();
                if (tokenId != null) {
                    Call<Boolean> call = getClient(LoginAPI.class).validateToken();
                    retrofit2.Response<Boolean> response = call.execute();

                    if(response.isSuccessful()){

                        if(response.body() != null) {
                            result = response.body();
                            Log.i("LoginLog","Sucesso: " + result);
                        }
                        else{
                            Log.i("LoginLog","Error: " + response.raw());
                        }
                    }else {
                        Log.i("LoginLog","Error: " + response.raw());
                    }

                }else{
                    Log.i("LoginLog","Token vazio");
                }
            } catch (Exception e) {
                Log.i("LoginLog", e.getMessage());
            }
        }

        if(!result) userPrefManager.setValueBoolean(UserPrefManager.LOGIN_STATUS, false);

        return result;
    }

    public void refreshToken(UserResponse userResponse) {
        token = userResponse.getToken();
        userPrefManager.saveObject(UserPrefManager.USER_KEY, userResponse.getUser());
        userPrefManager.saveObject(UserPrefManager.TOKEN_KEY, token);
        loadClient();
    }

    public static String toJson(Object object) {
        if (gson == null) gson = new Gson();
        return gson.toJson(object);
    }

    public static <GenericClass> GenericClass fromJson(String json, Class<GenericClass> classType) {
        if (gson == null) gson = new Gson();
        return gson.fromJson(json, classType);
    }
}
