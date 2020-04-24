package john.stemberger.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteSources {
    companion object {
        fun getRedditSource(): RedditApi {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RedditApi::class.java)
        }
    }
}