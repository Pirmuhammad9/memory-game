package uz.gita.memorygamexp.presentation.screen

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import uz.gita.memorygamexp.R
import uz.gita.memorygamexp.data.model.Player
import uz.gita.memorygamexp.databinding.ScreenMultiplayerBinding
import uz.gita.memorygamexp.presentation.screen.dialog.ExitDialog
import uz.gita.memorygamexp.presentation.screen.dialog.GameWinDialog
import uz.gita.memorygamexp.presentation.viewmodel.MultiPlayerScreenViewModel
import uz.gita.memorygamexp.presentation.viewmodel.impl.MultiPlayerScreenViewModelImpl
import uz.gita.memorygamexp.utils.Music
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MultiPlayerScreen : Fragment(R.layout.screen_multiplayer) {
    private val binding by viewBinding(ScreenMultiplayerBinding::bind)
    private val viewModel: MultiPlayerScreenViewModel by viewModels<MultiPlayerScreenViewModelImpl>()
    private var views = ArrayList<AppCompatImageView>()
    private var openImagecount = 0
    private var currentTurn = Player.FIRST_PLAYER
    private var sound = true
    private val exitDialog by lazy(LazyThreadSafetyMode.NONE) { ExitDialog() }
    private lateinit var winningDialog: GameWinDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitDialog.setOnYesClickLister {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            exitDialog.show(childFragmentManager, "")
        }
        viewModel.getData()
        viewModel.getDataLiveData.observe(viewLifecycleOwner, listObserver)
        back.setOnClickListener {
            exitDialog.show(childFragmentManager, "")
        }
    }

    private val listObserver = Observer<List<Int>> {
        binding.base.post {
            loadView(binding.base.height, binding.base.width)
            for (i in it.indices) {
                views[i].tag = it[i]
                views[i].setOnClickListener {
                    if (views[i].rotationY == 180f) closeImage(views[i])
                    else {
                        if (openImagecount == 0) openImage(views[i])
                        else {
                            if (openImagecount == 1) {
                                openImage(views[i])
                                lifecycleScope.launch {
                                    delay(500)
                                    val image = getOpenImage(views[i])
                                    if (image?.tag == views[i].tag) {
                                        makeImageInvisible(image!!, views[i])
                                        showCurrentPlayer()
                                        givePoint()
                                    } else {
                                        changeTurn(currentTurn)
                                    }
                                    close()
                                    if (checkForWin()) {
                                        binding.konfet.start(
                                            Party(
                                                speed = 0f,
                                                maxSpeed = 30f,
                                                damping = 0.9f,
                                                spread = 360,
                                                colors = listOf(
                                                    0xfce18a,
                                                    0xff726d,
                                                    0xf4306d,
                                                    0xb48def
                                                ),
                                                emitter = Emitter(
                                                    duration = 500,
                                                    TimeUnit.MILLISECONDS
                                                ).max(300),
                                                position = Position.Relative(0.5, 0.5)
                                            )
                                        )
                                        Music.player = null
                                        if (sound) Music.getMusicPlayer(
                                            requireContext(),
                                            R.raw.congrat
                                        )
                                            .apply {
                                                if (isPlaying) stop()
                                                start()
                                            }
                                        Music.player = null
                                        congratulateWinner()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadView(heightImage: Int, widthImage: Int) {
        showCurrentPlayer()
        views.clear()
        binding.relativeLayout.removeAllViews()
        binding.relativeLayout.layoutParams.apply {
            height = (heightImage / (10)) * 6
            width = (widthImage / (5)) * 4 + 16 * (5)
        }

        for (i in 0 until 4) {
            for (j in 0 until 6) {
                val image = AppCompatImageView(requireContext())
                image.x = (i * (widthImage / (5))).toFloat() + (i + 1) * 16
                image.y = (j * heightImage / (10)).toFloat()
                binding.relativeLayout.addView(image)
                val layoutParameters = image.layoutParams as RelativeLayout.LayoutParams
                layoutParameters.apply {
                    height = heightImage / (10)
                    width = widthImage / (5)
                }
                image.setImageResource(R.drawable.card_back)
                image.scaleType = ImageView.ScaleType.FIT_XY
                image.layoutParams = layoutParameters
                views.add(image)
            }
        }
    }


    private fun openImage(image: AppCompatImageView) {
        if (!binding.playerTurn.isVisible) {
            if (sound) Music.getMusicPlayer(requireContext(), R.raw.click).apply {
                if (isPlaying) stop()
                start()
            }
            image.isClickable = false
            image.animate().setDuration(200).rotationY(90f).withEndAction {
                image.setImageResource(image.tag as Int)
                image.animate().setDuration(200).rotationY(180f).interpolator =
                    DecelerateInterpolator()
                image.isClickable = true
            }
            openImagecount++
        }
    }

    private fun closeImage(image: AppCompatImageView) {
        image.animate().setDuration(200).rotationY(90f).withEndAction {
            image.setImageResource(R.drawable.card_back)
            image.animate().setDuration(200).rotationY(0f).interpolator = DecelerateInterpolator()
        }
        if (openImagecount > 0) openImagecount--
    }

    private fun getOpenImage(image: AppCompatImageView): AppCompatImageView? {
        for (view in views) {
            if (view.visibility == View.VISIBLE && view.rotationY == 180f && view != image) return view
        }
        return null
    }

    private fun makeImageInvisible(
        firstImage: AppCompatImageView,
        secondImage: AppCompatImageView
    ) {
        Music.player = null
        if (sound) Music.getMusicPlayer(requireContext(), R.raw.correct).apply {
            if (isPlaying) stop()
            start()
            Music.player = null
        }
        firstImage.visibility = View.GONE
        secondImage.visibility = View.GONE
    }

    private fun close() {
        for (i in views.indices) {
            if (views[i].rotationY == 180f) {
                closeImage(views[i])
            }
        }
        openImagecount = 0
    }

    private fun checkForWin(): Boolean {
        for (view in views) {
            if (view.visibility == View.VISIBLE) return false
        }
        return true
    }

    private fun showCurrentPlayer() = with(binding) {
        playerTurn.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(1000)
            playerTurn.visibility = View.GONE
        }
    }

    private fun changeTurn(player: Player) = with(binding) {
        playerTurn.visibility = View.VISIBLE
        if (player == Player.FIRST_PLAYER) {
            currentTurn = Player.SECOND_PLAYER
            playerTurn.setImageResource(R.drawable.turnchange_1)
        } else {
            currentTurn = Player.FIRST_PLAYER
            playerTurn.setImageResource(R.drawable.turnchange_0)
        }
        lifecycleScope.launch {
            delay(1000)
            playerTurn.visibility = View.GONE
        }
    }

    private fun givePoint() = with(binding) {
        when (currentTurn) {
            Player.FIRST_PLAYER -> {
                firstPlayerPoint.text = (firstPlayerPoint.text.toString().toInt() + 10).toString()
            }
            else -> {
                secondPlayerPoint.text = (secondPlayerPoint.text.toString().toInt() + 10).toString()
            }
        }
    }

    private fun congratulateWinner() {
        val pointFirst = binding.firstPlayerPoint.text.toString().toInt()
        val pointSecond = binding.secondPlayerPoint.text.toString().toInt()
        val winner = when {
            pointFirst > pointSecond -> "First player win"
            pointFirst == pointSecond -> "Draw"
            else -> "Second player win"
        }
        winningDialog = GameWinDialog(winner, "Exit")
        winningDialog.setOnContinueClickListener {
            viewModel.getData()
            openImagecount = 0
            currentTurn = Player.FIRST_PLAYER
            binding.firstPlayerPoint.text = "0"
            binding.secondPlayerPoint.text = "0"
        }
        winningDialog.setOnRefreshClickListener {
            findNavController().popBackStack()
        }
        winningDialog.show(childFragmentManager, "")
    }

}
