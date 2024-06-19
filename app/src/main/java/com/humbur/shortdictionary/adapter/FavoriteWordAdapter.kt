package com.humbur.shortdictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.humbur.shortdictionary.R
import com.humbur.shortdictionary.databinding.ItemAbbrevationBinding
import com.humbur.shortdictionary.databinding.ItemTermAndTranBinding
import com.humbur.shortdictionary.model.Dictionary

class FavoriteWordAdapter(
    private var list: MutableList<Dictionary>, private val setOnClickedListener: SetOnClickedListener
) : RecyclerView.Adapter<FavoriteWordAdapter.DictionaryViewHolder>() {


    companion object {
        private const val TYPE_ABBREVIATION = 1
        private const val TYPE_TERMS_AND_TRANS = 2
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_ABBREVIATION -> AbbreviationHolder(
            ItemAbbrevationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        TYPE_TERMS_AND_TRANS -> TermsAndTranHolder(
            ItemTermAndTranBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val item = list[position]
        when(item.typeA){
            "Sh" ->{
                holder as AbbreviationHolder
                holder.view.apply {
                    txtShortWord.text = item.shortA
                    txtDesc.text = item.eng
                    starter.text = item.typeA
                    imgFovarite.setImageResource(
                        if (item.favorite) R.drawable.ic_star_full
                        else R.drawable.ic_star_empty
                    )
                    imgFovarite.setOnClickListener {
                        list.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                        setOnClickedListener.deleteFavorite(item.id, position)
                    }
                    root.setOnClickListener {
                        setOnClickedListener.itemClicked(position)
                    }
                }

            }
            "Te" ->{
                holder as TermsAndTranHolder
                holder.view.apply {
                    txtShortWord.text = item.eng
                    starter.text = item.typeA
                    imgFovarite.setImageResource(
                        if (item.favorite) R.drawable.ic_star_full
                        else R.drawable.ic_star_empty
                    )
                    imgFovarite.setOnClickListener {
                        list.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                        setOnClickedListener.deleteFavorite(item.id, position)
                    }
                    root.setOnClickListener {
                        setOnClickedListener.itemClicked(position)
                    }
                }

            }
            "Tr" ->{
                holder as TermsAndTranHolder
                holder.view.apply {
                    txtShortWord.text = item.eng
                    starter.text = item.typeA
                    imgFovarite.setImageResource(
                        if (item.favorite) R.drawable.ic_star_full
                        else R.drawable.ic_star_empty
                    )
                    imgFovarite.setOnClickListener {
                        list.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                        setOnClickedListener.deleteFavorite(item.id, position)
                    }
                    root.setOnClickListener {
                        setOnClickedListener.itemClicked(position)
                    }
                }

            }
            else -> throw IllegalArgumentException()
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position].typeA){
            "Sh" -> TYPE_ABBREVIATION
            "Te" -> TYPE_TERMS_AND_TRANS
            "Tr" -> TYPE_TERMS_AND_TRANS
            else -> -1
        }
    }

    sealed class DictionaryViewHolder(view: View): RecyclerView.ViewHolder(view)
    class AbbreviationHolder(val view: ItemAbbrevationBinding): DictionaryViewHolder(view.root)
    class TermsAndTranHolder(val view: ItemTermAndTranBinding): DictionaryViewHolder(view.root)

    override fun getItemCount() = list.size

    interface SetOnClickedListener{
        fun itemClicked(position: Int)
        fun deleteFavorite(id: Int, position: Int)
    }

}