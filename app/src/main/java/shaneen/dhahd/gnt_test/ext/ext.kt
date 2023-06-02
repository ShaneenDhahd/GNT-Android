package shaneen.dhahd.gnt_test.ext

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import shaneen.dhahd.gnt_test.network.responses.LoginModel

fun Context.storeUserInformation(userInformation: LoginModel?) {
    if (userInformation != null) {
        this.getSharedPreferences("user_information", MODE_PRIVATE)
            ?.edit()?.putString("userData", Gson().toJson(userInformation))?.apply()
    }
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
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun Context?.clearUserInformation() {
    val sharedPreferences: SharedPreferences? =
        this?.getSharedPreferences("user_information", MODE_PRIVATE)
    val editor = sharedPreferences?.edit()
    editor?.clear()?.apply()
}