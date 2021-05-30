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
import com.app.vitrinovaapp.data.model.discover.Category


class CategoriesAdapter(private var categories: List<Category>)
    : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.item_categories, parent, false)

        return CategoriesViewHolder(view)
    }
    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun updateCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.categories = listOf()
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory : ImageView = itemView.findViewById(R.id.imgCategory)
        private val textCategory : TextView = itemView.findViewById(R.id.textCategory)

        @SuppressLint("SetTextI18n")
        fun bind(category: Category) {
            itemView.animation= AnimationUtils.loadAnimation(itemView.context,R.anim.anim_horizontal_recyclerview)

            GlideUtils.urlToImageView(imgCategory.context,category.cover.url,imgCategory)
            textCategory.text=category.name
        }
    }
}