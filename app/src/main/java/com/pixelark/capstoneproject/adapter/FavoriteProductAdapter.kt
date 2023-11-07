package com.pixelark.capstoneproject.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FavoriteItemBinding
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency

class FavoriteProductAdapter constructor(
    private val productList: List<ProductModel>,
    private val favoriteProductClickListener: FavoriteProductClickListener
) : RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductHolder>() {

    class FavoriteProductHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: FavoriteProductHolder, position: Int) {
        val products = productList[position]

        Glide.with(holder.itemView.context)
            .load(products.imageOne)
            .into(holder.binding.favoriteItemIvProductImage)
        holder.binding.favoriteItemTvProductName.text = products.title
        holder.binding.favoriteItemTvProductPrice.text =
            products.getPriceWithCurrency(Constants.Currency.TL)

        if (products.saleState == true) {
            holder.binding.favoriteItemTvProductPrice.setTextColor(Color.RED)
            holder.binding.favoriteItemTvProductSalePrice.text =
                products.getSalePriceWithCurrency(Constants.Currency.TL)
            holder.binding.favoriteItemTvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.itemView.setOnClickListener {
            favoriteProductClickListener.onClick(products)
        }
    }
}

interface FavoriteProductClickListener {
    fun onClick(selectedProduct: ProductModel)
}