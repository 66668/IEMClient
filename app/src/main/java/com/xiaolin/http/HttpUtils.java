package com.xiaolin.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaolin.utils.CheckNetwork;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jingbin on 2017/2/14.
 * 网络请求工具类 retrofit+okhttp3
 * <p>
 */

public class HttpUtils {
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private Object https;

    private boolean debug;//判断 app版本，由application设置

    private final static String TAG = "HttpUtils";
    private final static String API_BASE_URL = "http://192.168.1.245:1132/openapi/";//局域网测试
    //    private final static String API_BASE_URL = "http://101.201.72.112:7016/openapi/";//阿里云测试
    //    private final static String API_BASE_URL = "https://iemapi.yuevision.com/openapi/";//阿里云正式

    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
    public static int per_page_more = 20;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    //application中初始化
    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
        HttpHead.init(context);
    }

    /**
     * 01 登录
     *
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T getServer(Class<T> clz) {
        if (https == null) {
            synchronized (HttpUtils.class) {
                https = getRetrofitBuilder(API_BASE_URL).build().create(clz);
            }
        }
        return (T) https;
    }

    /**
     * retrofit配置 方式1
     * 源码方式，gson解析自定义，和该app的解析方式不同，不使用该方法
     *
     * @param apiUrl
     * @return
     */
    private Retrofit.Builder getBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkhttpClient());//设置okhttp（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory()); //添加自定义转换器，处理响应
        builder.addConverterFactory(GsonConverterFactory.create(getGson())); //添加Gson转换器，处理返回
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    /**
     * retrofit配置 方式2
     *
     * @param apiUrl
     * @return
     */
    private Retrofit.Builder getRetrofitBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkhttpClient());//设置okhttp（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    //
    private Gson getGson() {
        Log.d(TAG, "getGson: HttpUtils走gson转换方法");
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }


    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    //okhttp配置
    public OkHttpClient getOkhttpClient() {
        OkHttpClient client1;
        //        client1 = getUnsafeOkHttpClient();//（https）
        client1 = getUnsafeOkHttpClient2();//（http）
        return client1;
    }

    //okhttp配置
    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            //获取目标网站的证书
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            okBuilder.addInterceptor(getInterceptor());//设置拦截器
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //                    Log.d("HttpUtils", "==come");
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //okhttp配置 修正（http）
    public OkHttpClient getUnsafeOkHttpClient2() {
        try {

            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            okBuilder.addInterceptor(getInterceptor());//设置拦截器
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    Log.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 自定义拦截器，设置请求头
     * <p>
     * 离线读取本地缓存，在线获取最新数据
     */

    class HttpHeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //可添加token验证，oAuth验证，可用链式结构
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetwork.isNetworkConnected(context)) {
                int maxAge = 60;
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);//设置请求的缓存时间
            } else {
                int maxStale = 60 * 60 * 24 * 28;// tolerate 4-weeks stale
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            // 可添加token
            //            if (listener != null) {
            //                builder.addHeader("token", listener.getToken());
            //            }
            // 如有需要，添加请求头
            //            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }

    //设置拦截器，打印okhttp log
    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //可以通过 setLevel 改变日志级别,共包含四个级别：NONE、BASIC、HEADER、BODY
        /**
         * NONE 不记录
         * BASIC 请求/响应行
         * HEADERS 请求/响应行 + 头
         * BODY 请求/响应行 + 头 + 体
         */
        if (debug) {
            // 打印okhttp
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }
}
