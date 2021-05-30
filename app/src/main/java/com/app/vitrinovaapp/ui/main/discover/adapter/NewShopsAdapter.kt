package com.app.vitrinovaapp.ui.main.discover.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.utils.GlideUtils
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.Shop


class NewShopsAdapter(private var newShops: List<Shop>)
    : RecyclerView.Adapter<NewShopsAdapter.NewShopsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewShopsViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.item_new_shops, parent, false)

        return NewShopsViewHolder(view)
    }
    override fun getItemCount(): Int {
        return newShops.size
    }

    override fun onBindViewHolder(holder: NewShopsViewHolder, position: Int) {
        holder.bind(newShops[position])
    }

    fun updateNewShops(newShops: List<Shop>) {
        this.newShops = newShops
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.newShops = listOf()
        notifyDataSetChanged()
    }

    inner class NewShopsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgShop : ImageView = itemView.findViewById(R.id.imgShop)
        private val imgLogo : ImageView = itemView.findViewById(R.id.imgLogo)
        private val textTitle : TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription : TextView = itemView.findViewById(R.id.textDescription)
        private val textProductCount : TextView = itemView.findViewById(R.id.textProductCount)

        @SuppressLint("SetTextI18n")
        fun bind(newShop: Shop) {
            itemView.animation = AnimationUtils.loadAnimation(itemView.context,R.anim.anim_horizontal_recyclerview)

            newShop.logo?.let {
                GlideUtils.urlToImageView(imgLogo.context,it.url,imgLogo)
            }
            newShop.cover?.let {
                GlideUtils.urlToImageView(imgShop.context,it.url,imgShop)
            }
            textTitle.text=newShop.name
            textDescription.text=newShop.definition
            textProductCount.text=newShop.product_count.toString()+" ÜRÜN"
        }
    }
}