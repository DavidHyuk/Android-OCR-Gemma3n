# Android-OCR-Gemma3n

Gemma 3n 모델과 ExecuTorch를 활용한 온디바이스(On-Device) OCR 안드로이드 애플리케이션 프로젝트입니다.

## 🚀 프로젝트 개요
이 프로젝트는 Google의 경량화 LLM인 **Gemma 3n**을 **ExecuTorch** (.pte format) 런타임에서 구동하여, 서버 없이 기기 내에서 이미지 내 텍스트를 인식하고 처리하는 OCR 기능을 구현하는 것을 목표로 합니다.

## 📅 개발 계획 (Roadmap)

### Phase 1: 환경 구축 및 모델 준비
- [x] Gemma 3n .pte 모델 준비 (ExecuTorch export 완료)
- [x] Android Studio 프로젝트 설정 (Native C++ 지원 포함)
- [x] ExecuTorch Android Runtime 라이브러리 연동 설정 (CMake 완료)

### Phase 2: Core 런타임 구현
- [ ] ExecuTorch C++ API를 활용한 Inference Engine 작성
- [ ] JNI를 통한 Java/Kotlin 인터페이스 개발
- [ ] Gemma 3n 전용 Tokenizer(SentencePiece/BPE) 통합

### Phase 3: OCR 파이프라인 구축
- [ ] 이미지 전처리 모듈 (Resize, Normalization)
- [ ] Gemma 3n Vision-Language 멀티모달 프롬프트 설계 (OCR 특화)
- [ ] 추론 결과 후처리 및 텍스트 추출

... (중략)
