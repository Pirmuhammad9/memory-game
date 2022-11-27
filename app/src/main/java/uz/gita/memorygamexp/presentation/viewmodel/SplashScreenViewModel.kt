package uz.gita.memorygamexp.presentation.viewmodel

import androidx.lifecycle.LiveData

interface SplashScreenViewModel {
    val openNextScreenLiveData: LiveData<Unit>
}