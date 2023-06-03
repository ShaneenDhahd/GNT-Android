package shaneen.dhahd.gnt_test.repo

import shaneen.dhahd.gnt_test.network.BaseNetworkInteractor
import shaneen.dhahd.gnt_test.network.api.GNTApi
import javax.inject.Inject

class FormRepo @Inject constructor(private val api: GNTApi): BaseNetworkInteractor() {
    fun getGovernments() = safeApiCall { api.getGovernments() }
    fun submit(name: String, username: String,gov: String,comment: String,gps: String)
    = safeApiCall {
        api.submit(
            name = name,
            username = username,
            gov = gov,
            comment = comment,
            gps = gps
        )
    }
    fun getForms() = safeApiCall { api.getForms() }
}