package uz.pdp.dictionary.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class History {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var word: String? = null
    var phonetic: String? = null
}