package com.app.vitrinovaapp.data.repository

import com.app.vitrinovaapp.data.model.discover.DiscoverModel
import com.app.vitrinovaapp.data.remote.Resource
import com.app.vitrinovaapp.data.remote.ServiceClientInstance
import com.app.vitrinovaapp.utils.Utils.safeApiCall

class VitrinovaRepository {
    suspend fun getDiscover(): Resource<DiscoverModel> {
        return safeApiCall(call = { ServiceClientInstance.getInstance().api.getDiscover() })
    }
}