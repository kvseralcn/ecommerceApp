package com.pixelark.capstoneproject.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.ProductsByCategoryItemBinding
import com.pixelark.capstoneproject.util.Constants

class ProductsByCategoryAdapter constructor(
    private val productList: List<ProductModel>,
    private val productCategoryClickListener: ProductCategoryClickListener
) : RecyclerView.Adapter<ProductsByCategoryAdapter.CategoryHolder>() {

    class CategoryHolder(val binding: ProductsByCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            ProductsByCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
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
            productCategoryClickListener.onCategoryClick(products)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

interface ProductCategoryClickListener {
    fun onCategoryClick(selectedProduct: ProductModel)
}