package com.example.audionotes.domain

import android.media.MediaMetadataRetriever
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AudioMetadata(private val file: File) {

    private val mediaMetadataRetriever:MediaMetadataRetriever = MediaMetadataRetriever()

    companion object{
        private const val FORMAT_PATTERN="dd:MM:yyyy в HH:mm"
    }

    init {
        mediaMetadataRetriever.setDataSource(file.absolutePath)
    }

    fun getDuration(): String {
        val dur =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
                .toLong()
        val seconds = (dur % 60000) / 1000
        val minutes = dur / 60000
        return "$minutes:${if (seconds < 10) "0" else ""}$seconds"
    }

    fun getCreationTime():String{
        val creationTime = file.lastModified()
        val date = Date(creationTime)
        return formatDate(date)
    }

    private fun formatDate(date: Date) = SimpleDateFormat(FORMAT_PATTERN,Locale("RU")).format(date)
}