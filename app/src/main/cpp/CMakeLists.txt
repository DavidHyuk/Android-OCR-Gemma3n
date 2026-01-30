cmake_minimum_required(VERSION 3.22.1)

project("gemma3n_ocr")

set(EXECUTORCH_INSTALL_DIR ${CMAKE_CURRENT_SOURCE_DIR}/executorch_lib)
set(EXECUTORCH_INCLUDE_DIR ${EXECUTORCH_INSTALL_DIR}/include)
set(EXECUTORCH_LIB_DIR ${EXECUTORCH_INSTALL_DIR}/lib/${ANDROID_ABI})

include_directories(${EXECUTORCH_INCLUDE_DIR})
include_directories(${CMAKE_CURRENT_SOURCE_DIR})

# JNI 라이브러리 정의
add_library(gemma3n_ocr SHARED
    gemma3n-jni.cpp
    Gemma3nEngine.cpp
)

# ExecuTorch Runtime Libraries
add_library(executorch SHARED IMPORTED)
set_target_properties(executorch PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libexecutorch.so)

add_library(extension_module SHARED IMPORTED)
set_target_properties(extension_module PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libextension_module.so)

# Acceleration Delegates (Vulkan, XNNPACK)
add_library(vulkan_delegate SHARED IMPORTED)
set_target_properties(vulkan_delegate PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libvulkan_delegate.so)

add_library(xnnpack_delegate SHARED IMPORTED)
set_target_properties(xnnpack_delegate PROPERTIES IMPORTED_LOCATION ${EXECUTORCH_LIB_DIR}/libxnnpack_delegate.so)

find_library(log-lib log)
find_library(vulkan-lib vulkan)

target_link_libraries(
    gemma3n_ocr
    executorch
    extension_module
    vulkan_delegate
    xnnpack_delegate
    ${log-lib}
    ${vulkan-lib}
)
