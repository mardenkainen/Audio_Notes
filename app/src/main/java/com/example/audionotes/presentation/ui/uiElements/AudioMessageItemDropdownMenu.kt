package com.example.audionotes.presentation.ui.uiElements

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.audionotes.presentation.MainViewModel
import com.example.audionotes.presentation.models.AudioMessageItemModel
import com.example.audionotes.presentation.ui.theme.Gray
import com.example.audionotes.R

@Composable
fun AudioMessageItemDropdownMenu(state: MutableState<AudioMessageItemModel>,
                                 audioMessageItemModel: AudioMessageItemModel,
                                 viewModel: MainViewModel
){
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = state.value.showMenu,
            onDismissRequest = { viewModel.closeMenu(audioMessageItemModel) },
            modifier = Modifier.background(Gray)
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.delete)) },
                onClick = {
                    run {
                        viewModel.deleteAudio(
                            audioMessageItemModel
                        )
                        viewModel.closeMenu(audioMessageItemModel)
                    }
                })

        }
    }
}