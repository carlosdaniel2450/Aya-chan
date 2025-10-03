package com.ayachan.assistant

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AyaChanAssistantActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var commandProcessor: CommandProcessor
    
    private lateinit var statusText: TextView
    private lateinit var commandText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistant)
        
        statusText = findViewById(R.id.statusText)
        commandText = findViewById(R.id.commandText)
        
        val closeButton: Button = findViewById(R.id.closeButton)
        closeButton.setOnClickListener { finish() }
        
        textToSpeech = TextToSpeech(this, this)
        commandProcessor = CommandProcessor(this)
        
        startVoiceRecognition()
    }
    
    private fun startVoiceRecognition() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {
                statusText.text = "Ouvindo..."
            }
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                statusText.text = "Erro: $error"
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val command = matches?.get(0) ?: ""
                commandText.text = "Comando: $command"
                processCommand(command)
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
        }
        speechRecognizer.startListening(intent)
    }
    
    private fun processCommand(command: String) {
        val response = commandProcessor.processCommand(command, textToSpeech)
        commandText.text = "Resposta: $response"
    }
    
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("pt", "BR")
            textToSpeech.speak("Olá! Aya-chan aqui! Como posso ajudar?", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        textToSpeech.shutdown()
    }
}
