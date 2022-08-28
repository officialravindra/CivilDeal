package com.civildeal.civildeal.api;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;

import com.civildeal.civildeal.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Api_Client extends ContextWrapper {
    //    https://mdsnext.com/maverick/api/
    //public static String Basew_URl = Api_Client.domain+"excellence/admin/api/";
    public static String domain = "https://civildeal.com/";
    //    public static String Basew_URl = "http://excellenceacademy4mds.com/admin/api/";
    public static String Basew_URl ="https://civildeal.com/Api/";
    public static String Base_ImageURl ="https://civildeal.com/sandbox/public/assets/uploads/vendor/images/";

    private static Api api_;
    private static Api_Client mInstance;
    private static Context appContext;
    static Retrofit retrofit;
    HttpsURLConnection httpsURLConnection;

    public Api_Client(Context base) {
        super(base);
    }

    public static synchronized Api_Client getInstance(Context context) {
        appContext=context.getApplicationContext();
        if (mInstance == null) {
            mInstance = new Api_Client(context.getApplicationContext());
        }
        return mInstance;
    }

    public static Api getInstance() {
        if (api_ == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)


                    .readTimeout(60, TimeUnit.SECONDS)

                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Basew_URl)
                    .client(getUnsafeOkHttpClient().build())
                    .build();
            Api api_ = retrofit.create(Api.class);
            return api_;

        } else
            return api_;
    }


    Retrofit retrofit1 = new Retrofit.Builder()
            .baseUrl(Basew_URl)
            .client(getUnsafeOkHttpClient().build())
            .build();


    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

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

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
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




}