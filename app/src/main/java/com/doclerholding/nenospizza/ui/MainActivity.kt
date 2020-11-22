package com.doclerholding.nenospizza.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.PizzaAdapter
import com.doclerholding.nenospizza.data.beans.Pizza
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar("Nenno’s Pizza",this)

        pizzaRepository.initRepository()
        cartRepository.loadCartData(this.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE));
        cartRepository.viewmodel = cartModel

        setContent()
        setLiveData()
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
    }

    private fun setLiveData(){
        cartModel.itemNum?.observe(this, Observer {
            cartBadgeItemNum.text= it.toString()
        })
    }

    private fun adapterOnClick(item:Pizza){
        pizzaRepository.setSelectedPizza(item)
        val intent = Intent(this, PizzaEditorActivity::class.java)
        startActivity(intent)
    }

    private fun fabOnClick(){
        val intent = Intent(this, PizzaEditorActivity::class.java)
        startActivity(intent)
    }


}