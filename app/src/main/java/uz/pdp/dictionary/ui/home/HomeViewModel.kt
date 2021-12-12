package uz.pdp.dictionary.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.pdp.dictionary.database.models.Dictionary
import uz.pdp.dictionary.retrofit.ApiClient


class HomeViewModel : ViewModel() {

    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.Default + job

    private val liveData = MutableLiveData<List<Dictionary>>()

    fun fetchWord(word: String): LiveData<List<Dictionary>> {
        viewModelScope.launch(coroutineContext) {
            val response = ApiClient.apiService.getWord(word)
            if (response.isSuccessful) {
                liveData.postValue(response.body())
//                Log.d("AAAA", "fetchWord: ${response.body().toString()}")
            }
        }
        return liveData
    }
}