package com.example.fullmoon_android.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppManager : ViewModel() {
    // Manage a list of installed model names.
    private val _installedModels = MutableStateFlow<List<String>>(emptyList())
    val installedModels: List<String> get() = _installedModels.value

    // Currently selected model (if any).
    var selectedModel: ModelConfiguration? = null

    // (Simulated) name of the current model.
    var currentModelName: String? = null

    // Add a model to the installed list.
    fun addInstalledModel(modelName: String) {
        val list = _installedModels.value.toMutableList()
        if (!list.contains(modelName)) {
            list.add(modelName)
            _installedModels.value = list
        }
    }

    // Placeholder for haptic feedback (e.g. using vibration API).
    fun playHaptic() {
        // TODO: implement vibration feedback if desired.
    }
} 