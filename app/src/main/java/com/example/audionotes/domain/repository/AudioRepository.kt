package com.example.audionotes.domain.repository

import com.example.audionotes.domain.models.AudioMessage
import java.io.File

interface AudioRepository {
    fun saveAudio(file: File):Boolean

    fun getAudioFiles(): MutableList<AudioMessage>

    fun getAudioFileByName(name:String): File?

    fun getOutputFile() : File

    fun changeName(oldAudioMessage: AudioMessage, newName:String):Boolean

}