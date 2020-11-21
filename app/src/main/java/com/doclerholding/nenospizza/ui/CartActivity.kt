package com.doclerholding.nenospizza.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.CartAdapter
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Pizza
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.toolbar.*

class CartActivity : BaseActivity() {

    private lateinit var cartAdapter:CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setToolBar("Cart",this)

        cartRepository.viewmodel = cartModel
        setContent()
        setLiveData()
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.setData(cartRepository.getPizzas(),cartRepository.getDrinks())
        cartModel.sumPrice.value = cartRepository.getSummaryPrice()
    }

    private fun setContent(){
        cartAdapter = CartAdapter(cartRepository.getPizzas(),cartRepository.getDrinks())

        cart_list.adapter = cartAdapter
        cart_list.layoutManager = LinearLayoutManager(this)
        cartAdapter.onItemClick = { cartItem -> adapterOnClick(cartItem) }

        toolbarImageBtn.setOnClickListener{ drinkOnClick() }
        cartModel.sumPrice.value = cartRepository.getSummaryPrice()
    }

    private fun adapterOnClick(item: Any){
        if (item is Drink) {
            cartRepository.removeDrink(item as Drink)
        }

        if (item is Pizza) {
            cartRepository.removePizza(item as Pizza)
        }

    }

    private fun setLiveData(){
        cartModel.sumPrice?.observe(this, Observer {
            check_out_cart.text= "Checkout ($${it.toString()})"
        })
    }

    fun drinkOnClick(){
        val intent = Intent(this, DrinksActivity::class.java)
        startActivity(intent)
    }

}