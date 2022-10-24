package uz.gita.memorygamexp.presenter.viewmodel

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