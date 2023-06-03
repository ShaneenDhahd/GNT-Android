package shaneen.dhahd.gnt_test.network.responses


import com.google.gson.annotations.SerializedName

data class FormsModel(
    @SerializedName("data")
    val FormData: List<DataX>,
    @SerializedName("message")
    val message: String
)