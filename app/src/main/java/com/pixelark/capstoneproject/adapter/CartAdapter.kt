package com.pixelark.capstoneproject.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.CartProductItemBinding
import com.pixelark.capstoneproject.util.Constants

class CartAdapter constructor(
    private val cartProductList: MutableList<ProductModel>,
    private val cartClickListener: CartClickListener,
    private val cartDeleteClickListener: CartDeleteClickListener
) : RecyclerView.Adapter<CartAdapter.CartHolder>() {

    class CartHolder(val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding =
            CartProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val cartProducts = cartProductList[position]

        Glide.with(holder.itemView.context)
            .load(cartProducts.imageOne)
            .into(holder.binding.cartProductItemIvProductImage)
        holder.binding.cartProductItemTvProductName.text = cartProducts.title
        holder.binding.cartProductItemTvProductPrice.text =
            cartProducts.price.toString() + Constants.TextPicker.CURRENCY
        holder.binding.cartProductItemTvProductSalePrice.text =
            cartProducts.salePrice.toString() + Constants.TextPicker.CURRENCY

        holder.binding.cartProductItemTvProductSalePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener {
            cartClickListener.onClick(cartProducts)
        }

        holder.binding.cartProductItemIvDelete.setOnClickListener() {
            cartDeleteClickListener.onClickDelete(cartProducts)

            cartProductList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartProductList.size)
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        cartProductList.clear()
        notifyDataSetChanged()
    }
}

interface CartClickListener {
    fun onClick(selectedProduct: ProductModel)
}

interface CartDeleteClickListener {
    fun onClickDelete(selectedProduct: ProductModel)
}