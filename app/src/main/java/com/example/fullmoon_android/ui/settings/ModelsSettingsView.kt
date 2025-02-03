package com.example.fullmoon_android.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.data.ModelConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelsSettingsView(
    navController: NavController,
    appManager: AppManager
) {
    val installedModels = remember(appManager.installedModels) {
        appManager.installedModels.map { modelName ->
            ModelConfiguration(
                name = modelName,
                displayName = when {
                    modelName.contains("llama") -> "Llama 2 7B Chat"
                    modelName.contains("mistral") -> "Mistral 7B"
                    else -> modelName
                },
                description = "Installed model",
                size = 7_000_000_000,
                isDownloaded = true
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Model Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (installedModels.isEmpty()) {
                EmptyStateMessage()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(installedModels) { model ->
                        ModelSettingsCard(
                            model = model,
                            isSelected = model.name == appManager.currentModelName,
                            onSelect = { 
                                appManager.currentModelName = model.name
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No models installed yet. Go to the onboarding screen to install a model.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ModelSettingsCard(
    model: ModelConfiguration,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSelect
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.displayName,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Size: ${formatSize(model.size)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            if (isSelected) {
                RadioButton(
                    selected = true,
                    onClick = null
                )
            }
        }
    }
}

private fun formatSize(bytes: Long): String {
    val gb = bytes.toDouble() / 1_000_000_000
    return "%.1f GB".format(gb)
} 