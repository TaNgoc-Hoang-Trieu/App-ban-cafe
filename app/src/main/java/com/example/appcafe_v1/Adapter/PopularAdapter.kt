package com.example.appcafe_v1.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.appcafe_v1.Activity.DetailActivity
import com.example.appcafe_v1.Model.ItemsModel
import com.example.appcafe_v1.databinding.ViewholderPopularBinding

class PopularAdapter(var items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>( ) {

    private var context: Context? = null

    class ViewHolder(var binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context= parent.context
        var binding= ViewholderPopularBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleTxt.text= items[position].title
        holder.binding.priceTxt.text= "$"+items[position].price.toString()
        holder.binding.ratingBar.rating= items[position].rating.toFloat()
        holder.binding.extraTxt.text= items[position].extra

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}