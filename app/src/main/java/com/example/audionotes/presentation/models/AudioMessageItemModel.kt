package com.example.audionotes.presentation.models


class AudioMessageItemModel(
    var name:String,
    val duration:String,
    var creationTime:String,
    val type: String,
    val playButtonState: ButtonState,
    val showMenu:Boolean,
    val currentPosition:Float
    ){

    class Builder{
        private var name:String = "Новое сообщение"
        private var duration:String = "00:00"
        private var creationTime:String = ""
        private var type: String = ""
        private var playButtonState: ButtonState = ButtonState.ButtonBuilder.PLAY_BUTTON
        private var showMenu:Boolean = false
        private var currentPosition = 0f

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setDuration(duration: String): Builder {
            this.duration = duration
            return this
        }

        fun setCreationTime(creationTime: String): Builder {
            this.creationTime = creationTime
            return this
        }

        fun setType(type: String): Builder {
            this.type = type
            return this
        }

        fun setPlayButtonState(playButtonState: ButtonState): Builder {
            this.playButtonState = playButtonState
            return this
        }

        fun setShowMenu(showMenu: Boolean): Builder {
            this.showMenu = showMenu
            return this
        }

        fun setCurrentPosition(currentPosition: Float): Builder{
            if (currentPosition>=0f) this.currentPosition = currentPosition
            return this
        }

        fun copy(audioMessageItemModel: AudioMessageItemModel): Builder {
            this.setName(audioMessageItemModel.name)
            this.setDuration(audioMessageItemModel.duration)
            this.setCreationTime(audioMessageItemModel.creationTime)
            this.setType(audioMessageItemModel.type)
            this.setPlayButtonState(audioMessageItemModel.playButtonState)
            this.setShowMenu(audioMessageItemModel.showMenu)
            return this
        }

        fun build(): AudioMessageItemModel =
            AudioMessageItemModel(
                name = name,
                duration = duration,
                creationTime = creationTime,
                type = type,
                playButtonState = playButtonState,
                showMenu = showMenu,
                currentPosition = currentPosition
            )
    }
}

