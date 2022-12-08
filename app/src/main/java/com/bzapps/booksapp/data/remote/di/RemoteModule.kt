package com.bzapps.booksapp.data.remote.di

import android.content.Context
import com.bzapps.booksapp.R
import com.bzapps.booksapp.data.remote.api.IBookAPI
import com.bzapps.booksapp.data.remote.serivce.CoreService
import com.bzapps.booksapp.data.remote.serivce.ICoreService
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    factory { providesOkHttpClient(get()) }
    single { provideMoshi() }
    single {
        createService<IBookAPI>(
            okHttpClient = get(),
            url = provideBaseUrl(get())
        )
    }

    single { provideInterceptor() }
    factory<ICoreService> { CoreService(get()) }
}

fun provideBaseUrl(context: Context): String {
    return context.getString(R.string.base_url)
}

fun provideInterceptor(): Interceptor {
    return Interceptor { chain: Interceptor.Chain ->
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }
}

fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

internal fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
}

inline fun <reified T> createService(okHttpClient: OkHttpClient, url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(T::class.java)
}