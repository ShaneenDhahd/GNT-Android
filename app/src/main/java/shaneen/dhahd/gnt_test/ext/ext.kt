package shaneen.dhahd.gnt_test.ext

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import shaneen.dhahd.gnt_test.network.responses.LoginModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

fun ViewGroup.inflateRvItem(@LayoutRes itemRes: Int): View {
    return LayoutInflater.from(this.context)
        .inflate(itemRes, this, false)
}

fun String.formatDate(): String {
    return this.split(" ").first().toString()
}

fun <T : RecyclerView.Adapter<*>> RecyclerView.init(
    adp: T,
    isHorizontal: Boolean = false,
    layoutManager: LinearLayoutManager? = null
) {
    val orientation = if (isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    val lm = layoutManager
        ?: LinearLayoutManager(context, orientation, false)

    this.adapter = adp
    this.layoutManager = lm
    this.setHasFixedSize(true)
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