package uz.gita.memorygamexp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.memorygamexp.data.model.Player
import uz.gita.memorygamexp.domain.usecase.MultiPlayerScreenUsecase
import uz.gita.memorygamexp.presentation.viewmodel.MultiPlayerScreenViewModel
import javax.inject.Inject

@HiltViewModel
class MultiPlayerScreenViewModelImpl @Inject constructor(private val usecase: MultiPlayerScreenUsecase) :
    MultiPlayerScreenViewModel, ViewModel() {
    override val getDataLiveData = MutableLiveData<List<Int>>()
    override val showTurnLiveData = MutableLiveData<Player>()
    override val showWinnerLiveData = MutableLiveData<Player>()
    override fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = usecase.getItems()
            getDataLiveData.postValue(items)
        }
    }
    override fun showWinner(player: Player) {
        when (player) {
            Player.FIRST_PLAYER -> showTurnLiveData.value = Player.FIRST_PLAYER
            else -> Player.SECOND_PLAYER
        }
    }

    override fun firstPlayerTurn() {
        showTurnLiveData.value = Player.FIRST_PLAYER
    }

    override fun secondPlayerTurn() {
        showTurnLiveData.value = Player.SECOND_PLAYER
    }
}