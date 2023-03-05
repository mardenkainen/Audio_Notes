package com.example.audionotes.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.audionotes.data.OnFileStorageChangedListener
import com.example.audionotes.data.repository.AudioRepositoryImpl
import com.example.audionotes.domain.PlayingStatusListener
import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.data.AudioRecorder
import com.example.audionotes.domain.usecase.ChangeAudioMessageName
import com.example.audionotes.domain.usecase.GetAudioMessages
import com.example.audionotes.domain.usecase.PlayAudioByAudioMessage
import com.example.audionotes.presentation.models.AudioMessageItemModel
import com.example.audionotes.presentation.models.ButtonState


class MainViewModel(private val audioRecorder: AudioRecorder,
                    private val audioRepository: AudioRepositoryImpl,
                    private val audioPlayer:PlayAudioByAudioMessage) : ViewModel() {


    private val getAudioMessages = GetAudioMessages(audioRepository)
    private val nameChanger = ChangeAudioMessageName(audioRepository)

    var isDialogVisible = mutableStateOf(false)
    private var _renamedItem:MutableState<AudioMessageItemModel?> = mutableStateOf(null)
    val newName = mutableStateOf("")

    val renamedItem
    get() = _renamedItem.value

    val playerSlideBar = mutableStateOf(0f)


    private val _messages:SnapshotStateList<MutableState<AudioMessageItemModel>>
    val messages
        get() = _messages


    val recordButtonState = mutableStateOf(ButtonState
        .ButtonBuilder
        .RECORD_BUTTON)

    private var currentPlayingMessage:AudioMessageItemModel?=null

    init {
        audioRepository.setListener(onOnFileStorageChangedListener())
        audioPlayer.setListener(playerStatusListener())
        val messageList = AudioMessageParser.parseMessageListToModelList(getAudioMessages.execute())
        _messages = listToListOfState(messageList)

    }

    fun onRecordAudioClicked(){
        if(recordButtonState.value.isPressed){
            recordButtonState.value = ButtonState.ButtonBuilder.RECORD_BUTTON
            stopRecording()
        }  else {
            recordButtonState.value =ButtonState.ButtonBuilder.STOP_BUTTON
            startRecording()
        }
    }

    private var isPaused = false

    fun onPlayAudioClicked(audioMessageItemModel: AudioMessageItemModel){
        val index = getAudioItemIndex(audioMessageItemModel) ?: return
        val message = _messages[index].value
        if (currentPlayingMessage?.name==message.name){
            isPaused = if (isPaused) {
                audioPlayer.resume()
                changeButtonState(message)
                false
            } else {
                audioPlayer.pause()
                changeButtonState(message)
                true
            }
            return
        }
        audioPlayer.start(AudioMessageParser.parseModelToMessage(audioMessageItemModel))
        changeButtonState(message)
        currentPlayingMessage = audioMessageItemModel
    }

    fun deleteAudio(audioMessageItemModel: AudioMessageItemModel)  { audioRepository.deleteAudio(
        AudioMessageParser.parseModelToMessage(audioMessageItemModel)) }

    private fun changeButtonState(audioMessageItemModel: AudioMessageItemModel){
        val index = getAudioItemIndex(audioMessageItemModel) ?: return

        if (audioMessageItemModel.playButtonState.isPressed){
            _messages[index].value = AudioMessageItemModel.Builder()
                .copy(_messages[index].value)
                .setPlayButtonState(ButtonState.ButtonBuilder.PLAY_BUTTON)
                .build()
        }
        else{
            _messages[index].value = AudioMessageItemModel.Builder()
                .copy(_messages[index].value)
                .setPlayButtonState(ButtonState.ButtonBuilder.PAUSE_BUTTON)
                .build()
        }

    }

    private fun setCurrentPosition(audioMessageItemModel: AudioMessageItemModel,currentPosition:Float){
        val index = getAudioItemIndex(audioMessageItemModel) ?: return

        _messages[index].value = AudioMessageItemModel.Builder().copy(_messages[index].value).setCurrentPosition(currentPosition).build()
    }

    fun showMenu(audioMessageItemModel: AudioMessageItemModel){
        if (currentPlayingMessage?.name==audioMessageItemModel.name) return
        val index = getAudioItemIndex(audioMessageItemModel) ?: return
        val oldMessage = _messages[index].value

        _messages[index].value =
            AudioMessageItemModel.Builder()
                .copy(oldMessage)
                .setShowMenu(true)
                .build()
    }

    fun closeMenu(audioMessageItemModel: AudioMessageItemModel){
        val index = getAudioItemIndex(audioMessageItemModel) ?: return
        val oldMessage = _messages[index].value

        _messages[index].value =
            AudioMessageItemModel.Builder()
                .copy(oldMessage)
                .setShowMenu(false)
                .build()
    }

    fun showDialog(audioMessageItemModel: AudioMessageItemModel){
        if (currentPlayingMessage?.name == audioMessageItemModel.name) return
        _renamedItem.value = audioMessageItemModel
        newName.value = audioMessageItemModel.name
        isDialogVisible.value = true
    }

    fun closeDialog(){
        _renamedItem.value = null
        isDialogVisible.value = false
    }

    fun changeName(audioMessageItemModel: AudioMessageItemModel?,newName:String){
        val name = if (newName.length>30) newName.substring(0,30) else newName

        if (audioMessageItemModel==null || name=="") return
        if (name.contains(Regex("[\\^\\[<>:;,?\"*|/\\]+$]"))) return
        nameChanger.execute( AudioMessageParser.parseModelToMessage(audioMessageItemModel),name)
    }

    fun getState(audioMessageItemModel: AudioMessageItemModel) = _messages.first { it.value.name==audioMessageItemModel.name }

    private fun startRecording() {
        audioRecorder.start()
    }

    private fun stopRecording() {
        audioRecorder.stop()
    }

    private fun onOnFileStorageChangedListener(): OnFileStorageChangedListener {
        return object : OnFileStorageChangedListener {

            override fun onAudioSaved(audioMessage: AudioMessage) {
                _messages.add(mutableStateOf(AudioMessageParser.parseMessageToModel(audioMessage)))
            }

            override fun onAudioDeleted(audioMessage: AudioMessage) {
                val index = getAudioItemIndex(AudioMessageParser.parseMessageToModel(audioMessage))
                if (index != null) {
                    _messages.removeAt(index)
                }
            }

            override fun onAudioChangedName(
                oldAudioMessage: AudioMessage,
                newAudioMessage: AudioMessage
            ) {
                updateMessageName(oldAudioMessage, newAudioMessage)
            }
        }
    }

    private fun playerStatusListener():PlayingStatusListener{
        return object :PlayingStatusListener{
            override fun onStart() {
            }

            override fun onPlaying(percent: Float) {
                currentPlayingMessage?.let { setCurrentPosition(it,percent) }
                playerSlideBar.value = percent
            }

            override fun onPause() {
            }

            override fun onStop() {
                val index = currentPlayingMessage?.let { getAudioItemIndex(it) }

                if (index!=null){
                    _messages[index].value = AudioMessageItemModel.Builder()
                        .copy(_messages[index].value)
                        .setPlayButtonState(ButtonState.ButtonBuilder.PLAY_BUTTON)
                        .build()
                }
                currentPlayingMessage = null
            }


        }
    }

    private fun updateMessageName( oldAudioMessage: AudioMessage, newAudioMessage: AudioMessage) {
        val index = getAudioItemIndex(AudioMessageParser.parseMessageToModel(oldAudioMessage)) ?: return
        val oldMessage = _messages[index].value
        _messages[index].value =
            AudioMessageItemModel.Builder()
                .copy(oldMessage)
                .setName(newAudioMessage.name)
                .build()
    }

    private fun getAudioItemIndex(audioMessageItemModel: AudioMessageItemModel): Int? {
        _messages.forEachIndexed { index, mutableState ->
            run {
                if (mutableState.value.name == audioMessageItemModel.name) {
                    return index
                }
            }
        }
        return null
    }

    private fun listToListOfState(list:MutableList<AudioMessageItemModel>): SnapshotStateList<MutableState<AudioMessageItemModel>> {
        val stateList:SnapshotStateList<MutableState<AudioMessageItemModel>> = SnapshotStateList()
        list.forEach { stateList.add(mutableStateOf(it)) }
        return stateList
    }
}