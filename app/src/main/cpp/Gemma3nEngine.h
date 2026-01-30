#pragma once

#include <executorch/extension/module/module.h>
#include <string>
#include <vector>
#include <memory>
#include "Tokenizer.h"

namespace gemma3n {

class Gemma3nEngine {
public:
    Gemma3nEngine(const std::string& modelPath, const std::string& tokenizerPath);
    ~Gemma3nEngine() = default;

    std::string runInference(const std::vector<float>& imageTensor, const std::string& prompt);

private:
    std::unique_ptr<torch::executor::Module> module_;
    std::unique_ptr<Tokenizer> tokenizer_;
};

} // namespace gemma3n
