package com.example.fullmoon_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.ui.NavigationRoot
import com.example.fullmoon_android.ui.theme.FullmoonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullmoonTheme {
                // Create an instance of our app-wide state ViewModel (AppManager)
                val appManager = viewModel<AppManager>()
                NavigationRoot(appManager = appManager)
            }
        }
    }
} 