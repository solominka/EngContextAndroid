package com.example.engcontext.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engcontext.MainActivity
import com.example.engcontext.adapters.SerpItemAdapter
import com.example.engcontext.clients.GuardianClient
import com.example.engcontext.databinding.SerpFragmentBinding
import com.example.engcontext.db.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SerpFragment : Fragment() {

    private var _binding: SerpFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SerpItemAdapter
    private var guardianClient = GuardianClient()
    private lateinit var query: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SerpFragmentBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(this.context)
        adapter = SerpItemAdapter()
        binding.serpRecyclerView.layoutManager = manager
        binding.serpRecyclerView.adapter = adapter

        query = arguments?.getString("query") ?: savedInstanceState?.getString("query") ?: ""
        adapter.submitData(guardianClient.getSentencesByPhrase(query))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.searchBarTextInput.setText(query)
        binding.searchBar.searchButton.setOnClickListener { onSearchClick() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("query", query)
        super.onSaveInstanceState(outState)
    }

    private fun onSearchClick() {
        query = binding.searchBar.searchBarTextInput.text.toString()

        lifecycleScope.launch(Dispatchers.IO) {
            if (query != "")
                MainActivity.db.searchHistoryDao().insertAll(SearchHistory(query = query))
        }

        adapter.submitData(guardianClient.getSentencesByPhrase(query))

        val inputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        binding.searchBar.searchBarTextInput.clearFocus()
    }
}