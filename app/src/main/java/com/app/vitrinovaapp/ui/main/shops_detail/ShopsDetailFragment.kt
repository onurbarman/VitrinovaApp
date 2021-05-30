package com.app.vitrinovaapp.ui.main.shops_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.Product
import com.app.vitrinovaapp.data.model.discover.Shop
import com.app.vitrinovaapp.databinding.FragmentCollectionsDetailBinding
import com.app.vitrinovaapp.databinding.FragmentShopsDetailBinding
import com.app.vitrinovaapp.ui.main.MainActivity
import com.app.vitrinovaapp.ui.main.collection_detail.CollectionsDetailAdapter

class ShopsDetailFragment(private val toolbarTitle: String, private val shops: List<Shop>) : Fragment(R.layout.fragment_shops_detail) {

    private lateinit var shopsDetailAdapter: ShopsDetailAdapter
    private lateinit var binding: FragmentShopsDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShopsDetailBinding.bind(view)

        initView()
        initClick()
    }

    private fun initView() {
        binding.run {
            textTitle.text=toolbarTitle
            shopsDetailAdapter = ShopsDetailAdapter(shops)
            recyclerViewShops.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,false)
            recyclerViewShops.adapter=shopsDetailAdapter
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