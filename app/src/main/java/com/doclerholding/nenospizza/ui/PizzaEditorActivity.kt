package com.doclerholding.nenospizza.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.adapter.IngredientAdapter
import com.doclerholding.nenospizza.data.beans.Ingredient
import com.doclerholding.nenospizza.data.beans.Pizza
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pizza_editor.*
import javax.inject.Inject

class PizzaEditorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_editor)

        var title: String = "Custom Pizza" ;

        if(pizzaRepository.getSelectedPizza() != null){
            title = pizzaRepository.getSelectedPizza()?.name.toString()
            Picasso.get()
                .load(pizzaRepository.getSelectedPizza()?.imageUrl)
                .placeholder(R.drawable.custom)
                .resize(300, 300)
                .into(pizzaImage)
        }else{
            pizzaRepository.setSelectedEmptyPizza()
        }

        pizzaRepository.viewmodel = cartModel
        cartRepository.viewmodel = cartModel

        setToolBar(title,this)
        setLiveData()
        setContent()

    }

    private fun setContent(){
        val ingredientsAdapter: IngredientAdapter = IngredientAdapter(pizzaRepository.getSelectedIgredientsList())

        ingredients_list.setHasFixedSize(true)
        ingredients_list.adapter = ingredientsAdapter
        ingredients_list.layoutManager = LinearLayoutManager(this)

        ingredientsAdapter.onItemClick = { ingredient -> adapterOnClick(ingredient) }

        add_to_cart.setOnClickListener({ v -> onAddToCartClick() })

        if(pizzaRepository.getSelectedPizza() != null) {
            cartModel?.sumPrice?.value = pizzaRepository.getSelectedPizza()?.price
        }else{
            cartModel?.sumPrice?.value = 4.0
        }

    }

    private fun setLiveData(){
        cartModel.sumPrice?.observe(this, Observer {
            add_to_cart.text= "Add to cart ($${it.toString()})"
        })
    }

    private fun adapterOnClick(ingredient: Ingredient){
        pizzaRepository.syncSelectedPizza(ingredient)
    }

    private fun onAddToCartClick(){
       cartRepository.addPizza(pizzaRepository.getSelectedPizza()!!)
       pizzaRepository.clearSelectedPizza()
       finish()
    }



}