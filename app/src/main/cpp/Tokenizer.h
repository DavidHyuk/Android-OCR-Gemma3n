#pragma once

#include <string>
#include <vector>
#include <cstdint>

namespace gemma3n {

class Tokenizer {
public:
    Tokenizer(const std::string& modelPath);
    std::vector<int32_t> encode(const std::string& text);
    std::string decode(const std::vector<int32_t>& tokens);

private:
    // 실제 구현은 SentencePiece 등을 사용해야 함
};

} // namespace gemma3n
