package com.ayachan.assistant

import android.content.Context
import android.speech.tts.TextToSpeech

class CommandProcessor(private val context: Context) {
    
    fun processCommand(command: String, tts: TextToSpeech): String {
        return when {
            command.contains("hora", ignoreCase = true) -> {
                val time = java.text.SimpleDateFormat("HH:mm").format(java.util.Date())
                tts.speak("São $time", TextToSpeech.QUEUE_FLUSH, null, null)
                "🕐 São $time"
            }
            command.contains("olá", ignoreCase = true) -> {
                tts.speak("Olá! Aya-chan desu!", TextToSpeech.QUEUE_FLUSH, null, null)
                "👋 Olá! Aya-chan desu!"
            }
            else -> {
                tts.speak("Desculpe, não entendi", TextToSpeech.QUEUE_FLUSH, null, null)
                "❌ Comando não reconhecido: $command"
            }
        }
    }
}
