package shaneen.dhahd.gnt_test.network.responses

data class FormModel(
    val `data`: DataX,
    val message: String
)
data class FormsModel(
    val `data`: ArrayList<DataX>,
    val message: String
)