package shaneen.dhahd.gnt_test.repo

import shaneen.dhahd.gnt_test.network.BaseNetworkInteractor
import shaneen.dhahd.gnt_test.network.api.GNTApi
import javax.inject.Inject

class LoginRepo@Inject constructor(private val api: GNTApi): BaseNetworkInteractor() {
    fun login(email: String, password: String) = safeApiCall {
        api.login(email, password)
    }
    fun refreshToken() = safeApiCall {
        api.refreshToken()
    }

}