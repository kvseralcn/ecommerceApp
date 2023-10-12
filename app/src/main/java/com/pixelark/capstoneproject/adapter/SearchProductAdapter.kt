package com.pixelark.capstoneproject.adapter

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
    private val searchProductList: List<ProductModel>,
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
            .into(holder.binding.saleProductIvProductImage)
        holder.binding.saleProductTvProductName.text = products.title
        holder.binding.saleProductTvProductPrice.text =
            products.getPriceWithCurrency(Constants.Currency.TL)
        holder.binding.saleProductTvProductSalePrice.text =
            products.getSalePriceWithCurrency(Constants.Currency.TL)

        holder.binding.saleProductTvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener {
            searchProductClickListener.onSearchClick(products)
        }
    }
}

interface SearchProductClickListener {
    fun onSearchClick(selectedProduct: ProductModel)
}