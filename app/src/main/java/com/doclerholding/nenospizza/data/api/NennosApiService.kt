package com.doclerholding.nenospizza.data.api

import com.doclerholding.nenospizza.data.responses.Pizzas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NennosApiService {

    @GET("ingredients.json")
    suspend fun getIngredients(): String

    @GET("drinks.json")
    suspend fun getDrinks(): String

    @GET("pizzas.json")
    suspend fun getPizzas(): String

}

