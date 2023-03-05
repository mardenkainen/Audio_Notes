package com.example.audionotes.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.audionotes.data.repository.AudioRepositoryImpl
import com.example.audionotes.data.AudioPlayer
import com.example.audionotes.data.AudioRecorder
import com.example.audionotes.domain.usecase.PlayAudioByAudioMessage

class MainViewModelFactory(context:Context): ViewModelProvider.Factory {

    private val audioRepository= AudioRepositoryImpl(context = context)
    private val audioPlayer= AudioPlayer(context = context)
    private val playAudioByName= PlayAudioByAudioMessage(audioRepository,audioPlayer)
    private val audioRecorder= AudioRecorder(context,audioRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(audioRecorder,audioRepository,playAudioByName) as T
    }
}