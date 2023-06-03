package shaneen.dhahd.gnt_test.network.responses

data class Data(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val user: User
)