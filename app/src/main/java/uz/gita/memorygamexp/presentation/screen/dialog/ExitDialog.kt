package uz.gita.memorygamexp.presentation.screen.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.ExitDialogBinding

@AndroidEntryPoint
class ExitDialog : DialogFragment(R.layout.exit_dialog) {

    val binding by viewBinding(ExitDialogBinding::bind)
    private var setOnCancelClick: (() -> Unit)? = null
    private var setOnYesClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.no.setOnClickListener {
            setOnCancelClick?.invoke()
            this.dismiss()
        }
        binding.yes.setOnClickListener {
            setOnYesClick?.invoke()
        }
    }

    fun setOnYesClickLister(bl: () -> Unit) {
        setOnYesClick = bl
    }

    fun setOnCancelClickLister(bl: () -> Unit) {
        setOnCancelClick = bl
    }


}