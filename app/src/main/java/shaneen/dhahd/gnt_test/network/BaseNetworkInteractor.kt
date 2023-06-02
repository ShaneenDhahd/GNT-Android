package shaneen.dhahd.gnt_test.network

import android.util.Log
import shaneen.dhahd.gnt_test.network.wrappers.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import shaneen.dhahd.gnt_test.R

open class BaseNetworkInteractor {

    fun <T> safeApiCall(request: (suspend () -> Response<T>)) = flow {
        //show loading state
        emit(ResponseWrapper.Loading)


        kotlin.runCatching {
            request.invoke()
        }.onSuccess {
            Log.d("NetworkResponse", "response = $it")

            if (it.isSuccessful) {
                emit(ResponseWrapper.Success(it.body()!!))
            } else {
                emit(ResponseWrapper.Failure(it.message()))
            }
        }.onFailure {
            Log.d("NetworkResponse", "response 1 = $it")
            val errorMsg = catchException(it)
            emit(ResponseWrapper.LocalFailure(errorMsg))
        }
    }.flowOn(Dispatchers.IO)

    private fun catchException(throwable: Throwable) = when (throwable) {
        is HttpException -> R.string.error_internet_failure
        else -> R.string.error_service_failure
    }
}
