package com.example.audionotes.presentation.ui.uiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audionotes.presentation.MainViewModel
import com.example.audionotes.presentation.models.AudioMessageItemModel
import com.example.audionotes.presentation.ui.theme.Black
import com.example.audionotes.presentation.ui.theme.Gray
import com.example.audionotes.presentation.ui.theme.LightBlue
import com.example.audionotes.presentation.ui.theme.TextGrey

@Composable
fun AudioMessageItem(audioMessageItemModel: AudioMessageItemModel, vm: MainViewModel) {

    val state = vm.getState(audioMessageItemModel)
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { vm.showMenu(audioMessageItemModel = audioMessageItemModel) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        shadowElevation = 4.dp,
        color = Gray
    ) {
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()){

        AudioMessageItemDropdownMenu(state = state, audioMessageItemModel = audioMessageItemModel, viewModel = vm)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = state.value.name,
                    fontSize = 16.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { vm.showDialog(audioMessageItemModel)}
                )
                Text(
                    text = audioMessageItemModel.creationTime,
                    fontSize = 13.sp,
                    color = TextGrey
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = audioMessageItemModel.duration,
                        fontSize = 15.sp
                    )
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(state.value.playButtonState.color)
                            .padding(10.dp)
                            .clickable { vm.onPlayAudioClicked(audioMessageItemModel) }){

                        Icon(painter = painterResource(id = state.value.playButtonState.imageId),
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp))
                    }

                }
            }
            }
        Canvas(modifier = Modifier.fillMaxWidth().height(3.dp).align(Alignment.BottomCenter)){
            drawRoundRect(LightBlue, size = Size(width = maxWidth.toPx()*state.value.currentPosition, height = 3.dp.toPx()),cornerRadius = CornerRadius(20f,20f))
            //drawRoundRect(LightBlue,)
        }
        }
    }
}

