package uz.gita.memorygamexp.domain.repository

import uz.gita.memorygamexp.data.model.Category

interface Repository {

    suspend fun setIndex(int: Int)

    suspend fun getItems(): List<Int>

    suspend fun setCategory(category: Category)

    suspend fun getCategory(): String

    suspend fun getLevel(): Int

    suspend fun setLevel()

    suspend fun getItemsForMultiPlayer(): List<Int>

    suspend fun setDefaultLevel()

}