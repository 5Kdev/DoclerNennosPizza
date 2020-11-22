package com.doclerholding.nenospizza.data.request

import com.doclerholding.nenospizza.data.beans.Pizza
import com.google.gson.annotations.SerializedName

data class CheckoutRequest(
    @SerializedName("pizzas") val pizzas : List<Pizza>,
    @SerializedName("drinks") val drinks : List<Long>
)