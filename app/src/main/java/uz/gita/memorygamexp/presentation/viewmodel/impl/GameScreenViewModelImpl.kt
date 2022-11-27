package uz.gita.memorygamexp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import uz.gita.memorygamexp.domain.usecase.GameScreenUsecase
import uz.gita.memorygamexp.presentation.viewmodel.GameScreenViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModelImpl @Inject constructor(private val usecase: GameScreenUsecase) :
    GameScreenViewModel, ViewModel() {
    override val getListLiveData = MutableLiveData<List<Int>>()
    override val getLevelLiveData = MutableLiveData<Int>()
    override val timeLiveData = MutableLiveData<Int>()
    override val looseLiveData = MutableLiveData<Unit>()
    override var calculate = false
    private var job = viewModelScope.launch {  }

    override fun startTimer(string: String) {
        Log.d("WWW", "STR->$string")
        when (string) {
            "Easy" -> {
                start(30, 4)
            }
            "Medium" -> {
                start(60, 2)
            }
            else -> {
                start(120, 1)
            }
        }
    }

    override fun getList() {
        viewModelScope.launch(Dispatchers.IO) {
            getLevelLiveData.postValue(usecase.getLevel())
            getListLiveData.postValue(usecase.getItems())
        }
    }

    override fun setLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.setLevel()
            getList()
        }
    }

    override fun setDefaultLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.setDefaultLevel()
            getList()
        }
    }

    override fun cancelTimer() {
        job.cancel()
    }

    private fun start(count: Int, value: Int) {
        cancelTimer()
        job = viewModelScope.launch(Dispatchers.IO) {
            for (i in 0 until count) {
                while (calculate) {
                }
                delay(1000)
                timeLiveData.postValue(value)
                if (i == count-1) {
                    looseLiveData.postValue(Unit)
                }
            }
        }
    }
}