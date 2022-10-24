package uz.gita.memorygamexp.domain.usecase

interface GameScreenUsecase {

    suspend fun getLevel():Int

    suspend fun getItems():List<Int>

    suspend fun setLevel()

    suspend fun setDefaultLevel()

}