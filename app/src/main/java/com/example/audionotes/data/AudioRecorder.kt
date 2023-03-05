package com.example.audionotes.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import com.example.audionotes.domain.AudioRecorder
import com.example.audionotes.domain.repository.AudioRepository
import java.io.File
import java.io.FileOutputStream

class AudioRecorder(private var context: Context, private val audioRepository: AudioRepository):
    AudioRecorder {
    private var recorder: MediaRecorder? = null
    private var file:File?=null

    private var isRecording = false

    private fun createRecorder():MediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(context) else MediaRecorder()

    override fun start() {
        file = audioRepository.getOutputFile()
        if (isRecording || recorder!=null) {stop()}

        try {
            releaseRecorder()
            recorder = createRecorder()
            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(FileOutputStream(file).fd)

                prepare()
                start()

                isRecording = true
            }

        }catch (e:Exception){

        }
    }

    override fun stop() {
        if (!isRecording || recorder==null) return

        try {
            isRecording = false
            recorder!!.stop()
            recorder!!.reset()
            recorder!!.release()
            recorder=null
            save()
        }
        catch (e:RuntimeException){

        }
    }

    override fun save():Boolean = audioRepository.saveAudio(file!!)

    private fun releaseRecorder(){
        if (recorder!=null){
            recorder!!.release()
            recorder=null
        }
    }
}