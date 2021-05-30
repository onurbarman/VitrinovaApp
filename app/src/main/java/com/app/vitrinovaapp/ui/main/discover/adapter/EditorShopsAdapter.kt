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


class EditorShopsAdapter(private var editorShops: List<Shop>)
    : RecyclerView.Adapter<EditorShopsAdapter.EditorShopsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditorShopsViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.item_editor_shops, parent, false)

        return EditorShopsViewHolder(view)
    }
    override fun getItemCount(): Int {
        return editorShops.size
    }

    override fun onBindViewHolder(holder: EditorShopsViewHolder, position: Int) {
        holder.bind(editorShops[position])
    }

    fun updateEditorShops(editorShops: List<Shop>) {
        this.editorShops = editorShops
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.editorShops = listOf()
        notifyDataSetChanged()
    }

    inner class EditorShopsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgLogo : ImageView = itemView.findViewById(R.id.imgLogo)
        private val imgShop1 : ImageView = itemView.findViewById(R.id.imgShop1)
        private val imgShop2 : ImageView = itemView.findViewById(R.id.imgShop2)
        private val imgShop3 : ImageView = itemView.findViewById(R.id.imgShop3)
        private val textTitle : TextView = itemView.findViewById(R.id.textTitle)
        private val textShop : TextView = itemView.findViewById(R.id.textShop)

        @SuppressLint("SetTextI18n")
        fun bind(editorShop: Shop) {
            itemView.animation= AnimationUtils.loadAnimation(itemView.context,R.anim.anim_horizontal_recyclerview)

            editorShop.logo?.let { cover->
                GlideUtils.urlToImageView(imgLogo.context,cover.url,imgLogo)
            }
            GlideUtils.urlToImageView(imgShop1.context,editorShop.popular_products[0].images[0].url,imgShop1)
            GlideUtils.urlToImageView(imgShop2.context,editorShop.popular_products[1].images[0].url,imgShop2)
            GlideUtils.urlToImageView(imgShop3.context,editorShop.popular_products[2].images[0].url,imgShop3)
            textTitle.text=editorShop.name
            textShop.text=editorShop.definition
        }
    }
}