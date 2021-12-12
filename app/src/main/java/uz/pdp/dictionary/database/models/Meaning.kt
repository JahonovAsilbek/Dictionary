package uz.pdp.dictionary.database.models

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)