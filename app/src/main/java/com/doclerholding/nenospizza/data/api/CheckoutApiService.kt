package com.doclerholding.nenospizza.data.api

import com.doclerholding.nenospizza.data.request.CheckoutRequest
import com.doclerholding.nenospizza.data.responses.Pizzas
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CheckoutApiService {

    @POST("post")
    suspend fun checkoutOrder(@Body body: CheckoutRequest ):JsonObject

}

