package uz.pdp.dictionary.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val BASE_URl = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    var apiService: ApiService = getRetrofit().create(ApiService::class.java)
}