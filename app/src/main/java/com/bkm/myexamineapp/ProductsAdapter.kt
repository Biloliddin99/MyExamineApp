package com.bkm.myexamineapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bkm.myexamineapp.databinding.ItemRvBinding
import com.bkm.myexamineapp.models.Product
import com.bkm.myexamineapp.network.ApiClient
import com.squareup.picasso.Picasso

class ProductsAdapter( val list: ArrayList<Product> = ArrayList()) : RecyclerView.Adapter<ProductsAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(product: Product) {
            itemRvBinding.tvName.text = product.name
            itemRvBinding.tvPrice.text = "${product.price} UZS"
            Picasso.get().load("${ApiClient.PHOTO_BASE_URL}${product.photo_link}").into(itemRvBinding.imageUser)
            itemRvBinding.tvId.text = product.id.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

}