package com.pixelark.capstoneproject.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.SearchItemBinding
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency

class SearchProductAdapter constructor(
    private val searchProductList: MutableList<ProductModel>,
    private val searchProductClickListener: SearchProductClickListener
) : RecyclerView.Adapter<SearchProductAdapter.SearchProductHolder>() {

    class SearchProductHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductHolder {
        val binding =
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchProductList.size
    }

    override fun onBindViewHolder(holder: SearchProductHolder, position: Int) {
        val products = searchProductList[position]

        Glide.with(holder.itemView.context)
            .load(products.imageOne)
            .into(holder.binding.searchItemIvProductImage)
        holder.binding.searchItemTvProductName.text = products.title
        holder.binding.searchItemTvProductPrice.text =
            products.getPriceWithCurrency(Constants.Currency.TL)

        if (products.saleState == true) {
            holder.binding.searchItemTvProductPrice.setTextColor(Color.RED)
            holder.binding.searchItemTvProductSalePrice.text =
                products.getSalePriceWithCurrency(Constants.Currency.TL)
            holder.binding.searchItemTvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.itemView.setOnClickListener {
            searchProductClickListener.onSearchClick(products)
        }
    }

    fun clearList() {
        searchProductList.clear()
        notifyDataSetChanged()
    }
}

interface SearchProductClickListener {
    fun onSearchClick(selectedProduct: ProductModel)
}