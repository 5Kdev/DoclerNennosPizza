package com.doclerholding.nenospizza.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.doclerholding.nenospizza.data.beans.Cart
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Pizza
import com.doclerholding.nenospizza.data.request.CheckoutRequest
import com.doclerholding.nenospizza.viewmodels.CartViewModel
import com.google.gson.Gson


class CartRepository{

    private var pizzas: MutableList<Pizza> = mutableListOf<Pizza>()
    private var drinks: MutableList<Drink> = mutableListOf<Drink>()
    private var summaryPrice: Double = 0.0
    private var itemNum: Int = 0

    open var viewmodel: CartViewModel? = null;

    fun addPizza(pizza: Pizza){
        pizzas.add(pizza)
        summaryPrice+= pizza.price!!
        itemNum++
        updateData()
    }

    fun removePizza(pizza: Pizza){
        pizzas.remove(pizza)
        summaryPrice-= pizza.price!!
        itemNum--
        updateData()
    }


    fun addDrink(drink: Drink){
        drinks.add(drink)
        summaryPrice+= drink.price!!
        itemNum++
        updateData()
    }

    fun removeDrink(drink: Drink){
        drinks.remove(drink)
        summaryPrice-= drink.price!!
        itemNum--
        updateData()
    }

    fun getPizzas(): MutableList<Pizza> {
        return pizzas
    }

    fun getDrinks(): MutableList<Drink> {
        return drinks
    }

    fun getSummaryPrice(): Double {
        return summaryPrice
    }

    fun getItemNum(): Int {
        return itemNum
    }

    fun clear(){
        pizzas.clear()
        drinks.clear()
        summaryPrice= 0.0
        itemNum= 0
        updateData()
    }

    fun updateData(){
        if(summaryPrice<0) summaryPrice=0.0
        if(itemNum<0) itemNum=0

        viewmodel?.sumPrice?.value = summaryPrice
        viewmodel?.itemNum?.value = itemNum
    }


    fun saveCartData(sharedPreference: SharedPreferences.Editor){

        sharedPreference.putString(
            "CART", Gson().toJson(
                Cart(
                    pizzas,
                    drinks,
                    summaryPrice,
                    itemNum
                )
            )
        )
        sharedPreference.commit()
    }

    fun loadCartData(sharedPreference: SharedPreferences){
        try {
            Log.d("LOAD",sharedPreference?.getString("CART","").toString())
            val savedCart=Gson().fromJson(sharedPreference?.getString("CART",""), Cart::class.java)


            if(savedCart!=null){
                pizzas=savedCart.pizzas
                drinks=savedCart.drinks
                summaryPrice=savedCart.summaryPrice
                itemNum=savedCart.itemNum
            }

        }catch (e: Exception){
            Log.e("GSON", e.toString())
        }
    }

    fun getCheckoutData(): CheckoutRequest{

        var drinkIds: MutableList<Long> = mutableListOf()

        for(drink:Drink in drinks){
            drinkIds.add(drink.id)
        }

        val request: CheckoutRequest = CheckoutRequest(pizzas,drinkIds)

        return request;
    }


}