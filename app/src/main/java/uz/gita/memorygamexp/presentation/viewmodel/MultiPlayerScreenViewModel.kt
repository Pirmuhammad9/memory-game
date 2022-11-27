package uz.gita.memorygamexp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memorygamexp.data.model.Player

interface MultiPlayerScreenViewModel {

    val getDataLiveData:LiveData<List<Int>>
    val showTurnLiveData:LiveData<Player>
    val showWinnerLiveData:LiveData<Player>

    fun getData()
    fun showWinner(player: Player)
    fun firstPlayerTurn()
    fun secondPlayerTurn()

}