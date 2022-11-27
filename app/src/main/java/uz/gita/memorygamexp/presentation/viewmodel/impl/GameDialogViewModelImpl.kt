package uz.gita.memorygamexp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygamexp.presentation.viewmodel.GameDialogViewModel
import javax.inject.Inject

@HiltViewModel
class GameDialogViewModelImpl @Inject constructor(): GameDialogViewModel, ViewModel() {
    override val setSoundLiveData = MutableLiveData<String>()
    init {
        setSoundLiveData.value = "SOUND ON"
    }
    override fun setSound(sound: String) {
        setSoundLiveData.value = sound
    }
}