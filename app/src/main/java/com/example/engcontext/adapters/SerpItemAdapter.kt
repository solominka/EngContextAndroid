package com.example.engcontext.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.engcontext.databinding.SerpItemBinding
import com.example.engcontext.models.SerpItem

class SerpItemAdapter : RecyclerView.Adapter<SerpItemAdapter.SerpItemViewHolder>() {

    var data: List<SerpItem> = listOf(
        SerpItem(
            text = "The sixth Delhi is the Purana Qila, or Old Fort, a 16th-century stone fort near the eastern edge of the city, and a particularly good place from which to tell the story of Delhi's urban development",
            source = "The Guardian",
            url = "",
        ),
        SerpItem(
            text = "The sixth Delhi is the Purana Qila, or Old Fort, a 16th-century stone fort near the eastern edge of the city, and a particularly good place from which to tell the story of Delhi's urban development",
            source = "The Guardian",
            url = "",
        ),
        SerpItem(
            text = "The sixth Delhi is the Purana Qila, or Old Fort, a 16th-century stone fort near the eastern edge of the city, and a particularly good place from which to tell the story of Delhi's urban development",
            source = "The Guardian",
            url = "",
        )
    )

    class SerpItemViewHolder(val binding: SerpItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerpItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SerpItemBinding.inflate(inflater, parent, false)

        return SerpItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SerpItemViewHolder, position: Int) {
        val serpItem = data[position]

        with(holder.binding) {
            serpItemText.text = serpItem.text
            serpItemSource.text = serpItem.source
        }
    }

    fun submitData(newData: List<SerpItem>) {
        data = newData
        notifyDataSetChanged()
    }
}
