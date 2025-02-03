package com.example.fullmoon_android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.ui.chat.ChatView
import com.example.fullmoon_android.ui.onboarding.OnboardingView
import com.example.fullmoon_android.ui.onboarding.OnboardingDownloadingModelProgressView
import com.example.fullmoon_android.ui.onboarding.OnboardingInstallModelView
import com.example.fullmoon_android.ui.settings.ModelsSettingsView

@Composable
fun NavigationRoot(appManager: AppManager) {
    val navController = rememberNavController()
    // If no model is installed yet, launch onboarding; otherwise go straight to chat.
    val startDestination = if (appManager.installedModels.isEmpty()) "onboarding" else "chat"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") {
            OnboardingView(navController = navController, appManager = appManager)
        }
        composable("installModel") {
            OnboardingInstallModelView(navController = navController, appManager = appManager)
        }
        composable("downloadingProgress") {
            OnboardingDownloadingModelProgressView(navController = navController, appManager = appManager)
        }
        composable("chat") {
            ChatView(navController = navController, appManager = appManager)
        }
        composable("modelsSettings") {
            ModelsSettingsView(navController = navController, appManager = appManager)
        }
    }
} 