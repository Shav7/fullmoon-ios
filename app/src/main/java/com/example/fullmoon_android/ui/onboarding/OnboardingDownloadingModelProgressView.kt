package com.example.fullmoon_android.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.llm.LLMEvaluator
import kotlinx.coroutines.launch

@Composable
fun OnboardingDownloadingModelProgressView(
    navController: NavController,
    appManager: AppManager
) {
    val scope = rememberCoroutineScope()
    val llmEvaluator = remember { LLMEvaluator() }
    var progress by remember { mutableStateOf(0f) }
    
    LaunchedEffect(Unit) {
        scope.launch {
            appManager.selectedModel?.let { model ->
                llmEvaluator.downloadModel(model.name).collect { downloadProgress ->
                    progress = downloadProgress
                    if (downloadProgress >= 1f) {
                        appManager.addInstalledModel(model.name)
                        appManager.currentModelName = model.name
                        navController.navigate("chat") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Downloading Model",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = appManager.selectedModel?.displayName ?: "",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.titleLarge
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Please wait while we download and install the model",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
} 