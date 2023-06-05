package com.example.engcontext.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey val query: String,

    @ColumnInfo(name = "made_at")
    val madeAt: Instant = Instant.now(),
)
