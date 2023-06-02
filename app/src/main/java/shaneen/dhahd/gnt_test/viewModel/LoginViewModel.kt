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
import shaneen.dhahd.gnt_test.repo.LoginRepo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepo,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _loginObservable = MutableLiveData<ResponseWrapper<LoginModel>>()
    val loginObservable get() = _loginObservable.asLiveData()
    private val TAG = "TAG"
    fun login() {
        viewModelScope.launch {
            loginRepo.login().collect {
                Log.d(TAG, "getDiscover: $it")
                if (it is ResponseWrapper.Success)  {
                    preferencesManager.apply {
                        saveLoginModel(it.value)
                        saveToken(it.value.data.access_token)
                    }
                }
                _loginObservable.postValue(it)
            }
        }
    }
    fun getForms() {
        viewModelScope.launch {
            loginRepo.listData().collect {
                Log.d(TAG, "getDiscover: $it")
            }
        }
    }

}