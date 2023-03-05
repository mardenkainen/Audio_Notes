package com.example.audionotes.domain


interface AudioRecorder {
    fun start()
    fun stop()
    fun save():Boolean
}