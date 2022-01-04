package uz.pdp.dictionary.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import uz.pdp.dictionary.database.AppDatabase
import uz.pdp.dictionary.database.models.Dictionary
import uz.pdp.dictionary.database.models.History
import uz.pdp.dictionary.retrofit.ApiHelper
import uz.pdp.dictionary.utils.Resource


class HomeViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {

    private val words = MutableLiveData<Resource<List<Dictionary>>>()
    private var latestWords = MutableLiveData<List<History>>()
    val getDao = AppDatabase.getDatabase().wordDao()

    fun getWords(word: String): LiveData<Resource<List<Dictionary>>> {
        viewModelScope.launch {
            words.postValue(Resource.loading(null))
            try {
                val wordFromApi = apiHelper.getWord(word)
                words.postValue(Resource.success(wordFromApi))
            } catch (e: Exception) {
                words.postValue(Resource.error(e.toString(), null))
            }
        }
        return words
    }

    fun getLatest(): LiveData<List<History>> {
        viewModelScope.launch() {
            getDao.getAllHistory().distinctUntilChanged().collect {
                latestWords.value = it
                Log.d("AAAA", "getLatest: ${it.size}")
            }
        }
        return latestWords
    }

    fun insertHistory(history: History) {
        viewModelScope.launch {
            getDao.insertHistory(history)
        }
    }

    fun updateHistory(history: History) {
        viewModelScope.launch {
            getDao.updateHistory(history)
        }
    }

}