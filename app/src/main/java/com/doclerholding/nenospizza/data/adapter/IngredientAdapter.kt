package com.doclerholding.nenospizza.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.Ingredient


class IngredientAdapter(items: List<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientListViewHolder>() {
    private val ingredientItems: List<Ingredient> = items
    internal var onItemClick: ((Ingredient) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list_item, parent, false)
        return IngredientListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: IngredientListViewHolder, position: Int) {
        val item: Ingredient = ingredientItems[position]
        viewHolder.ingredent_item_name.text = item.name
        viewHolder.ingredent_item_price.text = "$" + item.price.toString()

        viewHolder.ingredent_item_chkbox.isChecked = item.selected;

        viewHolder.ingredent_item_chkbox.setOnCheckedChangeListener { _, isChecked ->
            ingredientItems[position].selected = isChecked
        }

        viewHolder.ingredent_item_chkbox.setOnClickListener {
            onItemClick?.invoke(ingredientItems[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return ingredientItems.size
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

}