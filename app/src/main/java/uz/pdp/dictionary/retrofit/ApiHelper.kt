package uz.pdp.dictionary.retrofit

import uz.pdp.dictionary.database.models.Dictionary

interface ApiHelper {
    suspend fun getWord(word:String):List<Dictionary>
}