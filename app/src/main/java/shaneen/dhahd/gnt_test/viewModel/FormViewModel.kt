package shaneen.dhahd.gnt_test.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import shaneen.dhahd.gnt_test.ext.asLiveData
import shaneen.dhahd.gnt_test.network.responses.FormModel
import shaneen.dhahd.gnt_test.network.responses.FormsModel
import shaneen.dhahd.gnt_test.network.responses.GovernmentsModel
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import shaneen.dhahd.gnt_test.repo.FormRepo
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val formRepo: FormRepo,
) : ViewModel() {

    private val _governmentsObservable = MutableLiveData<ResponseWrapper<GovernmentsModel>>()
    private val _formObservable = MutableLiveData<ResponseWrapper<FormModel>>()
    private val _formsObservable = MutableLiveData<ResponseWrapper<FormsModel>>()
    val governmentsObservable get() = _governmentsObservable.asLiveData()
    val formsObservable get() = _formsObservable.asLiveData()
    val formObservable get() = _formObservable.asLiveData()
    fun getGovs() {
        viewModelScope.launch {
            formRepo.getGovernments().collect {
                _governmentsObservable.postValue(it)
            }
        }
    }
    fun submit(name: String, username: String,gov: String,comment: String,gps: String) {
        viewModelScope.launch {
            formRepo.submit(
                name = name,
                username = username,
                gov = gov,
                comment = comment,
                gps = gps
            ).collect {
                _formObservable.postValue(it)
            }
        }
    }
    fun getForms() {
        viewModelScope.launch {
            formRepo.getForms().collect {
                _formsObservable.postValue(it)
            }
        }
    }
}