package com.jpmc.szabolcs.viewmodel


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.jpmc.szabolcs.R
import com.jpmc.szabolcs.model.DatabaseAlbum
import com.jpmc.szabolcs.databinding.AlbumItemBinding

class AlbumAdapter : RecyclerView.Adapter<AlbumViewHolder>() {

    var result: List<DatabaseAlbum> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumViewHolder(withDataBinding)
    }


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also{
            it.result = result[position]
        }
    }

    override fun getItemCount() = result.size
}

class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.album_item
    }



}
