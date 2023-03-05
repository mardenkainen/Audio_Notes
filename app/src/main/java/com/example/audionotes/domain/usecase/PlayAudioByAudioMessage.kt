package com.example.audionotes.domain.usecase

import com.example.audionotes.domain.AudioPlayer
import com.example.audionotes.domain.PlayingStatusListener
import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.domain.repository.AudioRepository

class PlayAudioByAudioMessage(private val audioRepository: AudioRepository, private val player: AudioPlayer) {
    fun start(audioMessage: AudioMessage){
        val file=audioRepository.getAudioFileByName("${audioMessage.name}${audioMessage.type}")
        file?.let { player.playFile(it) }
    }

    fun stop(){
        player.stop()
    }

    fun pause(){
        player.pause()
    }

    fun resume(){
        player.resume()
    }

    fun setListener(listener: PlayingStatusListener){
        player.setListener(listener)
    }
}