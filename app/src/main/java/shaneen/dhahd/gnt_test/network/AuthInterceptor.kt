package shaneen.dhahd.gnt_test.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import shaneen.dhahd.gnt_test.di.PreferencesManager
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val token = preferencesManager.getToken()
        val newRequest: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}
