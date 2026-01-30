package com.example.gemma3n_ocr

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptInfo(
    val store_name: String? = null,
    val date: String? = null,
    val items: List<ReceiptItem> = emptyList(),
    val total: Double? = null
)

@Serializable
data class ReceiptItem(
    val name: String,
    val price: Double,
    val quantity: Int = 1
)
