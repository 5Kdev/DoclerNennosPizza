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
import com.doclerholding.nenospizza.data.responses.Status
import com.doclerholding.nenospizza.utils.isConnectedToNetwork
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.checkout_success_block.*
import kotlinx.android.synthetic.main.toolbar.*

class CartActivity : BaseActivity() {

    private lateinit var cartAdapter:CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setToolBar(getString(R.string.cart_title),this)

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

            check_out_cart.text= getString(R.string.checkout)+" ($${it.toString()})"
        })
    }

    private fun drinkOnClick(){
        val intent = Intent(this, DrinksActivity::class.java)
        startActivity(intent)
    }


    private fun showSuccess(){
        toolbar.visibility = View.GONE
        order_success.visibility = View.VISIBLE

        back_to_home.setOnClickListener {
            cartRepository.clear()
            pizzaRepository.clearSelectedPizza()
            finish();
        }

    }

    private fun checkoutOnClick() {
        if (this.isConnectedToNetwork()) {

            cartModel.sendOrder(cartRepository.getCheckoutData()).observe(this, Observer {

                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data?.let {
                                showSuccess()
                            }
                        }
                        Status.ERROR -> {
                            resource.message?.let { it1 -> showError(it1) }
                        }
                        Status.LOADING -> {
                        }
                    }

                }
            })

        }else{
            showError(getString(R.string.general_no_internet_connection))
        }

    }




}