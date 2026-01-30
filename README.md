# Android-OCR-Gemma3n (Receipt Extraction)

Gemma 3 모델과 ExecuTorch를 활용하여 영수증 이미지에서 모든 텍스트를 추출하는 온디바이스(On-Device) OCR 안드로이드 애플리케이션입니다.

## 🚀 프로젝트 개요
이 프로젝트는 Google의 최신 멀티모달 모델인 **Gemma 3**를 사용하여, 영수증 사진으로부터 상점 정보, 품목 리스트, 가격 등 모든 텍스트 데이터를 고해상도로 추출합니다. 서버 통신 없이 기기 내에서 모든 처리가 이루어집니다.

## 📅 개발 현황

### Phase 1: Core 엔진 및 파이프라인 (완료)
- [x] Gemma 3 .pte 모델 로드 및 추론 엔진 구현 (`Gemma3nEngine.cpp`)
- [x] **고해상도 이미지 전처리 (896x896)**: 영수증의 작은 글자 인식을 위해 해상도 최적화 완료
- [x] 영수증 특화 멀티모달 프롬프트 설계

### Phase 2: 사용자 인터페이스 및 구조화 (완료)
- [x] 갤러리 이미지 선택 기능 구현 (파일 인풋 방식)
- [x] **JSON 데이터 구조화**: 모델 출력에서 상점명, 품목, 합계 등을 추출하여 객체화 (`ReceiptModel.kt`)
- [x] 결과 텍스트 시각화 및 후처리 모듈 (`ResultPostProcessor.kt`)

### Phase 3: 성능 최적화 (완료)
- [x] **Vulkan GPU 가속 연동**: `libvulkan_delegate`를 통한 추론 속도 개선
- [x] **XNNPACK CPU 가속**: 저사양 기기를 위한 CPU 최적화 연동

## 📖 사용 방법 (Usage)

### 1. 모델 준비
- Gemma 3 멀티모달 모델을 ExecuTorch 형식(`.pte`)으로 export합니다.
- 토크나이저 파일(`tokenizer.model`)을 준비합니다.
- 준비된 파일들을 안드로이드 기기의 내부 저장소(예: `/data/local/tmp/` 또는 앱 전용 캐시 폴더)에 복사합니다.

### 2. 네이티브 라이브러리 빌드 및 배치
앱 구동을 위해 ExecuTorch 및 가속 라이브러리(`.so`)가 필요합니다. 라이브러리가 없는 경우 아래 과정을 통해 빌드해야 합니다.

**빌드 단계:**
```bash
# 1. NDK 경로 설정 (본인의 환경에 맞게 수정)
export ANDROID_NDK=$HOME/Android/Sdk/ndk/25.1.8937393

# 2. ExecuTorch 빌드 스크립트 실행 (프로젝트 루트의 executorch 폴더 내에서 실행)
cd executorch
./build/build_android_extension.sh --abi arm64-v8a
```

**파일 배치:**
빌드 완료 후 생성된 바이너리들을 아래 경로에 복사합니다:
- `app/src/main/cpp/executorch_lib/lib/arm64-v8a/`
    - `libexecutorch.so`
    - `libextension_module.so`
    - `libvulkan_delegate.so`
    - `libxnnpack_delegate.so`
- `app/src/main/cpp/executorch_lib/include/` (헤더 파일 포함)

### 3. 앱 빌드 및 실행
- Android Studio에서 프로젝트를 엽니다.
- 기기를 연결하고 **Run** 버튼을 눌러 설치합니다.
- 앱에서 **"Pick Image"**를 선택하고 **"Extract Data"**를 눌러 결과를 확인합니다.

## 🛠 주요 기술 스택
- **Language**: Kotlin, C++ (JNI)
- **AI Runtime**: ExecuTorch (PyTorch On-Device)
- **Model**: Google Gemma 3 (Vision-Language)
- **UI**: Jetpack Compose
- **Acceleration**: Vulkan GPU, XNNPACK
- **Data**: kotlinx-serialization (JSON)
- **Resolution**: 896x896 (High-Res for OCR)

## ⚠️ 주의 사항
- 본 앱은 온디바이스에서 대규모 멀티모달 모델을 구동하므로, RAM 8GB 이상의 플래그십 기기(예: S23 이상)를 권장합니다.
- Vulkan 가속이 지원되지 않는 기기에서는 XNNPACK(CPU)으로 자동 전환되어 속도가 느려질 수 있습니다.
