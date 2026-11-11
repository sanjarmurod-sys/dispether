package com.water.dispatcher.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DispatcherDashboardScreen(
    dispatcherName: String,
    onSpeechBtnClick: () -> Unit,
    parsedResult: String,
    onSaveData: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Mas'ul dispetcher: $dispatcherName", style = MaterialTheme.typography.titleMedium)
                Text(text = "Tizim holati: Telegram Avto-yuborish faol", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "🎙 AI Ovozli Dispetcher", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedCard(modifier = Modifier.fillMaxWidth().height(140.dp)) {
            Box(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (parsedResult.isEmpty()) "Mikrofon tugmasini bosib, ko'rsatkichni ayting..." else parsedResult,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSpeechBtnClick,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(text = "🎤 Ovozli Kiritishni Boshlash")
        }

        if (parsedResult.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSaveData,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(text = "✅ Excel'ga Yozish va Tasdiqlash")
            }
        }
    }
}
