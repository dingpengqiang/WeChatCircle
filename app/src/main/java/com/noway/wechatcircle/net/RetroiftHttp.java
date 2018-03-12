package com.noway.wechatcircle.net;



import com.noway.wechatcircle.constant.Constants;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 包名:    com.noway.wechatcircle
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2018/3/10
 * 版本:    V-1.0.0
 */
public class RetroiftHttp {


    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build();

    public static <T> T createApi(Class<T> clazz) {

        return retrofit.create(clazz);
    }

    private static OkHttpClient getClient() {


        return new OkHttpClient.Builder()

                //并设置超时时间
                .connectTimeout(20, TimeUnit.SECONDS)
                //连接失败后是否重新连接
                //.retryOnConnectionFailure(true)

                //.addInterceptor()

                // 对所有请求添加请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

//                        Logger.e(Config.TAG, "url: "+chain.request().url() );

                        return chain.proceed(chain.request() // originalRequest
                                .newBuilder()
                                //添加请求头
                                .build());
                    }
                })
                //拦截器中打印请求参数
                .addInterceptor(new LoggingInterceptor())
                //设置缓存
                .build();
    }

    /**
     * 主要用于查看请求信息及返回信息，如链接地址、头信息、参数信息等，参考下面的log-拦截器定义：
     */
    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间

            Logger.e(String.format(Locale.CHINA, "发送请求: %s on %s%n%s", request.url(),
                    chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();//收到响应的时间

            ResponseBody responseBody = response.peekBody(1024 * 1024);
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
//            Logger.e(String.format(Locale.CHINA,"接收响应: [%s] %n返回json: %s \t\n in  %.1fms%n%s",
//                    response.message()+"\t"+response.code(),
//                    responseBody.string(),
//                    (t2 - t1) / 1e6d,
//                    response.headers()));
            Logger.e(String.format(Locale.CHINA, "发送请求: %s on %s%n%s \t\n接收响应: [%s] %n返回json: %s \t\n in  %.1fms%n%s",
                    request.url(),
                    chain.connection(),
                    request.headers(),
                    response.message() + "\t" + response.code(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));
            return response;
        }
    }

}
