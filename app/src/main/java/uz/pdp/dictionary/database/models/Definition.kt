package uz.pdp.dictionary.database.models

data class Definition(
    val definition: String,
    val example: String,
    val synonyms: List<String>
)