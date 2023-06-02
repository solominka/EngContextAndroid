package com.example.engcontext.clients

import com.example.engcontext.models.GuardianResponse
import com.example.engcontext.models.SerpItem
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class GuardianClient {
    private val httpClient = OkHttpClient()
    private val gson = Gson()

    fun getSentencesByPhrase(query: String): List<SerpItem> {
        val url = url.toHttpUrl().newBuilder()
            .addQueryParameter("q", "\"$query\"")
            .addQueryParameter("api-key", apiKey)
            .build()

        val request = Request.Builder().url(url).build()
        val respBody = httpClient.newCall(request).execute().body.string()
        val parsedResp = gson.fromJson(respBody, GuardianResponse::class.java)
        return parsedResp.response.results.map {
            SerpItem(text = it.webTitle, source = "The Guardinan", url = it.webUrl)
        }
    }

    companion object {
        private const val apiKey = "e38cdc2c-e0f7-4fd5-88e5-15ece3d535f5"
        private const val url = "https://content.guardianapis.com/search?"
    }
}