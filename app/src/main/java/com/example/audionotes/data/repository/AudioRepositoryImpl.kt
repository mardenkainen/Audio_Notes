package com.example.audionotes.data.repository

import android.content.Context
import com.example.audionotes.data.OnFileStorageChangedListener
import com.example.audionotes.data.storage.InternalStorage
import com.example.audionotes.domain.AudioMetadata
import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.domain.repository.AudioRepository
import java.io.File

class AudioRepositoryImpl(context: Context): AudioRepository {

    private val storage = InternalStorage(context)

    private val directory:File
    init {
        val folderPath=context.filesDir.path + "/records/"
        directory = File(folderPath)
        if (!directory.exists()){
            directory.mkdir()
        }
    }

    private var messages = mutableListOf<AudioMessage>()

    private var listener: OnFileStorageChangedListener?=null

    fun setListener(listener: OnFileStorageChangedListener) {
        this.listener = listener
    }

    override fun saveAudio(file: File): Boolean {
        val savedFile = storage.saveAudio(file)
        val newAudioMessage = createAudioMessage(savedFile)
        listener?.onAudioSaved(newAudioMessage)
        return true
    }

    override fun getAudioFiles(): MutableList<AudioMessage>{
        val files = storage.searchAudioFilesInStorage()
        val audioMessages = mutableListOf<AudioMessage>()
        files.forEach { audioMessages.add(createAudioMessage(it)) }
        messages = audioMessages
        return messages
    }

    override fun getAudioFileByName(name: String): File? {
        return storage.getAudio(name = name)
    }

    override fun getOutputFile(): File {
        return storage.getOutputFile()
    }

    private fun createAudioMessage(file: File): AudioMessage {
        val metadata = AudioMetadata(file)

        return AudioMessage(
            name = file.name.substringBeforeLast("."),
            duration = metadata.getDuration(), creationTime = metadata.getCreationTime(),
            type = ".${file.name.substringAfterLast(".")}")
    }

    fun deleteAudio(deletedMessage: AudioMessage):Boolean{
        val res = storage.deleteFile("${deletedMessage.name}${deletedMessage.type}")
        listener!!.onAudioDeleted(deletedMessage)
        return res
    }

    override fun changeName(oldAudioMessage: AudioMessage, newName:String): Boolean {
        val file =
            storage.changeName(oldName = "${oldAudioMessage.name}${oldAudioMessage.type}", newName = "$newName${oldAudioMessage.type}")
                ?: return false
        val newAudioMessage = createAudioMessage(file)
        listener!!.onAudioChangedName(oldAudioMessage = oldAudioMessage, newAudioMessage = newAudioMessage)
        return true
    }
}