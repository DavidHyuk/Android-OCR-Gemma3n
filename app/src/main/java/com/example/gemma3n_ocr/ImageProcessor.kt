package com.example.gemma3n_ocr

import android.graphics.Bitmap
import android.graphics.Color

class ImageProcessor {
    /**
     * Gemma 3n 모델의 Vision Encoder 사양에 맞춰 이미지를 전처리합니다.
     * Gemma 3는 고해상도(896x896 등)를 지원하여 OCR 성능이 뛰어납니다.
     */
    fun preprocess(bitmap: Bitmap, targetWidth: Int = 896, targetHeight: Int = 896): FloatArray {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
        val floatArray = FloatArray(targetWidth * targetHeight * 3)
        
        var index = 0
        // Gemma 3는 일반적으로 RGB 채널 순서와 [0, 1] 또는 특정 Mean/Std 정규화를 사용합니다.
        for (y in 0 until targetHeight) {
            for (x in 0 until targetWidth) {
                val pixel = resizedBitmap.getPixel(x, y)
                // R, G, B 순서로 정규화하여 저장
                floatArray[index++] = Color.red(pixel) / 255.0f
                floatArray[index++] = Color.green(pixel) / 255.0f
                floatArray[index++] = Color.blue(pixel) / 255.0f
            }
        }
        return floatArray
    }
}
