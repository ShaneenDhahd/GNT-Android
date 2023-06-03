package shaneen.dhahd.gnt_test.ext

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import shaneen.dhahd.gnt_test.network.responses.LoginModel

fun Context.storeUserInformation(userInformation: LoginModel?) {
    if (userInformation != null) {
        this.getSharedPreferences("user_information", MODE_PRIVATE)
            ?.edit()?.putString("userData", Gson().toJson(userInformation))?.apply()
    }
}
fun Context?.toast(msg: String?, long: Boolean = true) {
    if (long) Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    else Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
fun Context.getUserInformation(): LoginModel? {
    return try {
        Gson().fromJson(
            this.getSharedPreferences("user_information", MODE_PRIVATE)?.getString("userData", "")
            , LoginModel::class.java
        )
    } catch (exception: Exception) {
        null
    }
}

fun EditText.isEmpty() = this.text.toString().isEmpty()

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun Context?.clearUserInformation() {
    val sharedPreferences: SharedPreferences? =
        this?.getSharedPreferences("user_information", MODE_PRIVATE)
    val editor = sharedPreferences?.edit()
    editor?.clear()?.apply()
}
fun NavController.navigateTo(id: Int, finish: Boolean = false) {
    if (finish) this.popBackStack()
    this.navigate(id)
}
fun Fragment.dismiss() {
    findNavController().popBackStack()
}