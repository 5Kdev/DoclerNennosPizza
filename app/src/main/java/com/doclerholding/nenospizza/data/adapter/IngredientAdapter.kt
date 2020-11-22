package com.doclerholding.nenospizza.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.CartItem
import com.doclerholding.nenospizza.data.beans.Ingredient
import com.doclerholding.nenospizza.data.beans.Pizza
import java.text.NumberFormat
import java.util.*


class IngredientAdapter(items: List<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientListViewHolder>() {
    private val ingredient_items: List<Ingredient>
    internal var onItemClick: ((Ingredient) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list_item, parent, false)
        return IngredientListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: IngredientListViewHolder, position: Int) {
        val item: Ingredient = ingredient_items[position]
        viewHolder.ingredent_item_name.setText(item.name)
        viewHolder.ingredent_item_price.setText("$"+ item.price.toString())

        viewHolder.ingredent_item_chkbox.setChecked(item.selected);

        viewHolder.ingredent_item_chkbox.setOnCheckedChangeListener({
                buttonView, isChecked ->
            if (isChecked){
                ingredient_items[position].selected=true
            }else{
                ingredient_items[position].selected=false
            }
        })

        viewHolder.ingredent_item_chkbox.setOnClickListener {
            onItemClick?.invoke(ingredient_items[position])
        }
    }

    override fun getItemCount(): Int {
        return ingredient_items.size
    }

    class IngredientListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ingredent_item_name: TextView
        var ingredent_item_price: TextView
        var ingredent_item_chkbox: CheckBox

        init {
            ingredent_item_name = itemView.findViewById<View>(R.id.ingredientName) as TextView
            ingredent_item_price = itemView.findViewById<View>(R.id.ingredientPrice) as TextView
            ingredent_item_chkbox = itemView.findViewById<View>(R.id.ingredientCheckbox) as CheckBox
        }
    }

    init {
        ingredient_items = items
    }
}