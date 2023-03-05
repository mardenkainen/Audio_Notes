package com.example.audionotes.domain

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun pause()
    fun resume()
    fun stop()
    fun setListener(listener: PlayingStatusListener)
}