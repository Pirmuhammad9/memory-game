package uz.gita.memorygamexp.presenter.viewmodel

import androidx.lifecycle.LiveData

interface GameScreenViewModel {
    val getListLiveData: LiveData<List<Int>>
    val getLevelLiveData: LiveData<Int>
    val timeLiveData:LiveData<Int>
    val looseLiveData:LiveData<Unit>
    var calculate:Boolean
    fun startTimer(string: String)
    fun getList()
    fun setLevel()
    fun setDefaultLevel()
    fun cancelTimer()
}