package com.humbur.shortdictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.RowItemBinding
import com.humbur.shortdictionary.model.Dictionary

class WordAdapter(
    private var list: List<Dictionary>, private val setOnClickedListener: SetOnClickedListener
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.txtShortWord.text = short
                binding.txtDesc.text = full
                binding.imgFovarite.setImageResource(
                    if (favorite) R.drawable.ic_star_full
                    else R.drawable.ic_star_empty
                )
                binding.imgFovarite.setOnClickListener {
                    if (id != null) {
                        favorite = !favorite
                        notifyItemChanged(position)
                        setOnClickedListener.addFavorite(id, favorite)
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            setOnClickedListener.itemClicked(position)
        }

    }

    override fun getItemCount() = list.size

    interface SetOnClickedListener{
        fun itemClicked(position: Int)
        fun addFavorite(id: Int, state:Boolean)
    }

}