package com.doclerholding.nenospizza.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.DrinkAdapter
import com.doclerholding.nenospizza.data.beans.Drink
import kotlinx.android.synthetic.main.activity_drinks.*
import kotlinx.android.synthetic.main.toolbar.*

class DrinksActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks)

        setToolBar("Drinks",this);

        setContent()
    }

    private fun setContent(){
        val drinkAdapter: DrinkAdapter = DrinkAdapter(pizzaRepository.getDrinks() as MutableList<Drink>)

        drink_list.adapter = drinkAdapter
        drink_list.layoutManager = LinearLayoutManager(this)
        drinkAdapter.onItemClick = { drink -> adapterOnClick(drink) }

        toolbarImageBtn.setOnClickListener{ cartOnClick() }
        cartModel.sumPrice.value = cartRepository.getSummaryPrice()
    }

    private fun adapterOnClick(item: Drink){
        cartRepository.addDrink(item)
        addToCartAnimation(buttonProcessDrinks)
    }

}