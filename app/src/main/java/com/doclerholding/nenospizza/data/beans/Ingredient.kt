package com.doclerholding.nenospizza.data.beans

import com.google.gson.annotations.SerializedName

data class Ingredient (
		@SerializedName("id") val id : Long,
		var selected : Boolean = false
): CartItem("Ingredient"){
	override fun toString(): String {
		return "Ingredient(id:$id, name=$name, price=$price )"
	}
}