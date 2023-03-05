package com.example.audionotes.presentation.ui.uiElements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audionotes.R
import com.example.audionotes.presentation.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel){

    AudioMessageItemDialog(viewModel = vm)
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp)){
        item { Text(text = stringResource(id = R.string.your_records), fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 30.dp)) }
        items(items = vm.messages, itemContent = { AudioMessageItem(vm = vm, audioMessageItemModel = it.value) })
        item { Box(modifier = Modifier.padding(bottom = 96.dp)) }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp), contentAlignment = Alignment.BottomCenter){
        Column {
            RecordButton(vm)
        }
    }
}

@Composable
fun RecordButton(vm: MainViewModel){
    Button(
        onClick = { vm.onRecordAudioClicked() },
        shape = CircleShape,
        modifier = Modifier.size(72.dp),
        colors = ButtonDefaults.buttonColors(containerColor = vm.recordButtonState.value.color)) {
        Icon(painter = painterResource(id = vm.recordButtonState.value.imageId),
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier.size(72.dp))
    }
}

