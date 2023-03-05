package com.example.audionotes.presentation.ui.uiElements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audionotes.R
import com.example.audionotes.presentation.MainViewModel
import com.example.audionotes.presentation.ui.theme.Gray
import com.example.audionotes.presentation.ui.theme.LightBlue
import com.example.audionotes.presentation.ui.theme.White

@Composable
fun AudioMessageItemDialog(
    viewModel: MainViewModel
) {
    val fileName = viewModel.newName.value
    if (viewModel.isDialogVisible.value) {
            Dialog(
                onDismissRequest = { viewModel.closeDialog() }){
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                    shadowElevation = 4.dp,
                    color = Gray
                ) {

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.rename),
                            fontSize = 25.sp,
                            color = LightBlue,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        TextField(
                            value = fileName,
                            modifier = Modifier.border(
                                width = 3.dp,
                                color = LightBlue,
                                shape = RoundedCornerShape(12.dp)
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Gray,
                                cursorColor = LightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 15.sp),

                            onValueChange = { newValue -> viewModel.newName.value = newValue })

                        Button(onClick = {
                            viewModel.changeName(viewModel.renamedItem, fileName)
                            viewModel.closeDialog() },
                            shape = CircleShape,
                            contentPadding = PaddingValues(20.dp,12.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)
                        ) { Text(
                            text = stringResource(id = R.string.save),
                            fontSize = 20.sp,
                            color = White,
                            fontWeight = FontWeight.Bold,
                        ) }
                    }

            }
        }
    }

}

