package com.doclerholding.nenospizza.data.responses

import com.doclerholding.nenospizza.data.beans.Pizza
import com.google.gson.annotations.SerializedName

data class Pizzas(
    @SerializedName("basePrice") val base_price : Float,
    @SerializedName("pizzas") val pizzas : List<Pizza>
)
