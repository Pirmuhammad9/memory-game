package uz.gita.memorygamexp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.memorygamexp.data.model.Category
import uz.gita.memorygamexp.domain.usecase.CategoryScreenUsecase
import uz.gita.memorygamexp.presentation.viewmodel.CategoryScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModelImpl @Inject constructor(private val usecase: CategoryScreenUsecase) :
    CategoryScreenViewModel, ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val category = usecase.getCategory()
            getCategoryLiveData.postValue(category?:"FIRST")
        }
    }

    override val backButtonLiveData = MutableLiveData<Unit>()
    override val getCategoryLiveData = MutableLiveData<String>()

    override fun setCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.setCategory(category)
        }
    }

    override fun backPressed() {
        backButtonLiveData.value = Unit
    }
}