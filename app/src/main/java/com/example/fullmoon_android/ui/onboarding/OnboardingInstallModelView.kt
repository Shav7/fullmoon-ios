package com.example.fullmoon_android.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.data.ModelConfiguration

@Composable
fun OnboardingInstallModelView(
    navController: NavController,
    appManager: AppManager
) {
    val availableModels = remember {
        listOf(
            ModelConfiguration(
                name = "llama-2-7b-chat",
                displayName = "Llama 2 7B Chat",
                description = "A compact yet powerful chat model",
                size = 7_000_000_000
            ),
            ModelConfiguration(
                name = "mistral-7b",
                displayName = "Mistral 7B",
                description = "High-performance open source model",
                size = 7_200_000_000
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Choose a Model",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Select a language model to download and install",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(availableModels) { model ->
                ModelCard(
                    model = model,
                    onClick = {
                        appManager.selectedModel = model
                        navController.navigate("downloadingProgress")
                    }
                )
            }
        }
    }
}

@Composable
private fun ModelCard(
    model: ModelConfiguration,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = model.displayName,
                style = MaterialTheme.typography.titleLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = model.description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Size: ${formatSize(model.size)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun formatSize(bytes: Long): String {
    val gb = bytes.toDouble() / 1_000_000_000
    return "%.1f GB".format(gb)
} 