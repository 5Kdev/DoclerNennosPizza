package com.doclerholding.nenospizza.data.beans

import com.google.gson.annotations.SerializedName

data class Drink (
        @SerializedName("id") val id : Long
): CartItem("Drink"){
        override fun toString(): String {
                return "Drink(id:$id, name=$name, price=$price )"
        }
}