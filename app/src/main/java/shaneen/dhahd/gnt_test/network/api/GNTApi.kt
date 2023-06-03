package shaneen.dhahd.gnt_test.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import shaneen.dhahd.gnt_test.network.BaseUrls.GetGovernments
import shaneen.dhahd.gnt_test.network.BaseUrls.ListData
import shaneen.dhahd.gnt_test.network.BaseUrls.Login
import shaneen.dhahd.gnt_test.network.BaseUrls.Refresh
import shaneen.dhahd.gnt_test.network.BaseUrls.Submit
import shaneen.dhahd.gnt_test.network.responses.FormModel
import shaneen.dhahd.gnt_test.network.responses.FormsModel
import shaneen.dhahd.gnt_test.network.responses.GovernmentsModel
import shaneen.dhahd.gnt_test.network.responses.LoginModel

interface GNTApi {

    @GET(GetGovernments)
    suspend fun getGovernments(
    ): Response<GovernmentsModel>
    @POST(Refresh)
    suspend fun refreshToken(
    ): Response<LoginModel>

    @POST(Login)
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<LoginModel>

    @POST(ListData)
    suspend fun getForms(
    ): Response<FormsModel>

    @POST(Submit)
    suspend fun submit(
        @Query("name") name: String,
        @Query("username") username: String,
        @Query("gov") gov: String,
        @Query("comment") comment: String,
        @Query("gps") gps: String
    ): Response<FormModel>

}