package uz.gita.memorygamexp.presenter.screen

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatImageView
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
import uz.gita.memorygamexp.databinding.ScreenGameBinding
import uz.gita.memorygamexp.presenter.screen.dialog.ExitDialog
import uz.gita.memorygamexp.presenter.screen.dialog.GameDialog
import uz.gita.memorygamexp.presenter.screen.dialog.GameOverDialog
import uz.gita.memorygamexp.presenter.screen.dialog.GameWinDialog
import uz.gita.memorygamexp.presenter.viewmodel.GameScreenViewModel
import uz.gita.memorygamexp.presenter.viewmodel.impl.GameScreenViewModelImpl
import uz.gita.memorygamexp.utils.Music
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {

    private val binding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameScreenViewModel by viewModels<GameScreenViewModelImpl>()
    private var views = ArrayList<AppCompatImageView>()
    private var x = 2
    private var y = 3
    private var openImagecount = 0
    private val dialogExit by lazy(LazyThreadSafetyMode.NONE) { ExitDialog() }
    private val dialogSetting by lazy(LazyThreadSafetyMode.NONE) { GameDialog() }
    private val gameOverDialog = GameOverDialog()
    private val gameWinDialog by lazy(LazyThreadSafetyMode.NONE) { GameWinDialog() }
    private var sound = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.looseLiveData.observe(this@GameScreen, looseObserver)
        viewModel.timeLiveData.observe(this@GameScreen, timeObserver)
        dialogSetting.setSound { sound = it }
        gameOverDialog.setOnExitClickListener {
            findNavController().popBackStack()
        }
        gameOverDialog.setOnContinueClickListener {
            viewModel.getList()
        }
        gameWinDialog.setOnRefreshClickListener {
            viewModel.setDefaultLevel()
            viewModel.calculate = false
        }
        gameWinDialog.setOnContinueClickListener {
            viewModel.calculate = false
        }
        dialogExit.apply {
            setOnYesClickLister {
                dialogExit.dismiss()
                findNavController().popBackStack()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            dialogExit.show(childFragmentManager, "")
            viewModel.calculate = true
        }
        progress.max = 12
        back.setOnClickListener {
            dialogExit.let {
                viewModel.calculate = true
                it.show(childFragmentManager, "")
            }
        }
        settings.setOnClickListener {
            dialogSetting.let {
                viewModel.calculate = true
                it.show(childFragmentManager, "")
                it.setOnDismissListener {
                    viewModel.calculate = false
                }
                it.setReview {
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.getListLiveData.observe(viewLifecycleOwner, listObserver)
        viewModel.getLevelLiveData.observe(viewLifecycleOwner, levelObserver)
        viewModel.getList()
    }

    private val timeObserver = Observer<Int> {
        binding.progress.progress = binding.progress.progress - it
    }

    private val looseObserver = Observer<Unit> {
        try {
            val oldFragment: Fragment? = childFragmentManager.findFragmentByTag("wait_modal")
            if (oldFragment != null && oldFragment.isAdded)
            else if (!gameOverDialog.isAdded && !gameOverDialog.isVisible) {
                childFragmentManager.executePendingTransactions()
                gameOverDialog.show(childFragmentManager, "wait_modal")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val levelObserver = Observer<Int> {
        when (it) {
            1 -> binding.levelText.text = "Easy"
            2 -> binding.levelText.text = "Medium"
            else -> binding.levelText.text = "Hard"
        }
        viewModel.startTimer(binding.levelText.text.toString())
    }

    private val listObserver = Observer<List<Int>> {
        binding.main.post {
            when (it.size) {
                6 -> {
                    x = 2
                    y = 3
                }
                12 -> {
                    x = 3
                    y = 4
                }
                24 -> {
                    x = 4
                    y = 6
                }
            }
            loadView(binding.main.height, binding.main.width)
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
                                        if (binding.levelText.text == "Hard") {
                                            gameWinDialog.show(
                                                childFragmentManager,
                                                ""
                                            )
                                            viewModel.calculate = true
                                        }
                                        viewModel.setLevel()
                                        views.clear()
                                        viewModel.cancelTimer()
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
        binding.progress.max = 120
        binding.progress.progress = 120
        openImagecount = 0
        views.clear()
        binding.container.removeAllViews()
        for (i in 0 until x) {
            for (j in 0 until y) {
                val image = AppCompatImageView(requireContext())
                binding.container.layoutParams.apply {
                    height = (heightImage / (y + 4)) * y
                    width = (widthImage / (x + 1)) * x + 16 * (x + 1)
                }
                image.x = (i * (widthImage / (x + 1))).toFloat() + (i + 1) * 16
                image.y = (j * heightImage / (y + 4)).toFloat()
                binding.container.addView(image)
                val layoutParameters = image.layoutParams as RelativeLayout.LayoutParams
                layoutParameters.apply {
                    height = heightImage / (y + 4)
                    width = widthImage / (x + 1)
                }
                image.setImageResource(R.drawable.card_back)
                image.scaleType = ImageView.ScaleType.FIT_XY
                image.layoutParams = layoutParameters
                views.add(image)
            }
        }
    }

    private fun openImage(image: AppCompatImageView) {
        if (sound) Music.getMusicPlayer(requireContext(), R.raw.click).apply {
            if (isPlaying) stop()
            start()
        }
        image.animate().setDuration(200).rotationY(90f).withEndAction {
            image.setImageResource(image.tag as Int)
            image.animate().setDuration(200).rotationY(180f).interpolator = DecelerateInterpolator()
        }
        openImagecount++
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

}