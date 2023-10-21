package com.pixelark.capstoneproject.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.ProductsItemBinding
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency

class ProductAdapter constructor(
    private val productList: List<ProductModel>,
    private val productClickListener: FavoriteProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    class ProductHolder(val binding: ProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding =
            ProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val products = productList[position]

        Glide.with(holder.itemView.context)
            .load(products.imageOne)
            .into(holder.binding.productsItemIvProductImage)
        holder.binding.productsItemTvProductName.text = products.title
        holder.binding.productsItemTvProductPrice.text =
            products.getPriceWithCurrency(Constants.Currency.TL)

        if (products.saleState == true) {
            holder.binding.productsItemTvProductPrice.setTextColor(Color.RED)
            holder.binding.productsItemTvProductSalePrice.text =
                products.getSalePriceWithCurrency(Constants.Currency.TL)
            holder.binding.productsItemTvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.itemView.setOnClickListener {
            productClickListener.onClick(products)
        }
    }
}

interface ProductClickListener {
    fun onClick(selectedProduct: ProductModel)
}