package com.doclerholding.nenospizza.data.api

import com.doclerholding.nenospizza.data.responses.Pizzas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NennosApiService {

    @GET("ingerdients.json")
    suspend fun getIngredients(): Any

    @GET("drinks.json")
    suspend fun getDrinks(): Any

    @GET("pizzas.json")
    suspend fun getPizzas(): Response<Pizzas>

    @POST("/post")
    suspend fun checkoutOrder(@Body body :Any):Any

}

