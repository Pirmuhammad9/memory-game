package uz.gita.memorygamexp.presenter.screen.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.GameDialogBinding
import uz.gita.memorygamexp.presenter.viewmodel.GameDialogViewModel
import uz.gita.memorygamexp.presenter.viewmodel.impl.GameDialogViewModelImpl

@AndroidEntryPoint
class GameDialog : DialogFragment(R.layout.game_dialog) {

    private val binding by viewBinding(GameDialogBinding::bind)
    private val viewModel:GameDialogViewModel by viewModels<GameDialogViewModelImpl>()
    private var soundClickListener: ((Boolean) -> Unit)? = null
    private var menuClickListener: (() -> Unit)? = null
    private var newGameClickListener: (() -> Unit)? = null
    private var onDismissedListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        viewModel.setSoundLiveData.observe(viewLifecycleOwner, soundObserver)
        sound.setOnClickListener {
            if (soundText.text.endsWith("ON")) {
               viewModel.setSound("SOUND OFF")
                soundClickListener?.invoke(false)
            } else {
                viewModel.setSound("SOUND ON")
                soundClickListener?.invoke(true)
            }
        }
        menu.setOnClickListener {
            menuClickListener?.invoke()
            dismiss()
        }
        newGame.setOnClickListener {
            newGameClickListener?.invoke()
            dismiss()
        }
    }

    private val soundObserver = Observer<String>{
        binding.soundText.text = it
    }

    fun setSound(bl: (Boolean) -> Unit) {
        soundClickListener = bl
    }

    fun setReview(bl: () -> Unit) {
        menuClickListener = bl
    }

    fun setInfo(bl: () -> Unit) {
        newGameClickListener = bl
    }

    fun setOnDismissListener(bl:()->Unit){
        onDismissedListener = bl
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDismissedListener?.invoke()
    }

}