package com.app.vitrinovaapp.ui.main.products_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.Product
import com.app.vitrinovaapp.databinding.FragmentProductDetailBinding
import com.app.vitrinovaapp.ui.main.MainActivity

class ProductsDetailFragment(private val toolbarTitle: String, private val products: List<Product>) : Fragment(R.layout.fragment_product_detail) {

    private lateinit var productsDetailAdapter: ProductsDetailAdapter
    private lateinit var binding: FragmentProductDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)

        initView()
        initClick()
    }

    private fun initView() {
        binding.run {
            textTitle.text=toolbarTitle
            productsDetailAdapter = ProductsDetailAdapter(products)
            recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            recyclerViewProducts.adapter=productsDetailAdapter
        }
    }

    private fun initClick(){
        binding.run {
            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
        }
    }
}