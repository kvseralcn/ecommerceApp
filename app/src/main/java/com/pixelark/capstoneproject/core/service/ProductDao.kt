package com.pixelark.capstoneproject.core.service

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.pixelark.capstoneproject.core.data.ProductModel

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductModel)

    @Query("SELECT * FROM favorites")
    fun getFavoriteProducts(): List<ProductModel>

    @Query("DELETE FROM favorites WHERE id = :productId")
    fun deleteProduct(productId: Int)
}
