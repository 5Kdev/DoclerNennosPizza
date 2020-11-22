package com.doclerholding.nenospizza.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.CartAdapter
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Pizza
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.checkout_process_block.*
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
        check_out_cart.setOnClickListener{ checkoutOnClick() }

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
            if(it == 0.0){
                check_out_cart.isEnabled=false
                check_out_cart.isClickable=false
            }
            else{
                check_out_cart.isEnabled=true
                check_out_cart.isClickable=true
            }

            check_out_cart.text= "Checkout ($${it.toString()})"
        })
    }

    private fun drinkOnClick(){
        val intent = Intent(this, DrinksActivity::class.java)
        startActivity(intent)
    }


    private fun checkoutOnClick(){
        toolbar.visibility = View.GONE
        order_success.visibility = View.VISIBLE

        back_to_home.setOnClickListener {
            cartRepository.clear()
            pizzaRepository.clearSelectedPizza()
            finish();
        }

    }




}