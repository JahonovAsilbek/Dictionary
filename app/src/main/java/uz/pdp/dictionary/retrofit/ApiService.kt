package uz.pdp.dictionary.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.pdp.dictionary.database.models.Dictionary

interface ApiService {

    @GET("{word}")
    suspend fun getWord(@Path("word") word: String): Response<List<Dictionary>>
}