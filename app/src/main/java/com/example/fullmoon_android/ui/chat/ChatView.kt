package com.example.fullmoon_android.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fullmoon_android.data.AppManager
import com.example.fullmoon_android.data.ChatThread
import com.example.fullmoon_android.data.Message
import com.example.fullmoon_android.data.MessageRole
import com.example.fullmoon_android.llm.LLMEvaluator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    navController: NavController,
    appManager: AppManager
) {
    val scope = rememberCoroutineScope()
    val llmEvaluator = remember { LLMEvaluator() }
    var chatThread by remember { mutableStateOf(ChatThread()) }
    var userInput by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                actions = {
                    IconButton(onClick = { navController.navigate("modelsSettings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type your message...") },
                        enabled = !isGenerating
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(
                        onClick = {
                            if (userInput.isNotBlank() && !isGenerating) {
                                val userMessage = Message(
                                    role = MessageRole.USER,
                                    content = userInput
                                )
                                chatThread.messages.add(userMessage)
                                val currentInput = userInput
                                userInput = ""
                                isGenerating = true
                                
                                scope.launch {
                                    val assistantMessage = Message(
                                        role = MessageRole.ASSISTANT,
                                        content = ""
                                    )
                                    chatThread.messages.add(assistantMessage)
                                    
                                    llmEvaluator.generateResponse(currentInput).collect { response ->
                                        assistantMessage.content = response
                                        chatThread = chatThread.copy()
                                        listState.animateScrollToItem(chatThread.messages.size - 1)
                                    }
                                    isGenerating = false
                                }
                            }
                        },
                        enabled = userInput.isNotBlank() && !isGenerating
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chatThread.messages) { message ->
                ChatMessage(message = message)
            }
        }
    }
}

@Composable
private fun ChatMessage(message: Message) {
    val isUser = message.role == MessageRole.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 