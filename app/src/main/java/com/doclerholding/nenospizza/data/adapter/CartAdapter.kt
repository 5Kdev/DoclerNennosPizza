package com.doclerholding.nenospizza.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.beans.CartItem
import com.doclerholding.nenospizza.data.beans.Drink
import com.doclerholding.nenospizza.data.beans.Pizza


class CartAdapter(pizzas: MutableList<Pizza>,drinks: MutableList<Drink>) : RecyclerView.Adapter<CartAdapter.CartListViewHolder>() {
    private var cartItems: MutableList<CartItem> = mutableListOf()
    internal var onItemClick: ((Any) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.cart_list_item, parent, false)
        return CartListViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CartListViewHolder, position: Int) {
        val item: CartItem = cartItems[position]
        viewHolder.cart_item_name.text = item.name
        viewHolder.cart_item_price.text = "$"+ item.price.toString()
        viewHolder.cart_item_delete.isClickable=false
        viewHolder.cart_item_container.setOnClickListener {
            cartItems.remove(item)
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
        cartItems.clear()
        cartItems.addAll(pizzas)
        cartItems.addAll(drinks)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    class CartListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var cart_item_name: TextView
        var cart_item_price: TextView
        var cart_item_delete: ImageButton
        var cart_item_container: RelativeLayout

        init {
            cart_item_name = itemView.findViewById<View>(R.id.ingredientName) as TextView
            cart_item_price = itemView.findViewById<View>(R.id.ingredientPrice) as TextView
            cart_item_delete = itemView.findViewById<View>(R.id.cartListDelete) as ImageButton
            cart_item_container = itemView.findViewById<View>(R.id.cartItemContainer) as RelativeLayout

        }
    }

    init {
        cartItems.addAll(pizzas)
        cartItems.addAll(drinks)
    }
}