package com.example.gemma3n_ocr

import kotlinx.serialization.json.Json

class ResultPostProcessor {
    private val json = Json { 
        ignoreUnknownKeys = true 
        coerceInputValues = true
    }

    /**
     * 모델의 출력을 정제하고 JSON 객체로 파싱을 시도합니다.
     */
    fun process(rawText: String): String {
        val cleaned = rawText
            .replace("<pad>", "")
            .replace("<eos>", "")
            .replace("<bos>", "")
            .trim()
        
        return try {
            // JSON 부분만 추출 (모델이 텍스트와 함께 JSON을 줄 수 있음)
            val jsonStart = cleaned.indexOf("{")
            val jsonEnd = cleaned.lastIndexOf("}")
            if (jsonStart != -1 && jsonEnd != -1) {
                val jsonStr = cleaned.substring(jsonStart, jsonEnd + 1)
                val receipt = json.decodeFromString<ReceiptInfo>(jsonStr)
                formatReceipt(receipt)
            } else {
                cleaned
            }
        } catch (e: Exception) {
            cleaned // 파싱 실패 시 원본 반환
        }
    }

    private fun formatReceipt(receipt: ReceiptInfo): String {
        val sb = StringBuilder()
        sb.append("🏪 Store: ${receipt.store_name ?: "Unknown"}\n")
        sb.append("📅 Date: ${receipt.date ?: "Unknown"}\n")
        sb.append("----------------------------\n")
        receipt.items.forEach { item ->
            sb.append("• ${item.name}: $${item.price} (x${item.quantity})\n")
        }
        sb.append("----------------------------\n")
        sb.append("💰 Total: $${receipt.total ?: "0.00"}")
        return sb.toString()
    }
}
