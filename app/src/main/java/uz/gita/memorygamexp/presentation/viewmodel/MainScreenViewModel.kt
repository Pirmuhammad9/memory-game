package uz.gita.memorygamexp.presentation.viewmodel

import androidx.lifecycle.LiveData

interface MainScreenViewModel {

    val openMultiPlayerScreenLiveData: LiveData<Unit>
    val openCategoryScreenLiveData:LiveData<Unit>
    val openGameScreenLiveData:LiveData<Unit>
    val backPressedLiveData:LiveData<Unit>

    fun openCategoryScreen()
    fun openGameScreen()
    fun openMultiPlayerScreen()
    fun onBackPressed()

}