package com.doclerholding.nenospizza.data.beans
import com.google.gson.annotations.SerializedName

open class CartItem (type: String){
    @SerializedName("price") var price : Double? = null
    @SerializedName("name") var name : String? = null
    var type : String? = type
}