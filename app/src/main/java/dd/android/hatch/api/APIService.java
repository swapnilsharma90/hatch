package dd.android.hatch.api;

import android.util.Log;

import java.io.IOException;

import dd.android.hatch.BuildConfig;
import dd.android.hatch.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by swapsharma on 4/7/16.
 */
public class APIService {

    private static final String LOG_TAG = APIService.class.getSimpleName();
    private static final String LOG_TAG_REQUEST = LOG_TAG + ".Request";

    private APIService() {
        //empty cons
    }
      /* SERVER API SERVICE */

    /**
     * Method for creating the {@link Retrofit} instance using the {@link WikiApi} value.
     * <p/>
     * and utilising  the {@link RxJavaCallAdapterFactory}   instead of {@link GsonConverterFactory}
     * to support rxjava concepts in this wiki app
     */
    public static WikiApi createWikiApi() {
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                logRequest(request);

                return chain.proceed(request);
            }
        }).build();
        builder.client(client);
        return builder.build().create(WikiApi.class);
    }


    /*
     * for logging the Request
     *
     */
    private static void logRequest(Request request) {
        if (BuildConfig.DEBUG) {
            StringBuilder b = new StringBuilder()
                    .append("Request:")
                    .append(" method is: ").append(request.method())
                    .append(" urlis:").append(request.url())
                    .append(" header is :").append(request.headers());
            if (request.body() != null) {
                b.append(" body is :").append(convertRequestToString(request));
            }
            Log.d(LOG_TAG_REQUEST, b.toString());
        }
    }


    private static String convertRequestToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "error";
        }
    }
}