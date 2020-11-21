package com.doclerholding.nenospizza.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.CartItem
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Pizza
import java.text.NumberFormat
import java.util.*


class CartAdapter(pizzas: MutableList<Pizza>,drinks: MutableList<Drink>) : RecyclerView.Adapter<CartAdapter.CartListViewHolder>() {
    private var cart_items: MutableList<CartItem> = mutableListOf()
    internal var onItemClick: ((Any) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.cart_list_item, parent, false)
        return CartListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CartListViewHolder, position: Int) {
        val item: CartItem = cart_items[position]
        viewHolder.cart_item_name.setText(item.name)
        viewHolder.cart_item_price.setText("$"+ NumberFormat.getCurrencyInstance(Locale("US", "en")).format(item.price))

        viewHolder.cart_item_delete.setOnClickListener {
            cart_items.remove(item)
            notifyDataSetChanged()

            if (item is Drink) {
                onItemClick?.let { it1 -> it1(item as Drink) }
            }

            if (item is Pizza) {
                onItemClick?.let { it1 -> it1(item as Pizza) }
            }

        }
    }

    fun setData(pizzas: MutableList<Pizza>,drinks: MutableList<Drink>){
        cart_items.clear()
        cart_items.addAll(pizzas)
        cart_items.addAll(drinks)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cart_items.size
    }

    class CartListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var cart_item_name: TextView
        var cart_item_price: TextView
        var cart_item_delete: ImageButton

        init {
            cart_item_name = itemView.findViewById<View>(R.id.ingredientName) as TextView
            cart_item_price = itemView.findViewById<View>(R.id.ingredientPrice) as TextView
            cart_item_delete = itemView.findViewById<View>(R.id.cartListDelete) as ImageButton
        }
    }

    init {
        cart_items.addAll(pizzas)
        cart_items.addAll(drinks)
    }
}