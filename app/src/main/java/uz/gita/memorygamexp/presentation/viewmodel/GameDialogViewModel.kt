package uz.gita.memorygamexp.presentation.viewmodel

import androidx.lifecycle.LiveData

interface GameDialogViewModel {
    val setSoundLiveData:LiveData<String>
    fun setSound(sound: String)
}