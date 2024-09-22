package com.bijesh.coroutine.ui.view.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bijesh.coroutine.R
import com.bijesh.coroutine.ui.view.recycler_view_adapter.model.Item

class StickyHeaderAdapter(private val items:List<Item>) : RecyclerView.Adapter<StickyHeaderAdapter.StickyHeaderViewHolder>() {


    inner class StickyHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyHeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sticky_header,parent,false)
        return StickyHeaderViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StickyHeaderViewHolder, position: Int) {
        val item = items[position]
        holder.itemTextView.text = item.title
        holder.descriptionTextView.text = item.description
    }
}