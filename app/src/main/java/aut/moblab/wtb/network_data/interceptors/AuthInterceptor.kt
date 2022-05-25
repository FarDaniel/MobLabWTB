package aut.moblab.wtb.network_data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.url(
            chain.request().url.toString().replace("%7Bapi_key%7D", "k_jqru4qvo")
        )
        return chain.proceed(requestBuilder.build())
    }
}