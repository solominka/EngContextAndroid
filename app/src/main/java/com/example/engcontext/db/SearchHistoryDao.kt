package com.example.engcontext.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history")
    fun getAll(): List<SearchHistory>

    @Query("SELECT * FROM search_history ORDER BY made_at DESC LIMIT 5")
    fun getLast5(): LiveData<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg histories: SearchHistory)

    @Query("DELETE FROM search_history WHERE query = :query")
    fun deleteByQuery(query: String)
}