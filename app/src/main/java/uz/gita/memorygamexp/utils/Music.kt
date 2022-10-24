package uz.gita.memorygamexp.utils

import android.content.Context
import android.media.MediaPlayer

class Music {
    companion object {
        var player: MediaPlayer? = null
        fun getMusicPlayer(context: Context, url: Int): MediaPlayer {
            if (player == null) player = MediaPlayer.create(context, url)
            return player!!
        }
    }
}