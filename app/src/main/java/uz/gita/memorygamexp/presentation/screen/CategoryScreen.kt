package uz.gita.memorygamexp.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.data.model.Category
import uz.gita.memorygamexp.databinding.ScreenCategoryBinding
import uz.gita.memorygamexp.presentation.viewmodel.CategoryScreenViewModel
import uz.gita.memorygamexp.presentation.viewmodel.impl.CategoryScreenViewModelImpl

@AndroidEntryPoint
class CategoryScreen : Fragment(R.layout.screen_category) {
    private val binding by viewBinding(ScreenCategoryBinding::bind)
    private val viewModel: CategoryScreenViewModel by viewModels<CategoryScreenViewModelImpl>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backButtonLiveData.observe(this@CategoryScreen, backPressedObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        viewModel.getCategoryLiveData.observe(viewLifecycleOwner, categoryObserver)
        first.setOnClickListener {
            if (!first.isSelected) {
                setSelectedFalse()
                first.isSelected = true
                viewModel.setCategory(Category.FIRST)
            }
        }
        second.setOnClickListener {
            if (!second.isSelected) {
                setSelectedFalse()
                second.isSelected = true
                viewModel.setCategory(Category.SECOND)
            }
        }
        third.setOnClickListener {
            if (!third.isSelected) {
                setSelectedFalse()
                third.isSelected = true
                viewModel.setCategory(Category.THIRD)
            }
        }
        fourth.setOnClickListener {
            if (!fourth.isSelected) {
                setSelectedFalse()
                fourth.isSelected = true
                viewModel.setCategory(Category.FOURTH)
            }
        }
        back.setOnClickListener {
            viewModel.backPressed()
        }
    }

    private val backPressedObserver = Observer<Unit> {
        findNavController().popBackStack()
    }

    private val categoryObserver = Observer<String> {
        when (it) {
            "FIRST" -> binding.first.isSelected = true
            "SECOND" -> binding.second.isSelected = true
            "THIRD" -> binding.third.isSelected = true
            else -> binding.fourth.isSelected = true
        }
    }

    private fun setSelectedFalse() = with(binding) {
        first.isSelected = false
        second.isSelected = false
        third.isSelected = false
        fourth.isSelected = false
    }

}