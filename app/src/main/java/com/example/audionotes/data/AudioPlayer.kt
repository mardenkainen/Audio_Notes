package com.example.audionotes.data

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.audionotes.domain.AudioPlayer
import com.example.audionotes.domain.PlayingStatusListener
import kotlinx.coroutines.*
import java.io.File

class AudioPlayer(private val context: Context): AudioPlayer {

    private var player:MediaPlayer?=null
    private var listener: PlayingStatusListener?=null
    private var isPlaying = false
    private var job:Job?=null

    override fun playFile(file: File) {
        if (player != null) {
            stop()
        }
        if (!isPlaying){
            job = progress(50L)
            isPlaying=true
            player =  MediaPlayer.create(context,file.toUri())
            player?.start()
            listener?.onStart()
            player?.setOnCompletionListener { stop()}

        }

    }

    override fun stop() {
        job?.cancel()
        player?.stop()
        player?.release()
        player = null
        isPlaying = false
        listener?.onStop()
    }

    override fun pause() {
        player?.pause()
        listener?.onPause()
    }

    override fun resume() {
        player?.start()
    }

    private fun progress(timeInterval:Long):Job{
        return CoroutineScope(Dispatchers.Default).launch {
            while(isActive){
                val current = (player?.currentPosition ?: 0).toFloat()
                val duration = (player?.duration ?: 1).toFloat()
                val percent = (current / duration)
                listener?.onPlaying(percent)
                delay(timeInterval)
            }
        }
    }

    override fun setListener(listener: PlayingStatusListener) {
        this.listener = listener
    }
}