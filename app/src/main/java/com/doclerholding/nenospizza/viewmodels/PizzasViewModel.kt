package com.doclerholding.nenospizza.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.doclerholding.nenospizza.data.api.NennosApiService
import com.doclerholding.nenospizza.data.responses.Resource
import kotlinx.coroutines.Dispatchers

class PizzasViewModel(private val apiService: NennosApiService) : ViewModel() {

    fun getDrinks() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.getDrinks()))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }

    fun getIngredients() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.getIngredients()))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }

    fun getPizzas() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.getPizzas()))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }

    fun getAllData()= liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))

        try {
            val drinks = apiService.getDrinks()
            val ingredients = apiService.getIngredients()
            val pizzas =apiService.getPizzas()

            var datas = HashMap<String, String>()
            datas.put("drinks",drinks)
            datas.put("ingredients",ingredients)
            datas.put("pizzas",pizzas)

            emit(Resource.success(data=datas))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }



    class ViewModelFactory(private val apiService: NennosApiService) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PizzasViewModel(apiService) as T
        }

    }



}