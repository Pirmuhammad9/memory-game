package uz.gita.memorygamexp.presenter.viewmodel

import androidx.lifecycle.LiveData

interface SplashScreenViewModel {
    val openNextScreenLiveData: LiveData<Unit>
}