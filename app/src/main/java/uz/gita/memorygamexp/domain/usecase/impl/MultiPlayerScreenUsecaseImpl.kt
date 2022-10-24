package uz.gita.memorygamexp.domain.usecase.impl

import uz.gita.memorygamexp.domain.repository.Repository
import uz.gita.memorygamexp.domain.usecase.MultiPlayerScreenUsecase
import javax.inject.Inject

class MultiPlayerScreenUsecaseImpl @Inject constructor(private val repository: Repository) :
    MultiPlayerScreenUsecase {
    override suspend fun getItems(): List<Int> = repository.getItemsForMultiPlayer()
}