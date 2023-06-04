package com.example.engcontext.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history")
    fun getAll(): List<SearchHistory>

    @Query("SELECT * FROM search_history ORDER BY made_at DESC LIMIT 5")
    fun getLast5(): LiveData<List<SearchHistory>>

    @Insert
    fun insertAll(vararg histories: SearchHistory)

    @Delete
    fun delete(history: SearchHistory)
}