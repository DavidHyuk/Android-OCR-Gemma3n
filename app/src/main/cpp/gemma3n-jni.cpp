#include <jni.h>
#include <string>
#include <vector>
#include "Gemma3nEngine.h"

static gemma3n::Gemma3nEngine* engine = nullptr;

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_gemma3n_1ocr_Gemma3nClient_initEngine(JNIEnv* env, jobject thiz, jstring model_path, jstring tokenizer_path) {
    const char* m_path = env->GetStringUTFChars(model_path, nullptr);
    const char* t_path = env->GetStringUTFChars(tokenizer_path, nullptr);

    if (engine) {
        delete engine;
    }
    engine = new gemma3n::Gemma3nEngine(m_path, t_path);

    env->ReleaseStringUTFChars(model_path, m_path);
    env->ReleaseStringUTFChars(tokenizer_path, t_path);

    return engine != nullptr;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_gemma3n_1ocr_Gemma3nClient_recognizeText(JNIEnv* env, jobject thiz, jfloatArray image_data, jstring prompt) {
    if (engine == nullptr) return env->NewStringUTF("Engine not initialized");

    jsize len = env->GetArrayLength(image_data);
    jfloat* body = env->GetFloatArrayElements(image_data, nullptr);
    std::vector<float> data(body, body + len);
    env->ReleaseFloatArrayElements(image_data, body, JNI_ABORT);

    const char* p_str = env->GetStringUTFChars(prompt, nullptr);
    std::string result = engine->runInference(data, p_str);
    env->ReleaseStringUTFChars(prompt, p_str);

    return env->NewStringUTF(result.c_str());
}
