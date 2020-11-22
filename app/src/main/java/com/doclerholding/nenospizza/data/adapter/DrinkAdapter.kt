package com.doclerholding.nenospizza.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.Drink
import java.text.NumberFormat
import java.util.*

class DrinkAdapter(items: List<Drink>) : RecyclerView.Adapter<DrinkAdapter.CartListViewHolder>() {
    private val drink_items: List<Drink>
    internal var onItemClick: ((Drink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.drinks_list_item, parent, false)
        return CartListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CartListViewHolder, position: Int) {
        val item: Drink = drink_items[position]
        viewHolder.drink_item_name.setText(item.name)
        viewHolder.drink_item_price.setText("$"+ item.price.toString())
        viewHolder.drink_item_container.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return drink_items.size
    }

    class CartListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var drink_item_name: TextView
        var drink_item_price: TextView
        var drink_item_add: ImageButton
        var drink_item_container: RelativeLayout

        init {
            drink_item_name = itemView.findViewById<View>(R.id.ingredientName) as TextView
            drink_item_price = itemView.findViewById<View>(R.id.ingredientPrice) as TextView
            drink_item_add = itemView.findViewById<View>(R.id.drinkAdd) as ImageButton
            drink_item_container = itemView.findViewById<View>(R.id.drinkItemContainer) as RelativeLayout
        }
    }

    init {
        drink_items = items
    }
}