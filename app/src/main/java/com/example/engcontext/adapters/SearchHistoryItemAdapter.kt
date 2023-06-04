package com.example.engcontext.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.engcontext.MainActivity
import com.example.engcontext.databinding.SearchHistoryItemBinding
import com.example.engcontext.models.SearchHistoryItem
import kotlinx.coroutines.*

class SearchHistoryItemAdapter :
    RecyclerView.Adapter<SearchHistoryItemAdapter.SearchHistoryItemViewHolder>() {

    private var data: List<SearchHistoryItem> = listOf()
    private var binding: SearchHistoryItemBinding? = null

    class SearchHistoryItemViewHolder(val binding: SearchHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = SearchHistoryItemBinding.inflate(inflater, parent, false)

        binding!!.deleteSearchHistoryButton.setOnClickListener {
            deleteSearchHistory()
        }

        return SearchHistoryItemViewHolder(binding!!)
    }

    private fun deleteSearchHistory() {
        val query = binding!!.searchHistoryText.text.toString()
        GlobalScope.launch(Dispatchers.IO) {
            MainActivity.db.searchHistoryDao().deleteByQuery(query)
        }.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchHistoryItemViewHolder, position: Int) {
        val searchHistory = data[position]

        with(holder.binding) {
            searchHistoryText.text = searchHistory.query
        }
    }

    fun submitData(newData: List<SearchHistoryItem>) {
        data = newData
        notifyDataSetChanged()
    }
}
