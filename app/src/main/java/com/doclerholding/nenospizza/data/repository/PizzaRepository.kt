package com.doclerholding.nenospizza.data.repository

import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Ingredient
import com.doclerholding.nenospizza.data.beans.Pizza
import com.doclerholding.nenospizza.data.responses.Pizzas
import com.doclerholding.nenospizza.viewmodels.CartViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PizzaRepository{

    public lateinit var drinkResource: String
    public lateinit var ingredientResource: String
    public lateinit var pizzaResource: String

    private var pizzas: MutableList<Pizza> = mutableListOf<Pizza>()
    private var ingredients: List<Ingredient> = emptyList()
    private var drinks: List<Drink> = emptyList()
    private lateinit var emptyPizza: Pizza;

    private var selectedPizza: Pizza? = null
    open var viewmodel: CartViewModel? = null;

    var isInitialized = false


    fun syncSelectedPizza(ingredient: Ingredient){

        var ing = selectedPizza!!.ingredients_list.find { it.id==ingredient.id };

        if(ing!= null){
            if(ingredient.selected){
                ing.selected=true
            }else{
                selectedPizza!!.ingredients_list.remove(ingredient)
            }
        }
        else{
            ingredient.selected = true
            selectedPizza?.ingredients_list?.add(ingredient)
        }

        selectedPizza?.price = calculatePizzaPrice(selectedPizza!!);
        viewmodel?.sumPrice?.value = selectedPizza?.price
    }



    fun getSelectedIngredientsList(): List<Ingredient>{
        var selectedIngredients:List<Ingredient> = ingredients

        if(selectedPizza != null){

            for (ingredient in selectedIngredients) {
                ingredient.selected = false
            }

            for (id in selectedPizza!!.ingredients) {
                selectedIngredients.find { it.id == id }!!.selected=true
            }
        }

        return selectedIngredients
    }



    private fun calculatePizzaPrice(pizza: Pizza): Double{
        var price: Double=0.0

        price+=pizza.basePrice

        for (ingredient in pizza.ingredients_list) {
            price+= ingredient.price!!
        }

        return price
    }


    fun initRepository(){
        emptyPizza = Pizza(emptyList(), mutableListOf(),0.0,"")
        emptyPizza.name = "Custom Pizza"

        val gson = Gson()

        val drinktype = object : TypeToken<List<Drink>>() {}.type
        val ingredienttype = object : TypeToken<List<Ingredient>>() {}.type
        val pizzasType = object : TypeToken<Pizzas>() {}.type

        var pizzas_response: Pizzas? = null

        drinks = gson.fromJson(drinkResource, drinktype)
        ingredients = gson.fromJson(ingredientResource, ingredienttype)
        pizzas_response= gson.fromJson(pizzaResource, pizzasType)


        emptyPizza.price= pizzas_response?.base_price?.toDouble()
        emptyPizza.basePrice= pizzas_response!!.base_price!!.toDouble()

        for (pizza in pizzas_response.pizzas) {

            pizza.ingredients_list = mutableListOf<Ingredient>()
            pizza.basePrice= pizzas_response.base_price.toDouble()

            for (ing_id in pizza.ingredients){
                var ing:Ingredient = ingredients.find { it.id == ing_id }!!
                pizza.ingredients_list.add(ing)
            }

            pizza.price=calculatePizzaPrice(pizza)

            pizzas.add(pizza)
        }

        isInitialized = true

    }


    fun getPizzas(): List<Pizza>? {
        return pizzas
    }

    fun getIngredients(): List<Ingredient>? {
        return ingredients
    }

    fun getDrinks(): List<Drink>? {
        return drinks
    }

    fun getSelectedPizza(): Pizza? {
        return selectedPizza
    }

    fun setSelectedPizza(pizza: Pizza) {
        selectedPizza=pizza
    }

    fun clearSelectedPizza(){
        selectedPizza=null
    }

    fun setSelectedEmptyPizza(){
        selectedPizza=emptyPizza
    }

    companion object {
        private const val TAG = "PizzasRepository"
    }

}