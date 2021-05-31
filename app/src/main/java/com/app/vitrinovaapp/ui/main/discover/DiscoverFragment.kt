package com.app.vitrinovaapp.ui.main.discover

import android.app.Activity
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vitrinovaapp.R
import com.app.vitrinovaapp.data.model.discover.*
import com.app.vitrinovaapp.data.model.discover.Collections
import com.app.vitrinovaapp.databinding.FragmentDiscoverBinding
import com.app.vitrinovaapp.ui.custom.FeaturedViewPagerTransformer
import com.app.vitrinovaapp.ui.main.MainActivity
import com.app.vitrinovaapp.ui.main.collection_detail.CollectionsDetailFragment
import com.app.vitrinovaapp.ui.main.discover.adapter.*
import com.app.vitrinovaapp.ui.main.products_detail.ProductsDetailFragment
import com.app.vitrinovaapp.ui.main.shops_detail.ShopsDetailFragment
import com.app.vitrinovaapp.utils.GlideUtils
import com.app.vitrinovaapp.utils.Utils
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var binding: FragmentDiscoverBinding

    private lateinit var featuredAdapter: FeaturedViewPagerAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var collectionsAdapter: CollectionsAdapter
    private lateinit var editorShopsAdapter: EditorShopsAdapter
    private lateinit var newShopsAdapter: NewShopsAdapter

    private var listProducts: List<Product>? = null
    private var listCollections: List<Collections>? = null
    private var listEditorShops: List<Shop>? = null
    private var listNewShops: List<Shop>? = null

    private var scrollIsIdle: Boolean = false
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiscoverBinding.bind(view)

        initView()
        initClick()
        getDiscover()
        listenDiscoverData()
    }

    private fun initView() {
        binding.run {
            featuredAdapter = FeaturedViewPagerAdapter(listOf())
            productsAdapter = ProductsAdapter(listOf())
            categoriesAdapter = CategoriesAdapter(listOf())
            collectionsAdapter = CollectionsAdapter(listOf())
            editorShopsAdapter = EditorShopsAdapter(listOf())
            newShopsAdapter = NewShopsAdapter(listOf())

            recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            recyclerViewCategories.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            recyclerViewCollections.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            recyclerViewEditorShops.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            recyclerViewNewShops.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
    }

    private fun initClick() {
        binding.run {
            btnVoice.setOnClickListener {
                initSpeech()
            }

            swipeRefreshLayout.setOnRefreshListener {
                getDiscover()
            }
            btnAllProducts.setOnClickListener {
                listProducts?.let { products ->
                    val productDetailFragment = ProductsDetailFragment(labelNewProducts.text.toString(),products)
                    (activity as MainActivity).loadFragment(productDetailFragment,"ProductsDetailFragment")
                }
            }

            btnAllCollections.setOnClickListener {
                listCollections?.let { collections ->
                    val collectionsFragment = CollectionsDetailFragment(labelCollections.text.toString(),collections)
                    (activity as MainActivity).loadFragment(collectionsFragment,"CollectionsDetailFragment")
                }
            }

            btnEditorShops.setOnClickListener {
                listEditorShops?.let { shops ->
                    val shopsFragment = ShopsDetailFragment(labelEditorShops.text.toString(),shops)
                    (activity as MainActivity).loadFragment(shopsFragment,"ShopsDetailFragment")
                }
            }

            btnAllNewShops.setOnClickListener {
                listNewShops?.let { shops ->
                    val shopsFragment = ShopsDetailFragment(labelNewShops.text.toString(),shops)
                    (activity as MainActivity).loadFragment(shopsFragment,"ShopsDetailFragment")
                }
            }
        }
    }

    private fun getDiscover() {
        if (Utils.isNetworkAvailable(requireContext())) {
            binding.run {
                swipeRefreshLayout.isRefreshing=true
                emptyLoadingView.visibility=View.VISIBLE
                viewModel.getDiscover()
            }
        }
        else
            Utils.showToast(requireContext(),getString(R.string.no_internet))
    }

    private fun listenDiscoverData(){
        viewModel.postDiscover.observe(requireActivity(),{
            it.let {
                binding.run {
                    labelNewProducts.text=it[1].title
                    labelCategories.text=it[2].title
                    labelCollections.text=it[3].title
                    labelEditorShops.text=it[4].title
                    labelNewShops.text=it[5].title

                    swipeRefreshLayout.isRefreshing=false
                    emptyLoadingView.visibility=View.GONE
                }
                initCarousel(it[0].featured)
                initProducts(it[1].products)
                initCategories(it[2].categories)
                initCollections(it[3].collections)
                initEditorShops(it[4].shops)
                initNewShops(it[5].shops)
            }
        })
    }

    private fun initNewShops(shops: List<Shop>) {
        binding.run {
            listNewShops = shops
            newShopsAdapter.updateNewShops(shops)
            recyclerViewNewShops.adapter=newShopsAdapter
        }
    }

    private fun initEditorShops(shops: List<Shop>) {
        binding.run {
            listEditorShops = shops
            editorShopsAdapter.updateEditorShops(shops)
            recyclerViewEditorShops.adapter=editorShopsAdapter
            setGrayscaleColorFilter()

            val snapHelperEditorShop = GravitySnapHelper(Gravity.START)
            snapHelperEditorShop.attachToRecyclerView(recyclerViewEditorShops)
            shops[0].cover?.let { cover->
                GlideUtils.urlToImageView(requireContext(), cover.url, imgEditorBackground)
            }


            recyclerViewEditorShops.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollIsIdle) {
                        recyclerViewEditorShops.layoutManager?.let { manager ->
                            val snapView = snapHelperEditorShop.findSnapView(manager)
                            val snapPosition = snapView?.let { snap -> manager.getPosition(snap) }
                            snapPosition?.let { position ->
                                shops[position].cover?.let { cover ->
                                    GlideUtils.urlToImageView(requireContext(), cover.url, imgEditorBackground)
                                }
                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    scrollIsIdle = newState != RecyclerView.SCROLL_STATE_DRAGGING
                }


            })
        }
    }

    private fun initCollections(collections: List<Collections>) {
        binding.run {
            listCollections = collections
            collectionsAdapter.updateCollections(collections)
            recyclerViewCollections.adapter=collectionsAdapter
        }
    }

    private fun initCategories(categories: List<Category>) {
        binding.run {
            categoriesAdapter.updateCategories(categories)
            recyclerViewCategories.adapter=categoriesAdapter
        }
    }

    private fun initProducts(products: List<Product>) {
        binding.run {
            listProducts = products
            productsAdapter.updateProducts(products)
            recyclerViewProducts.adapter=productsAdapter
        }
    }

    private fun initCarousel(featured: List<Featured>) {
        binding.run {
            featuredAdapter.updateFeatured(featured)
            featuresViewPager.setPageTransformer(FeaturedViewPagerTransformer())
            featuresViewPager.adapter=featuredAdapter

            TabLayoutMediator(tabDots, featuresViewPager) { _, _ -> }.attach()
        }
    }

    private fun setGrayscaleColorFilter(){
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)
        binding.imgEditorBackground.colorFilter = filter
    }

    private fun initSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.discover_search_hint))
        }

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        binding.editSearch.setText(it[0])
                    }
                }
            }
        }
    }
}