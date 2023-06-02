package com.example.engcontext.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engcontext.adapters.SerpItemAdapter
import com.example.engcontext.clients.GuardianClient
import com.example.engcontext.databinding.SerpFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SerpFragment : Fragment() {

    private var _binding: SerpFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SerpItemAdapter
    private var guardianClient = GuardianClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SerpFragmentBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(this.context)
        adapter = SerpItemAdapter()
        binding.serpRecyclerView.layoutManager = manager
        binding.serpRecyclerView.adapter = adapter

        val query = arguments?.getString("query") ?: ""
        adapter.data = guardianClient.getSentencesByPhrase(query)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}