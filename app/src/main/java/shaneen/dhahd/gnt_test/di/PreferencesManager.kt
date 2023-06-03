package shaneen.dhahd.gnt_test.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import shaneen.dhahd.gnt_test.network.responses.LoginModel

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {
    private val PREFS_NAME = "MyPrefs"
    private val gson = Gson()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLoginModel(loginModel: LoginModel) {
        val json = gson.toJson(loginModel)
        sharedPreferences.edit().putString("loginModel", json).apply()
    }

    fun getLoginModel(): LoginModel? {
        val json = sharedPreferences.getString("loginModel", null)
        return gson.fromJson(json, LoginModel::class.java)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearAll(){
        sharedPreferences.edit().clear().apply()
    }

    // Add any other necessary methods for your preferences
}

