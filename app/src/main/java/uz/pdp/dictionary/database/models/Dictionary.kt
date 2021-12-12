package uz.pdp.dictionary.database.models

data class Dictionary(
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val word: String
)