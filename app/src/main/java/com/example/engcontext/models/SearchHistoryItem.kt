package com.example.engcontext.models

import java.time.Instant

data class SearchHistoryItem(
    val query: String,
    val madeAt: Instant = Instant.now(),
)
