package uz.gita.memorygamexp.presenter.screen.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.databinding.BaseDialogBinding

@AndroidEntryPoint
class BaseDialog : DialogFragment(R.layout.base_dialog) {

    private val binding by viewBinding(BaseDialogBinding::bind)
    private var reviewClickListener: (() -> Unit)? = null
    private var telegramClickListener: (() -> Unit)? = null
    private var infoClickListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
        this.isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        review.setOnClickListener {
            reviewClickListener?.invoke()
        }


        telegram.setOnClickListener {
            telegramClickListener?.invoke()
        }

        info.setOnClickListener {
            infoClickListener?.invoke()
        }

    }

    fun setReview(bl: () -> Unit) {
        reviewClickListener = bl
    }

    fun setInfo(bl: () -> Unit) {
        infoClickListener = bl
    }

    fun setTelegram(bl: () -> Unit) {
        telegramClickListener = bl
    }

}