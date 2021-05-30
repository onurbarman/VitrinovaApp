package com.app.vitrinovaapp.ui.main.collection_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.Collections
import com.app.vitrinovaapp.databinding.FragmentCollectionsDetailBinding
import com.app.vitrinovaapp.ui.main.MainActivity

class CollectionsDetailFragment(private val toolbarTitle: String, private val collections: List<Collections>) : Fragment(R.layout.fragment_collections_detail) {

    private lateinit var collectionsDetailAdapter: CollectionsDetailAdapter
    private lateinit var binding: FragmentCollectionsDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCollectionsDetailBinding.bind(view)

        initView()
        initClick()
    }

    private fun initView() {
        binding.run {
            textTitle.text=toolbarTitle
            collectionsDetailAdapter = CollectionsDetailAdapter(collections)
            recyclerViewCollections.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,false)
            recyclerViewCollections.adapter=collectionsDetailAdapter
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