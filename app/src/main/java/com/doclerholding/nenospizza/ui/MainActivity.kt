package com.doclerholding.nenospizza.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.PizzaAdapter
import com.doclerholding.nenospizza.data.beans.Pizza
import com.doclerholding.nenospizza.data.responses.Status
import com.doclerholding.nenospizza.utils.isConnectedToNetwork
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar(getString(R.string.main_title), this)

        cartRepository.loadCartData(this.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE));

        if(!pizzaRepository.isInitialized){
            getDatas()
        }

    }


    override fun onResume() {
        super.onResume()
        pizzaRepository.clearSelectedPizza()
        cartModel.itemNum.value=cartRepository.getItemNum()
    }


    private fun setContent(){
        val pizzaAdapter:PizzaAdapter = PizzaAdapter(pizzaRepository.getPizzas()!!)

        pizza_list.adapter = pizzaAdapter
        pizza_list.layoutManager = LinearLayoutManager(this)
        pizzaAdapter.onItemClick = { pizza -> adapterOnClick(pizza) }

        floatingActionButton.setOnClickListener { fabOnClick() }
        toolbarImageBtn.setOnClickListener{ cartOnClick() }
        cartBadge.setOnClickListener{ cartOnClick() }
    }

    private fun setLiveData(){
        cartModel.itemNum?.observe(this, Observer {
            cartBadgeItemNum.text = it.toString()
        })
    }

    private fun adapterOnClick(item: Pizza){
        pizzaRepository.setSelectedPizza(item)
        val intent = Intent(this, PizzaEditorActivity::class.java)
        startActivity(intent)
    }

    private fun fabOnClick(){
        val intent = Intent(this, PizzaEditorActivity::class.java)
        startActivity(intent)
    }




    private fun getDatas() {
        if (this.isConnectedToNetwork()) {

                pizzaModel.getAllData().observe(this, Observer {

                    it?.let { resource ->
                        when (resource.status) {

                            Status.SUCCESS -> {
                                showProgress(false)

                                resource.data?.let {
                                    pizzaRepository.drinkResource = it.get("drinks").toString()
                                    pizzaRepository.ingredientResource = it.get("ingredients").toString()
                                    pizzaRepository.pizzaResource = it.get("pizzas").toString()

                                    pizzaRepository.initRepository()
                                    setContent()
                                    setLiveData()
                                }

                            }
                            Status.ERROR -> {
                                showError(getString(R.string.data_init_error))
                                showProgress(false)
                            }
                            Status.LOADING -> {
                                showProgress(true)
                            }
                        }

                    }
                })

        } else {
            showError(getString(R.string.general_no_internet_connection))
        }

    }






}