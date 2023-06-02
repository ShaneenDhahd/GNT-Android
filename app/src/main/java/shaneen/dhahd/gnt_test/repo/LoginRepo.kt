package shaneen.dhahd.gnt_test.repo

import shaneen.dhahd.gnt_test.network.BaseNetworkInteractor
import shaneen.dhahd.gnt_test.network.api.GNTApi
import javax.inject.Inject

class LoginRepo@Inject constructor(private val api: GNTApi): BaseNetworkInteractor() {
    fun login() = safeApiCall {
        api.login("test@giganet.iq", "QLoU7XYVcYUNOQIm8z")
    }
    fun listData() = safeApiCall {
        api.getForms()
    }
}