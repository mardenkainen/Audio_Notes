package com.example.audionotes.data.storage

import java.io.File

interface AudioStorage {

    fun saveAudio(file: File): File

    fun searchAudioFilesInStorage():MutableList<File>

    fun getAudio(name: String): File?

    fun getUniqueFileName(directory: File?):String

    fun getOutputFile(): File

    fun deleteFile(name:String):Boolean

    fun changeName(oldName:String,newName:String): File?
}