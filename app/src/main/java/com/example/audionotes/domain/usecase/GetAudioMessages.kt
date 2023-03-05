package com.example.audionotes.domain.usecase

import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.domain.repository.AudioRepository

class GetAudioMessages(private val audioRepository: AudioRepository) {
    fun execute(): MutableList<AudioMessage> {
        return audioRepository.getAudioFiles()
    }
}