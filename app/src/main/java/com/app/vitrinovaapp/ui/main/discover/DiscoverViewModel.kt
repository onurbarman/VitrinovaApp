package com.app.vitrinovaapp.ui.main.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vitrinovaapp.data.model.discover.DiscoverModel
import com.app.vitrinovaapp.data.repository.VitrinovaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: VitrinovaRepository) : ViewModel() {

    val postDiscover: MutableLiveData<DiscoverModel> by lazy {
        MutableLiveData<DiscoverModel>()
    }

    fun getDiscover() {
        viewModelScope.launch {
            val retrofitPost = repository.getDiscover()
            retrofitPost.data?.let {
                postDiscover.postValue(retrofitPost.data)
                Log.d("discover_response",retrofitPost.data.toString())
            }
        }
    }
}