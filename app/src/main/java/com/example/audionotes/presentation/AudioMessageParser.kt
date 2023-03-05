package com.example.audionotes.presentation

import com.example.audionotes.domain.models.AudioMessage
import com.example.audionotes.presentation.models.AudioMessageItemModel
import com.example.audionotes.presentation.models.ButtonState

class AudioMessageParser{

    companion object {
        fun parseMessageListToModelList(audioMessageList: MutableList<AudioMessage>): MutableList<AudioMessageItemModel> {
            val modelList = mutableListOf<AudioMessageItemModel>()
            audioMessageList.forEach { modelList.add(parseMessageToModel(it)) }
            return modelList
        }

        fun parseMessageToModel(audioMessage: AudioMessage): AudioMessageItemModel {
            return AudioMessageItemModel.Builder()
                .setName(name = audioMessage.name)
                .setDuration(duration = audioMessage.duration)
                .setCreationTime(creationTime = audioMessage.creationTime)
                .setType(type = audioMessage.type)
                .setPlayButtonState(playButtonState = ButtonState.ButtonBuilder.PLAY_BUTTON)
                .setShowMenu(showMenu = false)
                .build()

        }

        fun parseModelToMessage(messageItemModel: AudioMessageItemModel): AudioMessage {
            return AudioMessage(
                name = messageItemModel.name,
                duration = messageItemModel.duration,
                creationTime = messageItemModel.creationTime,
                type = messageItemModel.type
            )
        }
    }

}