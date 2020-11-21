package com.doclerholding.nenospizza.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.Pizza
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class PizzaAdapter(items: List<Pizza>) : RecyclerView.Adapter<PizzaAdapter.PizzaListViewHolder>() {
    private val pizza_items: List<Pizza>
    internal var onItemClick: ((Pizza) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.pizza_list_item,
            parent,
            false
        )
        return PizzaListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: PizzaListViewHolder, position: Int) {
        val item: Pizza = pizza_items[position]
        viewHolder.pizza_name.setText(item.name)
        viewHolder.pizza_ingredients.setText(item.getIngredientsString())

        viewHolder.pizza_order_btn.setText("$"+NumberFormat.getCurrencyInstance(Locale("US", "en")).format(item.price))

        Picasso.get()
                .load(item.imageUrl)
                .placeholder(R.drawable.custom)
                .resize(600, 600)
                .into(viewHolder.pizza_image)

        viewHolder.pizza_container.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return pizza_items.size
    }

    class PizzaListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var pizza_image: ImageView
        var pizza_name: TextView
        var pizza_ingredients: TextView
        var pizza_order_btn: Button
        var pizza_container: ConstraintLayout

        init {
            pizza_image = itemView.findViewById<View>(R.id.pizzaListImage) as ImageView
            pizza_name = itemView.findViewById<View>(R.id.pizzaListName) as TextView
            pizza_ingredients = itemView.findViewById<View>(R.id.pizzaListIngredients) as TextView
            pizza_order_btn = itemView.findViewById<View>(R.id.pizzaListOrder) as Button
            pizza_container= itemView.findViewById<View>(R.id.pizzaListContainer) as ConstraintLayout
        }
    }

    init {
        pizza_items = items

    }
}