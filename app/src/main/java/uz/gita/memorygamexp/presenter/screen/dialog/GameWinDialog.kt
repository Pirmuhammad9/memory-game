package uz.gita.memorygamexp.presenter.screen.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.GameWinDialogBinding

class GameWinDialog(private var winner: String = "", private var exitText: String = "") :
    DialogFragment(R.layout.game_win_dialog) {
    private val binding by viewBinding(GameWinDialogBinding::bind)
    private var onRefreshClick: (() -> Unit)? = null
    private var onContinueClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        no.setOnClickListener {
            dismiss()
            childFragmentManager.popBackStack()
            onRefreshClick?.invoke()
        }
        yes.setOnClickListener {
            dismiss()
            childFragmentManager.popBackStack()
            onContinueClick?.invoke()
        }
        if (exitText.isNotEmpty()) {
            setExitText()
        }
        if (winner.isNotEmpty()) {
            setText(winner)
        }
    }

    fun setOnRefreshClickListener(bl: () -> Unit) {
        onRefreshClick = bl
    }

    fun setOnContinueClickListener(bl: () -> Unit) {
        onContinueClick = bl
    }

    private fun setText(string: String) {
        binding.text.text = string
    }

    private fun setExitText() {
        binding.exit.text = "Exit"
    }

}