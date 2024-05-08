package com.example.test4_emptyviewsact.utils
//package com.xsode.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

object MediaPlayerManager {

private var mediaPlayer: MediaPlayer? = null

fun playMusic(context: Context, musicPath: String) {
mediaPlayer = MediaPlayer()
val uri = Uri.parse(musicPath)
mediaPlayer?.setDataSource(context, uri)
mediaPlayer?.prepare()
mediaPlayer?.start()
}

fun stopMusic() {
mediaPlayer?.release()
mediaPlayer = null
}
}