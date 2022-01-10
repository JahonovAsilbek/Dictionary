package uz.pdp.dictionary.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.pdp.dictionary.database.models.History

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History)

    @Query("select * from history")
    fun getAllHistory(): Flow<List<History>>

    @Query("select * from history where saved=1")
    fun getAllSaved(): Flow<List<History>>

    @Update
    suspend fun updateHistory(history: History)

    @Query("delete from history where word=:word")
    suspend fun deleteHistory(word: String)

}