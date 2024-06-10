package com.humbur.shortdictionary.ui.search

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.humbur.shortdictionary.databinding.RowItemBinding
import com.humbur.shortdictionary.model.Dictionary

class Adapter(
    private var list: List<Dictionary>, private val setOnClickedListener: SetOnClickedListener
) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtShortWord.text = list[position].short
        holder.binding.txtDesc.text = list[position].full
        holder.binding.imgFovarite.setOnClickListener{
            setOnClickedListener.itemClicked(position)
        }
    }

    override fun getItemCount() = list.size

    interface SetOnClickedListener{
        fun itemClicked(position: Int)
    }

}