package com.example.gemma3n_ocr

import android.content.Context
import android.util.Log

class Gemma3nClient(private val context: Context) {
    companion object {
        private const val TAG = "Gemma3nClient"
        init {
            try {
                System.loadLibrary("gemma3n_ocr")
            } catch (e: UnsatisfiedLinkError) {
                Log.e(TAG, "Native library load failed")
            }
        }
    }

    external fun initEngine(modelPath: String, tokenizerPath: String): Boolean
    external fun recognizeText(imageData: FloatArray, prompt: String): String

    fun initialize(modelPath: String, tokenizerPath: String): Boolean {
        return initEngine(modelPath, tokenizerPath)
    }

    fun ocr(imageData: FloatArray, mode: String = "RECEIPT"): String {
        val prompt = when(mode) {
            "RECEIPT" -> "<image>\nExtract all information from this receipt including store name, date, items, prices, and total amount in a structured format."
            "DOCUMENT" -> "<image>\nRead and extract all text from this document."
            else -> "<image>\nExtract text."
        }
        return recognizeText(imageData, prompt)
    }
}
