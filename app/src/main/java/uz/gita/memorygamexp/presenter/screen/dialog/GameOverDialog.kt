package uz.gita.memorygamexp.presenter.screen.dialog

import android.os.Bundle
import android.view.View
import androidx.core.view.ContentInfoCompat
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.GameOverDialogBinding

class GameOverDialog : DialogFragment(R.layout.game_over_dialog) {

    private val binding by viewBinding(GameOverDialogBinding::bind)
    private var onExitClickListener: (() -> Unit)? = null
    private var onContinueClickListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        no.setOnClickListener {
            dismiss()
            onExitClickListener?.invoke()
        }
        yes.setOnClickListener {
            dismiss()
            onContinueClickListener?.invoke()
        }
    }

    fun setOnExitClickListener(bl: () -> Unit) {
        onExitClickListener = bl
    }

    fun setOnContinueClickListener(bl: () -> Unit) {
        onContinueClickListener = bl
    }

}