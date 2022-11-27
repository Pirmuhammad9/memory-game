package uz.gita.memorygamexp.presentation.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.ScreenMenuBinding
import uz.gita.memorygamexp.presentation.screen.dialog.BaseDialog
import uz.gita.memorygamexp.presentation.screen.dialog.ExitDialog
import uz.gita.memorygamexp.presentation.viewmodel.MainScreenViewModel
import uz.gita.memorygamexp.presentation.viewmodel.impl.MainScreenViewModelImpl
import uz.gita.memorygamexp.utils.isNetworkAvailable

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_menu) {

    private val binding by viewBinding(ScreenMenuBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val baseDialog by lazy(LazyThreadSafetyMode.NONE) { BaseDialog() }
    private val exitDialog by lazy(LazyThreadSafetyMode.NONE) { ExitDialog() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openCategoryScreenLiveData.observe(this@MainScreen, openCategoryScreenObserver)
        viewModel.openMultiPlayerScreenLiveData.observe(
            this@MainScreen,
            openMultiPlayerScreenObserver
        )
        viewModel.openGameScreenLiveData.observe(this@MainScreen, openGameScreenObserver)
        viewModel.backPressedLiveData.observe(this@MainScreen, onBackPressedObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showExitDialog()
        }
        play.setOnClickListener {
            viewModel.openGameScreen()
        }
        stage.setOnClickListener {
            viewModel.openCategoryScreen()
        }
        multiPlayer.setOnClickListener {
            viewModel.openMultiPlayerScreen()
        }
        back.setOnClickListener {
            showExitDialog()
        }
        settings.setOnClickListener {
            baseDialog.show(childFragmentManager, "")
            baseDialog.setTelegram {
                if (isNetworkAvailable(requireContext())) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://en.m.wikipedia.org/wiki/Memory_game")
                    startActivity(intent)
                } else {
                    showMessage()
                }
            }
            baseDialog.setReview {
                if (isNetworkAvailable(requireContext())) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://t.me/Pirmuhammad1")
                    startActivity(intent)
                } else {
                    showMessage()
                }
            }
            baseDialog.setInfo {
                baseDialog.dismiss()
            }
        }

    }

    private val openGameScreenObserver = Observer<Unit> {
        findNavController().navigate(MainScreenDirections.actionMainScreenToGameScreen())
    }

    private val openMultiPlayerScreenObserver = Observer<Unit> {
        findNavController().navigate(MainScreenDirections.actionMainScreenToMultiPlayerScreen())
    }

    private val openCategoryScreenObserver = Observer<Unit> {
        findNavController().navigate(MainScreenDirections.actionMainScreenToCategoryScreen())
    }

    private val onBackPressedObserver = Observer<Unit> {
        requireActivity().finish()
    }

    private fun showMessage() {
        Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_SHORT)
            .show()
    }

    private fun showExitDialog() {
        val dialog = exitDialog
        dialog.show(childFragmentManager, "")
        dialog.setOnCancelClickLister {
            dialog.dismiss()
        }
        dialog.setOnYesClickLister {
            dialog.dismiss()
            viewModel.onBackPressed()
        }
    }
}