package com.example.audionotes.domain

interface PlayingStatusListener {
    fun onStart()
    fun onPlaying(percent: Float)
    fun onPause()
    fun onStop()
}