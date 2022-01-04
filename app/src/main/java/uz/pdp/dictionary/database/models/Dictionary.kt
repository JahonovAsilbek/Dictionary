package uz.pdp.dictionary.database.models

data class Dictionary(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)