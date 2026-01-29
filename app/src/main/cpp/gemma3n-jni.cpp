#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "Gemma3nJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_gemma3n_1ocr_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from Gemma 3n Native OCR Engine";
    return env->NewStringUTF(hello.c_str());
}
