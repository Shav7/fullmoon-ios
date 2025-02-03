package com.example.fullmoon_android.data

data class ModelConfiguration(
    val name: String,
    val displayName: String,
    val description: String,
    val size: Long,
    val isDownloaded: Boolean = false
) 