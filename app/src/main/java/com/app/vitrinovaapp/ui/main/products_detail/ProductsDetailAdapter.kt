package com.app.vitrinovaapp.ui.main.products_detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.utils.GlideUtils
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.Product


class ProductsDetailAdapter(private var products: List<Product>)
    : RecyclerView.Adapter<ProductsDetailAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater .from(parent.context)
            .inflate(R.layout.item_product_detail, parent, false)

        return ProductsViewHolder(view)
    }
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        this.products = listOf()
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProduct : ImageView = itemView.findViewById(R.id.imgProduct)
        private val textTitle : TextView = itemView.findViewById(R.id.textTitle)
        private val textShop : TextView = itemView.findViewById(R.id.textShop)
        private val textPrice : TextView = itemView.findViewById(R.id.textPrice)
        private val textOldPrice : TextView = itemView.findViewById(R.id.textOldPrice)
        private val cardFastShipment : CardView = itemView.findViewById(R.id.cardFastShipment)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            itemView.animation= AnimationUtils.loadAnimation(itemView.context,R.anim.anim_vertical_recyclerview)

            GlideUtils.urlToImageView(imgProduct.context,product.images[0].url,imgProduct)
            if (product.cargo_time==1)
                cardFastShipment.visibility=View.VISIBLE

            textTitle.text=product.title
            textShop.text=product.shop.name
            textPrice.text=product.price.toString()+" TL"
            product.old_price?.let {
                textOldPrice.visibility=View.VISIBLE
                textOldPrice.text=it.toString()+" TL"
                textOldPrice.paintFlags = textOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
}