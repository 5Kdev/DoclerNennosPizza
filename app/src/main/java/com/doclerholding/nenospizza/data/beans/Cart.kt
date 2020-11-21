package com.doclerholding.nenospizza.data.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Cart (
     var pizzas: MutableList<Pizza> = mutableListOf<Pizza>(),
     var drinks: MutableList<Drink> = mutableListOf<Drink>(),
     var summaryPrice: Double = 0.0,
     var itemNum: Int = 0,
): Serializable