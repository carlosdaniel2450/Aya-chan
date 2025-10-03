package com.ayachan.assistant

import android.content.Context
import org.json.JSONObject
import java.io.File

class FlashcardManager(private val context: Context) {
    
    fun addFlashcard(pergunta: String, resposta: String) {
        // Implementação simples
        val file = File(context.filesDir, "flashcards.json")
        // ... código para salvar JSON
    }
}