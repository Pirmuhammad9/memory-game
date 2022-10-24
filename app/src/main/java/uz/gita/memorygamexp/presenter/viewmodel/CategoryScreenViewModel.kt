package uz.gita.memorygamexp.presenter.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memorygamexp.data.model.Category

interface CategoryScreenViewModel {

    val backButtonLiveData: LiveData<Unit>
    val getCategoryLiveData: LiveData<String>

    fun setCategory(category: Category)

    fun backPressed()
}