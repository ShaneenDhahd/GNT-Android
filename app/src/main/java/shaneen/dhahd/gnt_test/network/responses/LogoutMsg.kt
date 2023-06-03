package shaneen.dhahd.gnt_test.network.responses


import com.google.gson.annotations.SerializedName

data class LogoutMsg(
    @SerializedName("message")
    val message: String
)