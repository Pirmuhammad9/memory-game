package uz.gita.memorygamexp.domain.usecase

interface MultiPlayerScreenUsecase {

    suspend fun getItems():List<Int>

}