package com.example.fullmoon_android.data

enum class MessageRole {
    USER, ASSISTANT
}

data class Message(
    val id: String = java.util.UUID.randomUUID().toString(),
    val role: MessageRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ChatThread(
    val id: String = java.util.UUID.randomUUID().toString(),
    val messages: MutableList<Message> = mutableListOf()
) 