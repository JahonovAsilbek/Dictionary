package uz.pdp.dictionary.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.pdp.dictionary.retrofit.ApiHelper
import uz.pdp.dictionary.ui.home.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}