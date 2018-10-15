package org.andrekot.rustoenggifsearcher.view

/*Created by Andrekot on 16/10/18*/

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.andrekot.rustoenggifsearcher.R
import org.andrekot.rustoenggifsearcher.databinding.ItemBinding
import org.andrekot.rustoenggifsearcher.model.GifResult
import org.andrekot.rustoenggifsearcher.viewmodel.ItemViewModel

class GifAdapter() : RecyclerView.Adapter<GifAdapterViewHolder>() {

    private var gifList: List<GifResult>? = null

    init {
        this.gifList = emptyList<GifResult>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifAdapterViewHolder {
        val itemGifBinding = DataBindingUtil.inflate<ItemBinding>(LayoutInflater.from(parent.context), R.layout.item,
                parent, false)
        return GifAdapterViewHolder(itemGifBinding)
    }

    override fun onBindViewHolder(holder: GifAdapterViewHolder, position: Int) {
        holder.bindGif(gifList!![position])
    }

    override fun getItemCount(): Int {
        return gifList!!.size
    }

    internal fun setGifList(gifList: List<GifResult>) {
        this.gifList = gifList
        notifyDataSetChanged()
    }
}

class GifAdapterViewHolder(var mItemGifBinding: ItemBinding) : RecyclerView.ViewHolder(mItemGifBinding.itemGif) {

    fun bindGif(gifResult: GifResult) {
        if (mItemGifBinding.gifViewModel == null) {
            mItemGifBinding.gifViewModel = ItemViewModel.create(gifResult, itemView.context)
        } else {
            (mItemGifBinding.gifViewModel as ItemViewModel).setGifResult(gifResult)
        }
    }
}