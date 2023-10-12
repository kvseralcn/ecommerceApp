package com.pixelark.capstoneproject.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.SaleProductsItemBinding
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency

class SaleProductAdapter constructor(
    private val productList: List<ProductModel>,
    //private val searchProductClickListener: SearchProductClickListener
    private val searchProductClickListener: SaleProductClickListener
) : RecyclerView.Adapter<SaleProductAdapter.SaleProductHolder>() {

    class SaleProductHolder(val binding: SaleProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductHolder {
        val binding =
            SaleProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: SaleProductHolder, position: Int) {
        val products = productList[position]

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
            searchProductClickListener.onClick(products)
        }
    }
}

interface SaleProductClickListener {
    fun onClick(selectedProduct: ProductModel)
}