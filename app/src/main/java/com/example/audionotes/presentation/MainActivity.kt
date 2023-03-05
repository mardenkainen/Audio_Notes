package com.example.audionotes.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.audionotes.presentation.ui.theme.MyApplicationTheme
import com.example.audionotes.presentation.ui.theme.White
import com.example.audionotes.presentation.ui.uiElements.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm=ViewModelProvider(this, MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO),0)
        setContent {
            MyApplicationTheme {
                Box(modifier = Modifier.fillMaxSize().background(White)){
                    MainScreen(vm)
                }
            }
        }
    }
}