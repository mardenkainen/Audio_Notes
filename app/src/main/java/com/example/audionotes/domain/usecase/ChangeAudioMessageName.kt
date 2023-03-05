package com.example.audionotes.domain.usecase

import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.domain.repository.AudioRepository

class ChangeAudioMessageName(private val audioRepository: AudioRepository) {
    fun execute(oldAudioMessage: AudioMessage, newName:String): Boolean {
        return audioRepository.changeName(oldAudioMessage = oldAudioMessage, newName = newName)
    }
}