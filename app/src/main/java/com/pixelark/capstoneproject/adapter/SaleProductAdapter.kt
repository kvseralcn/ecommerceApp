package com.pixelark.capstoneproject.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.SaleProductsItemBinding
import com.pixelark.capstoneproject.util.Constants

class SaleProductAdapter constructor(
    private val productList: List<ProductModel>,
    private val productClickListener: ProductClickListener
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
            products.price.toString() + Constants.TextPicker.CURRENCY
        holder.binding.saleProductTvProductSalePrice.text =
            products.salePrice.toString() + Constants.TextPicker.CURRENCY

        holder.binding.saleProductTvProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener {
            productClickListener.onClick(products)
        }
    }
}

interface ProductClickListener {
    fun onClick(selectedProduct: ProductModel)
}