package com.doclerholding.nenospizza.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doclerholding.nenospizza.data.beans.CartItem

class CartViewModel : ViewModel() {

    val itemNum = MutableLiveData<Int>()
    val sumPrice = MutableLiveData<Double>()
    val cartItems = MutableLiveData<MutableList<CartItem>>()


    class ViewModelFactory(): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CartViewModel() as T
        }

    }
}