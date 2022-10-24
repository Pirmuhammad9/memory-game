package uz.gita.memorygamexp.presenter.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygamexp.presenter.viewmodel.MainScreenViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor() : MainScreenViewModel, ViewModel() {
    override val openMultiPlayerScreenLiveData = MutableLiveData<Unit>()
    override val openCategoryScreenLiveData = MutableLiveData<Unit>()
    override val openGameScreenLiveData = MutableLiveData<Unit>()
    override val backPressedLiveData = MutableLiveData<Unit>()

    override fun openCategoryScreen() {
        openCategoryScreenLiveData.value = Unit
    }

    override fun openGameScreen() {
        openGameScreenLiveData.value = Unit
    }

    override fun openMultiPlayerScreen() {
        openMultiPlayerScreenLiveData.value = Unit
    }

    override fun onBackPressed() {
        backPressedLiveData.value = Unit
        Log.d("TTT", "VIEW MODEL")
    }
}