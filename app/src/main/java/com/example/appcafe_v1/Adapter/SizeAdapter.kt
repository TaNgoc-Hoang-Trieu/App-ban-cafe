package com.example.appcafe_v1.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcafe_v1.R
import com.example.appcafe_v1.databinding.ViewholderSizeBinding


class SizeAdapter(val items: MutableList<String>): RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    inner class ViewHolder(val binding: ViewholderSizeBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if(selectedPosition == position) {
            holder.binding.img.setBackgroundResource(R.drawable.orange_bg)
        } else {
            holder.binding.img.setBackgroundResource(R.drawable.size_bg)
        }
        val imageSize = when(position){
            0->45.toPx(context)
            1->50.toPx(context)
            2->55.toPx(context)
            3->65.toPx(context)
            else->70.toPx(context)
        }
        val layoutParams = holder.binding.img.layoutParams
        layoutParams.width = imageSize
        layoutParams.height = imageSize
        holder.binding.img.layoutParams = layoutParams
    }
    private fun Int.toPx(context: Context): Int {
            return (this * context.resources.displayMetrics.density).toInt()

    }
    override fun getItemCount(): Int = items.size
}