package guru.maze.avatar.net;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;
import guru.maze.avatar.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by xiaok
 * @date 2022/10/31
 */
public class RetrofitFactory {

    private static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        //产生卡顿的原因应该是Retrofit初始化问题！！
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    // Cookie 持久化
//                    CookieJarImpl cookieJar = new CookieJarImpl(new MyPersistentCookieStore(App.getIns()));

//                     自定义一个信任所有证书的TrustManager
                    final X509TrustManager trustManager = new X509TrustManager() {
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
                    };
                    final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustManager);
//                    X509TrustManager trustManager;
//                    SSLSocketFactory sslSocketFactory;
//                    try {
//                        trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
//                        SSLContext sslContext = SSLContext.getInstance("TLS");
//                        sslContext.init(null, new TrustManager[]{trustManager}, null);
//                        sslSocketFactory = sslContext.getSocketFactory();
//                    } catch (GeneralSecurityException e) {
//                        throw new RuntimeException(e);
//                    }

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                                                           .cookieJar(cookieJar)
                                                           .sslSocketFactory(sslSocketFactory, trustManager).addInterceptor(new AddHeaderInterceptor()).addInterceptor(new AddResponseInterceptor()).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true);

                    // Log 拦截器

                    retrofit = new Retrofit.Builder()
                                       //TODO
                                       .baseUrl(BuildConfig.Base_Url).client(builder.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();

                }
            }
        }
        return retrofit;
    }



}
