package com.example.engcontext.fragments

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engcontext.MainActivity
import com.example.engcontext.R
import com.example.engcontext.adapters.SearchHistoryItemAdapter
import com.example.engcontext.databinding.SearchBarFragmentBinding
import com.example.engcontext.db.SearchHistory
import com.example.engcontext.models.SearchHistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchBarFragment : Fragment() {

    private var _binding: SearchBarFragmentBinding? = null
    private lateinit var adapter: SearchHistoryItemAdapter
    private lateinit var searchHistory: LiveData<List<SearchHistory>>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchBarFragmentBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(this.context)
        adapter = SearchHistoryItemAdapter(binding.searchBarTextInput)
        binding.searchHistoryRecyclerView.layoutManager = manager
        binding.searchHistoryRecyclerView.adapter = adapter

        searchHistory = MainActivity.db.searchHistoryDao().getLast5()
        searchHistory.observe(viewLifecycleOwner) { histories ->
            adapter.submitData(histories.map {
                SearchHistoryItem(
                    query = it.query,
                    madeAt = it.madeAt
                )
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding.searchBarTextInput.setOnFocusChangeListener { _, hasFocus ->
            binding.searchHistoryRecyclerView.visibility = if (hasFocus) View.VISIBLE else View.GONE
        }
        binding.searchButton.setOnClickListener { onSearchClick() }
    }

    private fun onSearchClick() {
        val query = binding.searchBarTextInput.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if (query != "")
                MainActivity.db.searchHistoryDao().insertAll(SearchHistory(query = query))
        }

        findNavController().navigate(
            R.id.action_SearchBarFragment_to_SerpFragment,
            bundleOf("query" to query)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}