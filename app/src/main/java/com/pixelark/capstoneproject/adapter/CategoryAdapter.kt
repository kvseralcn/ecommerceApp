package com.pixelark.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pixelark.capstoneproject.databinding.CategoryItemBinding

class CategoryAdapter constructor(
    private val categoryList: List<String>,
    private val categoryClickListener: CategoryClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    class CategoryHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val products = categoryList[position]
        holder.binding.categoryItemTvCategory.text = products

        holder.binding.categoryItemTvCategory.setOnClickListener {
            categoryClickListener.onCategoryClick(products)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

interface CategoryClickListener {
    fun onCategoryClick(selectedProduct: String)
}