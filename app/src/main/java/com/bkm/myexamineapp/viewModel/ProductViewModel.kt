package com.bkm.myexamineapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkm.myexamineapp.repository.MyRepository
import com.bkm.myexamineapp.models.GetProductsResponse
import com.bkm.myexamineapp.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val liveData = MutableLiveData<Resource<GetProductsResponse>>()

    fun getProduct(token: String): MutableLiveData<Resource<GetProductsResponse>> {

        viewModelScope.launch {
            liveData.postValue(Resource.loading("Loading..."))
            try {
                coroutineScope {
                    val response = async {
                        myRepository.getUserProducts(token)
                    }.await()
                    liveData.postValue(Resource.success(response))
                }
            }catch (e:Exception){
                liveData.postValue(Resource.error(e.message))
            }
        }

        return liveData
    }
}