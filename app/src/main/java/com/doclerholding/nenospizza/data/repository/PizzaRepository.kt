package com.doclerholding.nenospizza.data.repository

import com.doclerholding.nenospizza.data.api.NennosApiService
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Ingredient
import com.doclerholding.nenospizza.data.beans.Pizza
import com.doclerholding.nenospizza.data.responses.Pizzas
import com.doclerholding.nenospizza.ui.viewmodels.CartViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class PizzaRepository @Inject constructor(api: NennosApiService) :IRepository {

    private var pizzas: MutableList<Pizza> = mutableListOf<Pizza>()
    private var ingredients: List<Ingredient> = emptyList()
    private var drinks: List<Drink> = emptyList()
    private lateinit var emptyPizza: Pizza;

    private var selectedPizza: Pizza? = null
    open var viewmodel: CartViewModel? = null;

    var isInitialized = false

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


    fun syncSelectedPizza(ingredient: Ingredient){

        var ing =selectedPizza!!.ingredients_list.find { it.id==ingredient.id };
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

    fun getSelectedIgredientsList(): List<Ingredient>{
        var selectedIngredients:List<Ingredient> = ingredients

        if(selectedPizza != null){
            for (id in selectedPizza!!.ingredients) {
                selectedIngredients.find { it.id == id }!!.selected=true
            }
        }

        return selectedIngredients
    }

    fun calculatePizzaPrice(pizza: Pizza): Double{
        var price: Double=0.0

        price+=pizza.basePrice

        for (ingredient in pizza.ingredients_list) {
            price+= ingredient.price!!
        }

        return price
    }

    fun initRepository(){
        isInitialized = true

        emptyPizza = Pizza(emptyList(), mutableListOf(),4.0,"")
        emptyPizza.name = "Custom Pizza"

        val gson = Gson()

        val drinktype = object : TypeToken<List<Drink>>() {}.type
        val ingredienttype = object : TypeToken<List<Ingredient>>() {}.type

        val drink_response: List<Drink> = gson.fromJson(getDrinksString(), drinktype)
        val ingredient_response: List<Ingredient> = gson.fromJson(getIngredientsString(), ingredienttype)
        val pizzas_response: Pizzas = gson.fromJson(getPizzaString(), Pizzas::class.java)

        ingredients=ingredient_response
        drinks=drink_response

        emptyPizza.price= pizzas_response.base_price.toDouble()
        emptyPizza.basePrice= pizzas_response.base_price.toDouble()

        for (pizza in pizzas_response.pizzas) {

            pizza.ingredients_list = mutableListOf<Ingredient>()
            pizza.basePrice= pizzas_response.base_price.toDouble()

            for (ing_id in pizza.ingredients){
                var ing:Ingredient = ingredients!!.find { it.id == ing_id }!!
                pizza.ingredients_list.add(ing)
            }

            pizza.price=calculatePizzaPrice(pizza)

            pizzas.add(pizza)
        }


    }


    fun getDrinksString():String{
        return "[{\n" +
                "    \"price\": 1,\n" +
                "    \"name\": \"Still Water\",\n" +
                "    \"id\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 1.5,\n" +
                "    \"name\": \"Sparkling Water\",\n" +
                "    \"id\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 2.5,\n" +
                "    \"name\": \"Coke\",\n" +
                "    \"id\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 3,\n" +
                "    \"name\": \"Beer\",\n" +
                "    \"id\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 4,\n" +
                "    \"name\": \"Red Wine\",\n" +
                "    \"id\": 5\n" +
                "  }]"
    }

    fun getPizzaString():String{
        return "{\n" +
                "  \"basePrice\": 4,\n" +
                "  \"pizzas\": [\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        2\n" +
                "      ],\n" +
                "      \"name\": \"Margherita\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44095.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        5\n" +
                "      ],\n" +
                "      \"name\": \"Ricci\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44092.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4\n" +
                "      ],\n" +
                "      \"name\": \"Boscaiola\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44090.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        5,\n" +
                "        6\n" +
                "      ],\n" +
                "      \"name\": \"Primavera\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44077.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        7,\n" +
                "        8\n" +
                "      ],\n" +
                "      \"name\": \"Hawaii\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44073.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        9,\n" +
                "        10\n" +
                "      ],\n" +
                "      \"name\": \"Mare Bianco\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        4,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10\n" +
                "      ],\n" +
                "      \"name\": \"Mari e monti\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44075.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        9\n" +
                "      ],\n" +
                "      \"name\": \"Bottarga\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44071.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        9,\n" +
                "        6\n" +
                "      ],\n" +
                "      \"name\": \"Boottarga e Asparagi\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44068_error_on_purpose.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ingredients\": [\n" +
                "        1,\n" +
                "        5,\n" +
                "        6\n" +
                "      ],\n" +
                "      \"name\": \"Ricci e Asparagi\",\n" +
                "      \"imageUrl\": \"https://doclerlabs.github.io/mobile-native-challenge/images/pizza_PNG44066.png\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    fun getIngredientsString():String{
        return "[\n" +
                "  {\n" +
                "    \"price\": 1,\n" +
                "    \"name\": \"Mozzarella\",\n" +
                "    \"id\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 0.5,\n" +
                "    \"name\": \"Tomato Sauce\",\n" +
                "    \"id\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 1.5,\n" +
                "    \"name\": \"Salami\",\n" +
                "    \"id\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 2,\n" +
                "    \"name\": \"Mushrooms\",\n" +
                "    \"id\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 4,\n" +
                "    \"name\": \"Ricci\",\n" +
                "    \"id\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 2,\n" +
                "    \"name\": \"Asparagus\",\n" +
                "    \"id\": 6\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 1,\n" +
                "    \"name\": \"Pineapple\",\n" +
                "    \"id\": 7\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 3,\n" +
                "    \"name\": \"Speck\",\n" +
                "    \"id\": 8\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 2.5,\n" +
                "    \"name\": \"Bottarga\",\n" +
                "    \"id\": 9\n" +
                "  },\n" +
                "  {\n" +
                "    \"price\": 2.2,\n" +
                "    \"name\": \"Tuna\",\n" +
                "    \"id\": 10\n" +
                "  }\n" +
                "]"
    }





    companion object {
        private const val TAG = "PizzasRepository"
    }

}