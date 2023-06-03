package shaneen.dhahd.gnt_test.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import shaneen.dhahd.gnt_test.di.PreferencesManager
import shaneen.dhahd.gnt_test.ext.asLiveData
import shaneen.dhahd.gnt_test.network.responses.LoginModel
import shaneen.dhahd.gnt_test.network.responses.LogoutMsg
import shaneen.dhahd.gnt_test.repo.LoginRepo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _loginObservable = MutableLiveData<ResponseWrapper<LoginModel>>()
    private val _logoutObservable = MutableLiveData<ResponseWrapper<LogoutMsg>>()
    val logoutObservable get() = _loginObservable.asLiveData()
    val loginObservable get() = _loginObservable.asLiveData()
    private val TAG = "TAG"
    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepo.login(email, password).collect {
                Log.d(TAG, "getDiscover: $it")
                if (it is ResponseWrapper.Success) {
                    preferencesManager.apply {
                        saveLoginModel(it.value)
                        saveToken(it.value.data.access_token)
                    }
                }
                _loginObservable.postValue(it)
            }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            loginRepo.refreshToken().collect {
                Log.d(TAG, "getDiscover: $it")
                if (it is ResponseWrapper.Success) {
                    preferencesManager.apply {
                        saveLoginModel(it.value)
                        saveToken(it.value.data.access_token)
                    }
                }
                _loginObservable.postValue(it)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginRepo.logout().collect {
                _logoutObservable.postValue(it)
            }
        }
    }

}