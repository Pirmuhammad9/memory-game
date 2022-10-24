package uz.gita.memorygamexp.presenter.viewmodel

import androidx.lifecycle.LiveData

interface GameDialogViewModel {
    val setSoundLiveData:LiveData<String>
    fun setSound(sound: String)
}