package uz.gita.memorygamexp.domain.usecase.impl

import uz.gita.memorygamexp.domain.repository.Repository
import uz.gita.memorygamexp.domain.usecase.GameScreenUsecase
import javax.inject.Inject

class GameScreenUsecaseImpl @Inject constructor(private val repository: Repository) :
    GameScreenUsecase {
    override suspend fun getLevel(): Int = repository.getLevel()

    override suspend fun getItems(): List<Int> = repository.getItems()

    override suspend fun setLevel() = repository.setLevel()

    override suspend fun setDefaultLevel() = repository.setDefaultLevel()
}