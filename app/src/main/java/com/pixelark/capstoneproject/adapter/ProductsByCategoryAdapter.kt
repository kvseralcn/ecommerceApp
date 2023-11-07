package com.pixelark.capstoneproject.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.ProductsByCategoryItemBinding
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency

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
            .into(holder.binding.productsByCategoryItemIvProductImage)
        holder.binding.productsByCategoryItemTvProductName.text = products.title
        holder.binding.productsByCategoryItemTvProductPrice.text =
            products.getPriceWithCurrency(Constants.Currency.TL)

        if (products.saleState == true) {
            holder.binding.productsByCategoryItemTvProductPrice.setTextColor(Color.RED)
            holder.binding.productsByCategoryItemTvProductSalePrice.text =
                products.getSalePriceWithCurrency(Constants.Currency.TL)
            holder.binding.productsByCategoryItemTvProductPrice.paintFlags =
                Paint.STRIKE_THRU_TEXT_FLAG
        }

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