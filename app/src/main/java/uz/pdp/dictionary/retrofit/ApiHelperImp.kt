package uz.pdp.dictionary.retrofit

import uz.pdp.dictionary.database.models.Dictionary

class ApiHelperImp(private val apiService: ApiService) : ApiHelper {

    override suspend fun getWord(word: String): List<Dictionary> = apiService.getWord(word)

}