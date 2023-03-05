package com.example.audionotes.data.storage

import android.content.Context
import android.util.Log
import java.io.File
import java.lang.NumberFormatException

private const val DEFAULT_FILE_NAME = "Новая запись "

class InternalStorage(private val context: Context): AudioStorage {

    private val directory:File
    init {
        val folderPath=context.filesDir.path + "/records/"
        directory = File(folderPath)
        if (!directory.exists()){
            directory.mkdir()
        }
    }

    override fun saveAudio(file: File): File {
        val folderPath=context.filesDir.path + "/records/"
        val name = getUniqueFileName(directory)
        val dest=File(folderPath,name)
        file.renameTo(dest)
        return dest
    }

    override fun searchAudioFilesInStorage():MutableList<File> {
        val messages = mutableListOf<File>()
        directory.walk().forEach {
            if (it.isFile && it.name.substringAfterLast(".")=="mp3"){

                messages.add(it)

            }
        }
        return messages
    }

    override fun getAudio(name: String):File? {
        directory.walk().forEach {
            if (it.isFile && it.name==name){
                return it
            }
        }
        return null
    }

    override fun getUniqueFileName(directory:File?):String {
        var uniqueFileNumber = 0
        directory!!.walk().forEach {
            val name=it.name
            if (name.startsWith(DEFAULT_FILE_NAME)){
                val postfix = name.drop(DEFAULT_FILE_NAME.length).substringBeforeLast(".")

                val number:Int = try {
                    postfix.toInt()
                } catch (e: NumberFormatException) {
                    -1
                }

                if (number >= uniqueFileNumber) {
                    uniqueFileNumber = number + 1
                }
            }
        }
        return "$DEFAULT_FILE_NAME$uniqueFileNumber.mp3"
    }

    override fun getOutputFile(): File = File("${context.filesDir}","audio.mp3")

    override fun deleteFile(name: String):Boolean{
        Log.d("CCC",name)
        val file = getAudio(name) ?: return false
        return file.delete()
    }

    override fun changeName(oldName:String,newName:String): File? {
        val file = getAudio(oldName)
        val newFile = File("${directory.path}/$newName")
        Log.d("CCC","${file?.path}  ${newFile.path}")
        if(!newFile.exists()){
            file?.renameTo(newFile)
            return newFile
        }
        return null
    }
}