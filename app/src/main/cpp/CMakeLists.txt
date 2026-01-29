cmake_minimum_required(VERSION 3.22.1)

project("gemma3n_ocr")

# ExecuTorch 관련 경로 설정
set(EXECUTORCH_INSTALL_DIR ${CMAKE_CURRENT_SOURCE_DIR}/executorch_lib)
set(EXECUTORCH_INCLUDE_DIR ${EXECUTORCH_INSTALL_DIR}/include)
set(EXECUTORCH_LIB_DIR ${EXECUTORCH_INSTALL_DIR}/lib/${ANDROID_ABI})

include_directories(${EXECUTORCH_INCLUDE_DIR})

# JNI 라이브러리 정의
add_library(gemma3n_ocr SHARED gemma3n-jni.cpp)

# ExecuTorch 런타임 라이브러리 (나중에 파일이 배치되어야 함)
# libexecutorch.so, libextension_module.so 등이 필요함
add_library(executorch SHARED IMPORTED)
set_target_properties(executorch PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libexecutorch.so)

add_library(extension_module SHARED IMPORTED)
set_target_properties(extension_module PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libextension_module.so)

find_library(log-lib log)

target_link_libraries(
    gemma3n_ocr
    executorch
    extension_module
    ${log-lib}
)
