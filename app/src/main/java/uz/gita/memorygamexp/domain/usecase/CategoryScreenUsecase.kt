package uz.gita.memorygamexp.domain.usecase

import uz.gita.memorygamexp.data.model.Category

interface CategoryScreenUsecase {

    suspend fun setCategory(category: Category)
    suspend fun getCategory():String

}