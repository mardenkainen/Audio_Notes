package com.example.audionotes.presentation.models

import androidx.compose.ui.graphics.Color
import com.example.audionotes.R
import com.example.audionotes.presentation.ui.theme.LightBlue
import com.example.audionotes.presentation.ui.theme.PauseGrey

class ButtonState(var isPressed:Boolean, var color:Color, var imageId:Int){
    class ButtonBuilder{
        private var isPressed:Boolean = false
        private var color:Color =LightBlue
        private var imageId:Int = R.drawable.ic_play_icon

        companion object{
            val PLAY_BUTTON = ButtonBuilder()
                .setIsPressed(false)
                .setColor(LightBlue)
                .setImageId(R.drawable.ic_play_icon)
                .build()

            val PAUSE_BUTTON = ButtonBuilder()
                .setIsPressed(true)
                .setColor(PauseGrey)
                .setImageId(R.drawable.pause_icon)
                .build()

            val RECORD_BUTTON = ButtonBuilder()
                .setIsPressed(false)
                .setColor(LightBlue)
                .setImageId(R.drawable.microphone_icon)
                .build()

            val STOP_BUTTON = ButtonBuilder()
                .setIsPressed(true)
                .setColor(PauseGrey)
                .setImageId(R.drawable.stop_icon)
                .build()
        }

        fun setIsPressed(isPressed: Boolean): ButtonBuilder {
            this.isPressed = isPressed
            return this
        }

        fun setColor(color: Color): ButtonBuilder {
            this.color = color
            return this
        }

        fun setImageId(imageId: Int): ButtonBuilder {
            this.imageId = imageId
            return this
        }

        fun build(): ButtonState =
            ButtonState(
                isPressed = isPressed,
                color =color,
                imageId = imageId
            )
    }
}