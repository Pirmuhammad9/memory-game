package uz.gita.memorygamexp.domain.usecase.impl

import uz.gita.memorygamexp.data.model.Category
import uz.gita.memorygamexp.domain.repository.Repository
import uz.gita.memorygamexp.domain.usecase.CategoryScreenUsecase
import javax.inject.Inject

class CategoryScreenUsecaseImpl @Inject constructor(private val repository: Repository) :
    CategoryScreenUsecase {
    override suspend fun setCategory(category: Category) {
        repository.setCategory(category)
    }
    override suspend fun getCategory(): String = repository.getCategory()

}