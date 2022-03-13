package com.uho.artbookkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.databinding.AdapterArtRowBinding
import com.uho.artbookkotlin.db.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ViewHolder>() {

    class ViewHolder(var view: AdapterArtRowBinding) : RecyclerView.ViewHolder(view.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<AdapterArtRowBinding>(inflater,R.layout.adapter_art_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.art = arts[position]
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}