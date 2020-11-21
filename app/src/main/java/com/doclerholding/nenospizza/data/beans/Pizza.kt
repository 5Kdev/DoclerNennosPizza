package com.doclerholding.nenospizza.data.beans
import com.google.gson.annotations.SerializedName

data class Pizza(
	@SerializedName("ingredients") var ingredients: List<Long>,
	var ingredients_list: MutableList<Ingredient> = mutableListOf(),
	var basePrice: Double = 0.0,
	@SerializedName("imageUrl") var imageUrl: String = ""
) : CartItem("Pizza"){

	override fun toString(): String {
	return "Pizza(name=$name, price=$price, basePrice=$basePrice, ingredients=$ingredients, ingredients_list=$ingredients_list,imageUrl=$imageUrl)"
	}

	fun getIngredientsString():String{

		var names:MutableList<String> = mutableListOf()

		for (ingredient in ingredients_list) {
			ingredient.name?.let { names.add(it) }
		}

		return names.joinToString()
	}
}