package com.doclerholding.nenospizza.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.doclerholding.nenospizza.data.api.CheckoutApiService
import com.doclerholding.nenospizza.data.request.CheckoutRequest
import com.doclerholding.nenospizza.data.responses.Resource
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val apiService: CheckoutApiService) : ViewModel() {

    val itemNum = MutableLiveData<Int>()
    val sumPrice = MutableLiveData<Double>()

    fun sendOrder(request: CheckoutRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.checkoutOrder(request)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }

    class ViewModelFactory(private val apiService: CheckoutApiService): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CartViewModel(apiService) as T
        }

    }
}