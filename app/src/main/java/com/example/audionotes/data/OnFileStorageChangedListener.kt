package com.example.audionotes.data

import com.example.audionotes.domain.models.AudioMessage

interface OnFileStorageChangedListener {
    fun onAudioSaved(audioMessage: AudioMessage)
    fun onAudioDeleted(audioMessage: AudioMessage)
    fun onAudioChangedName(oldAudioMessage: AudioMessage, newAudioMessage: AudioMessage)
}